package au.com.iglooit.espower.esadapter.core.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nicholaszhu on 5/11/2015.
 */
public class FieldMappingDTO {
    private String index;
    private String esType;
    private String cqType;
    private Date lastModified;
    private Boolean valid;
    private List<CustomerFieldMapItemDTO> children = new ArrayList<CustomerFieldMapItemDTO>();

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getEsType() {
        return esType;
    }

    public void setEsType(String esType) {
        this.esType = esType;
    }

    public String getCqType() {
        return cqType;
    }

    public void setCqType(String cqType) {
        this.cqType = cqType;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }


    @Override
    public String toString() {
        return "FieldMappingDTO{" +
                "index='" + index + '\'' +
                ", esType='" + esType + '\'' +
                ", cqType='" + cqType + '\'' +
                ", lastModified=" + lastModified +
                ", mappingContent='" + children + '\'' +
                '}';
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public List<CustomerFieldMapItemDTO> getChildren() {
        return children;
    }

    public void setChildren(List<CustomerFieldMapItemDTO> children) {
        this.children = children;
    }
}
