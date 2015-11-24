package au.com.iglooit.espower.esadapter.core.searchhistory;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Date;

/**
 * Created by nicholaszhu on 29/10/2015.
 */
public class SearchHistory {
    private Long took;
    private Long hit;
    private float maxScore;
    private String keyword;
    private JsonNode searchContent;
    private Date timestamp;

    public Long getTook() {
        return took;
    }

    public void setTook(Long took) {
        this.took = took;
    }

    public Long getHit() {
        return hit;
    }

    public void setHit(Long hit) {
        this.hit = hit;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public JsonNode getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(JsonNode searchContent) {
        this.searchContent = searchContent;
    }

    public float getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(float maxScore) {
        this.maxScore = maxScore;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
