package au.com.iglooit.espower.esadapter.core.mapping;

import java.util.Map;

/**
 * Created by nicholaszhu on 2/11/2015.
 */
public interface ESAssetMapping {


    public String getIndexName();

    /**
     *
     * @param type dam_Asset
     * @return dam:Asset
     */
    public String getAEMAssetName(String type);

    /**
     *
     * @param assetType e.x. dam:Asset
     * @return dam_Asset
     */
    public String getTypeName(String assetType);

    public void updateFieldMapping(String json);

    public String getMapping();
}
