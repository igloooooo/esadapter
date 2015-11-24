package au.com.iglooit.espower.esadapter.mapper;

/**
 * Created by nicholaszhu on 26/10/2015.
 */
public interface IFieldMapper {
    Boolean isIgnore(String name);
    String toESName(String name);
    String toSourceName(String esName);
}
