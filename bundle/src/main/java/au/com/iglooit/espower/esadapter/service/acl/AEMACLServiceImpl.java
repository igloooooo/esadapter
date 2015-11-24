package au.com.iglooit.espower.esadapter.service.acl;

import au.com.iglooit.espower.esadapter.core.acl.AEMACLService;
import com.day.cq.security.util.CqActions;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.principal.PrincipalIterator;
import org.apache.jackrabbit.api.security.principal.PrincipalManager;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.security.Principal;
import java.util.*;

@Service(AEMACLServiceImpl.class)
@Component(metatype = true)
public class AEMACLServiceImpl implements AEMACLService {
    private final Logger LOGGER = LoggerFactory.getLogger(AEMACLServiceImpl.class);
    @Reference
    private SlingRepository repository;
    private Session session;

    protected void activate(ComponentContext context) throws Exception {
        session = repository.loginAdministrative(null);
        LOGGER.info("Init customer Field Mapping.");
    }

    @Override
    public List<String> getAccessPath() {
        List<String> result = new ArrayList<String>();
        try {
            Authorizable authorizable = getAuthorizable("damtest", session);
            Set<Principal> principals = getPrincipals(authorizable, session);

            CqActions cqActions = new CqActions(session);
            String path = "/content";
            Node node = session.getNode(path);
            if (node != null) {
                findDenyPath(node, result, cqActions, principals);
            }

        } catch (RepositoryException e) {
            e.printStackTrace();
        }


        return result;
    }

    private void findDenyPath(Node parent, final List<String> pathList, final CqActions cqActions, final Set<Principal> principals) {
        Collection<String> actions = null;
        try {
            actions = cqActions.getAllowedActions(parent.getPath(), principals);

            if (!actions.contains("read")) {
                pathList.add(parent.getPath());
                return;
            }
            NodeIterator iterator = parent.getNodes();
            while (iterator.hasNext()) {
                findDenyPath(iterator.nextNode(), pathList, cqActions, principals);
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

    }

    private  Authorizable getAuthorizable(String authorizableId, Session session)
            throws RepositoryException
    {
        UserManager uMgr = ((JackrabbitSession)session).getUserManager();
        Authorizable authorizable = uMgr.getAuthorizable(authorizableId);
        if (authorizable == null) {
            throw new RepositoryException("No such authorizable " + authorizableId);
        }
        return authorizable;
    }

    private Set<Principal> getPrincipals(Authorizable authorizable, Session session)
            throws RepositoryException
    {
        Set<Principal> principals = new LinkedHashSet();

        Principal princ = authorizable.getPrincipal();
        principals.add(princ);
        for (PrincipalIterator it = ((JackrabbitSession)session).getPrincipalManager().getGroupMembership(princ); it.hasNext();) {
            principals.add(it.nextPrincipal());
        }
        return principals;
    }

}
