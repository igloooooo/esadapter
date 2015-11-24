package au.com.iglooit.espower.esadapter.core.index;

import javax.jcr.Node;

/**
 * Created by nicholaszhu on 24/11/2015.
 */
public interface IIndexNodeHandler {
    public void addNodeIndex(Node node);
    public void saveOrUpdateNodeIndex(Node node);
    public void deleteNodeIndex(Node node);
    public void updateNodeIndex(Node node);
}
