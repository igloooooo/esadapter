package au.com.iglooit.espower.esadapter.core.dto;

import java.util.List;

/**
 * Created by nicholaszhu on 24/10/2015.
 */
public class DataExportResponse {
    private Long count;
    private List<String> exportedPathList;
    private Long timeTook;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<String> getExportedPathList() {
        return exportedPathList;
    }

    public void setExportedPathList(List<String> exportedPathList) {
        this.exportedPathList = exportedPathList;
    }

    public Long getTimeTook() {
        return timeTook;
    }

    public void setTimeTook(Long timeTook) {
        this.timeTook = timeTook;
    }
}
