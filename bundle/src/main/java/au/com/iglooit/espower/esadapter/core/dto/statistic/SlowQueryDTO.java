package au.com.iglooit.espower.esadapter.core.dto.statistic;

import com.fasterxml.jackson.annotation.JsonRawValue;

/**
 * Created by nicholaszhu on 31/10/2015.
 */
public class SlowQueryDTO {
    private Long took;
    @JsonRawValue
    private String content;
    private String timestamp;

    public Long getTook() {
        return took;
    }

    public void setTook(Long took) {
        this.took = took;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
