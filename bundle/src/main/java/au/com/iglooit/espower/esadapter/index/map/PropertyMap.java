package au.com.iglooit.espower.esadapter.index.map;

import java.util.Map;

/**
 * Created by nicholaszhu on 19/10/2015.
 */
public interface PropertyMap {
    String getIndexPropertyName(String propertyName);
    String getPropertyName(String indexPropertyName);
    Map<String, String> getPropertyMap();

}
