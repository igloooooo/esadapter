package au.com.iglooit.espower.esadapter.index;

import au.com.iglooit.espower.esadapter.core.dto.FieldMappingDTO;

import javax.jcr.Node;

/**
 * Created by nicholaszhu on 19/10/2015.
 */
public interface IndexBuilder {
    String buildIndexByNode(Node node, FieldMappingDTO mappingDTO);
}
