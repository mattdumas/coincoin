package fr.coincoin.configuration;

import org.apache.commons.configuration.*;

import java.util.Iterator;
import java.util.Properties;

public class CoinCoinPropertiesConfiguration {


    public Configuration getConfiguration() {
        try {
            CompositeConfiguration configuration = new CompositeConfiguration();

            configuration.addConfiguration(new SystemConfiguration());
            configuration.addConfiguration(new PropertiesConfiguration("coincoin.properties"));

            return configuration;
        }
        catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public Properties getProperties(Configuration configuration) {
        Properties properties = new Properties();

        Iterator<String> keys = configuration.getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            properties.put(key, configuration.getProperty(key));
        }

        return properties;
    }


}
