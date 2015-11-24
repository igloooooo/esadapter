package au.com.iglooit.espower.esadapter.core.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;

import java.util.Date;

/**
 * Created by nicholaszhu on 2/11/2015.
 */
public class MappingDTO {
    private String index;
    private String type;
    private String cqType;
    private Date lastModified;
    private Long count;
    @JsonRawValue
    private String mappingContent;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getMappingContent() {
        return mappingContent;
    }

    public void setMappingContent(String mappingContent) {
        this.mappingContent = mappingContent;
    }

    public String getCqType() {
        return cqType;
    }

    public void setCqType(String cqType) {
        this.cqType = cqType;
    }
}
