package au.com.iglooit.espower.esadapter.util;

import org.elasticsearch.common.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by nicholaszhu on 20/10/2015.
 */
public final class DefaultNodeTypeUtils {
    private static final String PAGE_TYPE = "cq:Page";
    private static final String DAM_TYPE = "dam:Asset";
    private static final Set<String> FOLD_TYPE = new HashSet<String>() {{
        add("sling:OrderedFolder");
        add("nt:folder");
        add("sling:Folder");
    }};

    private DefaultNodeTypeUtils() {

    }

    public static String getTypeNameFromNodeType(String nodeTypeName) {
        if (StringUtils.isNotEmpty(nodeTypeName)) {
            return nodeTypeName.replaceAll(":", "_");
        }
        return "";
    }

    public static Boolean isIgnoreNode(String nodeTypeName) {
        return !(DAM_TYPE.equalsIgnoreCase(nodeTypeName) || PAGE_TYPE.equalsIgnoreCase(nodeTypeName));
    }

    public static Boolean isFolder(String nodeTypeName) {
        return FOLD_TYPE.contains(nodeTypeName);
    }

    public static Boolean isPage(String nodeTypeName) {
        return PAGE_TYPE.equalsIgnoreCase(nodeTypeName);
    }

    public static Boolean isDam(String nodeTypeName) {
        return DAM_TYPE.equalsIgnoreCase(nodeTypeName);
    }
}
