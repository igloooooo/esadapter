package au.com.iglooit.espower.esadapter.core.dto;

import java.util.List;

/**
 * Created by nicholaszhu on 18/11/2015.
 */
public class CustomerFieldMapItemDTO {
    private String name;
    private Integer type;
    private String xpath;
    private String id;

    private List<CustomerFieldMapItemDTO> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<CustomerFieldMapItemDTO> getChildren() {
        return children;
    }

    public void setChildren(List<CustomerFieldMapItemDTO> children) {
        this.children = children;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CustomerFieldMapItemDTO{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", xpath='" + xpath + '\'' +
                ", id='" + id + '\'' +
                ", children=" + children +
                '}';
    }
}
