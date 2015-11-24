package au.com.iglooit.espower.esadapter.core.dto;

/**
 * Created by nicholaszhu on 9/11/2015.
 */
public class ConfigDTO {
    private String esAddress = "http://localhost";
    private Integer esPort = 9200;
    private ESConfigDTO esConfigDTO = new ESConfigDTO();

    public String getEsAddress() {
        return esAddress;
    }

    public void setEsAddress(String esAddress) {
        this.esAddress = esAddress;
    }

    public ESConfigDTO getEsConfigDTO() {
        return esConfigDTO;
    }

    public void setEsConfigDTO(ESConfigDTO esConfigDTO) {
        this.esConfigDTO = esConfigDTO;
    }

    public Integer getEsPort() {
        return esPort;
    }

    public void setEsPort(Integer esPort) {
        this.esPort = esPort;
    }
}
