package au.com.iglooit.espower.esadapter.impl;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.util.concurrent.jsr166e.LongAdder;

/**
 * Created by nicholaszhu on 17/10/2015.
 */
public class ESTransportClient extends TransportClient {

    public ESTransportClient() throws ElasticsearchException {
    }

    public ESTransportClient(Settings settings) {
        super(settings);
    }

    public ESTransportClient(Settings.Builder settings) {
        super(settings);
    }

    public ESTransportClient(Settings.Builder settings, boolean loadConfigSettings) throws ElasticsearchException {
        super(settings, loadConfigSettings);
    }

    public ESTransportClient(Settings pSettings, boolean loadConfigSettings) throws ElasticsearchException {
        super(pSettings, loadConfigSettings);
    }
}
