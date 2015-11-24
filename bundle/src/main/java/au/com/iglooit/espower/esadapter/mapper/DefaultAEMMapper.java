package au.com.iglooit.espower.esadapter.mapper;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by nicholaszhu on 26/10/2015.
 */
public class DefaultAEMMapper implements IFieldMapper {
    private static DefaultAEMMapper mapper;
    private Set<String> ignorePropertyName = new HashSet<String>();

    public static DefaultAEMMapper getInstance() {
        if (mapper == null) {
            mapper = new DefaultAEMMapper();
        }
        return mapper;
    }

    private DefaultAEMMapper() {
        ignorePropertyName.add("jcr:versionHistory");
        ignorePropertyName.add("jcr:baseVersion");
        ignorePropertyName.add("jcr:predecessors");
        ignorePropertyName.add("jcr:uuid");
    }

    @Override
    public Boolean isIgnore(String name) {
        return ignorePropertyName.contains(name);
    }

    @Override
    public String toESName(String name) {
        return null;
    }

    @Override
    public String toSourceName(String esName) {
        return null;
    }
}
