package au.com.iglooit.espower.esadapter.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Created by nicholaszhu on 17/11/2015.
 */
public final class PathUtil {

    private PathUtil() {

    }

    public static Boolean matchPath(Set<String> paths, String eventPath) {
        Path targetPath = Paths.get(eventPath);
        for (String path : paths) {
            Path monitorPath = Paths.get(path);
            if (targetPath.startsWith(monitorPath)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
