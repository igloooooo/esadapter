package au.com.iglooit.espower.esadapter.core.mapping;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by nicholaszhu on 2/11/2015.
 */
public class DefaultSystemMapping implements ESAssetMapping {
    public static final String DEFAULT_SYSTEM_INDEX = "aem";
    public static final String DAM_ASSET = "dam:Asset";
    public static final String DAM_ASSET_TYPE = "dam_Asset";
    public static final String CQ_PAGE = "cq:Page";
    public static final String CQ_PAGE_TYPE = "cq_Page";
    public static final Map<String, String> DEFAULT_SYSTEM_TYPE_MAP = ImmutableMap.<String, String>builder()
            .put(DAM_ASSET_TYPE, DAM_ASSET)
            .put(CQ_PAGE_TYPE, CQ_PAGE)
            .build();

    private static DefaultSystemMapping instance;

    private DefaultSystemMapping() {

    }

    public static DefaultSystemMapping getInstance() {
        if (instance == null) {
            instance = new DefaultSystemMapping();
        }
        return instance;
    }

    @Override
    public String getIndexName() {
        return DEFAULT_SYSTEM_INDEX;
    }

    @Override
    public String getAEMAssetName(String type) {
        return DEFAULT_SYSTEM_TYPE_MAP.get(type);
    }

    @Override
    public String getTypeName(String assetType) {
        return null;
    }

    @Override
    public void updateFieldMapping(String json) {

    }

    @Override
    public String getMapping() {
        return null;
    }
}
