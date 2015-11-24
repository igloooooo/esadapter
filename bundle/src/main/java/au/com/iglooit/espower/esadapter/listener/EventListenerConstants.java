package au.com.iglooit.espower.esadapter.listener;

/**
 * Created by nicholaszhu on 19/10/2015.
 */
public interface EventListenerConstants {
    public String ASSET_PATH = "/content/dam";
    public String PAGE_PATH = "/content";
    public String PAGE_TYPE = "cq:Page";
    public String DAM_TYPE = "dam:Asset";
    public String[] ASSET_LISTENER_TYPE = { DAM_TYPE, "dam:AssetContent" };

    public String[] PAGE_LISTENER_TYPE = { PAGE_TYPE, "cq:PageContent" };

}
