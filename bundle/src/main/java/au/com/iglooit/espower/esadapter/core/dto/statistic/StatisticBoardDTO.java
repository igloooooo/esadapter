package au.com.iglooit.espower.esadapter.core.dto.statistic;

import au.com.iglooit.espower.esadapter.util.StatisticUtil;

/**
 * Created by nicholaszhu on 26/11/2015.
 */
public class StatisticBoardDTO {
    private Long indexCount;
    private Long nodeCount;
    private Long queryCount;
    private Long slowestQuery;
    private Double averageQuery;
    private Long thisWeekIndex;
    private Long yesterdayIndex;

    public Long getIndexCount() {
        return indexCount;
    }

    public void setIndexCount(Long indexCount) {
        this.indexCount = indexCount;
    }

    public Long getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(Long nodeCount) {
        this.nodeCount = nodeCount;
    }

    public Long getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(Long queryCount) {
        this.queryCount = queryCount;
    }

    public Long getSlowestQuery() {
        return slowestQuery;
    }

    public void setSlowestQuery(Long slowestQuery) {
        this.slowestQuery = slowestQuery;
    }

    public Double getAverageQuery() {
        return StatisticUtil.round(averageQuery, 2);
    }

    public void setAverageQuery(Double averageQuery) {
        this.averageQuery = averageQuery;
    }

    public Long getThisWeekIndex() {
        return thisWeekIndex;
    }

    public void setThisWeekIndex(Long thisWeekIndex) {
        this.thisWeekIndex = thisWeekIndex;
    }

    public Long getYesterdayIndex() {
        return yesterdayIndex;
    }

    public void setYesterdayIndex(Long yesterdayIndex) {
        this.yesterdayIndex = yesterdayIndex;
    }
}
