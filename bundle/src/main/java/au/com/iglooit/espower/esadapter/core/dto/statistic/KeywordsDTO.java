package au.com.iglooit.espower.esadapter.core.dto.statistic;

/**
 * Created by nicholaszhu on 30/10/2015.
 */
public class KeywordsDTO {
    private String keyword;
    private Long count;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
