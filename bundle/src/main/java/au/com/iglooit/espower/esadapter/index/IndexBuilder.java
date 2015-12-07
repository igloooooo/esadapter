package au.com.iglooit.espower.esadapter.index;

import au.com.iglooit.espower.esadapter.core.dto.FieldMappingDTO;

import javax.jcr.Node;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nicholaszhu on 19/10/2015.
 */
public interface IndexBuilder {
    public static final String CREATION_DATE = "creation_date";
    String buildIndexByNode(Node node, FieldMappingDTO mappingDTO);
    public static final Set<String> SUPPORT_DOCUMENT = new HashSet(){{
        add("DOC");
        add("DOCX");
        add("ODT");
        add("PDF");
        add("HTML");
        add("RTF");
        add("TXT");
        add("XLS");
        add("XLSX");
        add("ODS");
        add("PPT");
        add("PPTX");
        add("ODP");
    }};
}
