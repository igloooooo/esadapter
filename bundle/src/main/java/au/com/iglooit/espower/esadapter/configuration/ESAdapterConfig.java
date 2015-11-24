package au.com.iglooit.espower.esadapter.configuration;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import java.util.Dictionary;

@Component(
        name = "ES Adapter Configuration",
        metatype = true,
        label = "ES Adapter Configuration"
)
@Service(ESAdapterConfig.class)
public class ESAdapterConfig {
    private Dictionary<?, ?> properties;


}
