package au.com.iglooit.espower.esadapter.core.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by nicholaszhu on 9/11/2015.
 */
public class ESConfigDTO {
    private String clusterName = "elasticsearch";
    private Long connectTimeout = 10000L;
    private Long nodesSamplerInterval = 10000L;
    private Boolean sniff = true;
    private String threadpoolSearchType = "fixed";
    private Long threadpoolSearchSize = 8L;
    private Long threadpoolSearchQueueSize = 100L;
    private Set<String> listenerPath = new HashSet<String>();
    private Boolean startListener = true;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Long getNodesSamplerInterval() {
        return nodesSamplerInterval;
    }

    public void setNodesSamplerInterval(Long nodesSamplerInterval) {
        this.nodesSamplerInterval = nodesSamplerInterval;
    }

    public Boolean getSniff() {
        return sniff;
    }

    public void setSniff(Boolean sniff) {
        this.sniff = sniff;
    }

    public String getThreadpoolSearchType() {
        return threadpoolSearchType;
    }

    public void setThreadpoolSearchType(String threadpoolSearchType) {
        this.threadpoolSearchType = threadpoolSearchType;
    }

    public Long getThreadpoolSearchSize() {
        return threadpoolSearchSize;
    }

    public void setThreadpoolSearchSize(Long threadpoolSearchSize) {
        this.threadpoolSearchSize = threadpoolSearchSize;
    }

    public Long getThreadpoolSearchQueueSize() {
        return threadpoolSearchQueueSize;
    }

    public void setThreadpoolSearchQueueSize(Long threadpoolSearchQueueSize) {
        this.threadpoolSearchQueueSize = threadpoolSearchQueueSize;
    }

    public Set<String> getListenerPath() {
        return listenerPath;
    }

    public void setListenerPath(Set<String> listenerPath) {
        this.listenerPath = listenerPath;
    }

    public Boolean getStartListener() {
        return startListener;
    }

    public void setStartListener(Boolean startListener) {
        this.startListener = startListener;
    }
}
