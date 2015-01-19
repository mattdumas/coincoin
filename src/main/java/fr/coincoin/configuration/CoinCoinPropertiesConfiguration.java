package fr.coincoin.configuration;

import org.apache.commons.configuration.*;

import java.util.Iterator;
import java.util.Properties;

public class CoinCoinPropertiesConfiguration {


    public Configuration getConfiguration() {
        try {
            CompositeConfiguration configuration = new CompositeConfiguration();
            SystemConfiguration systemConfiguration = new SystemConfiguration();
            systemConfiguration.setDelimiterParsingDisabled(true);
            configuration.addConfiguration(systemConfiguration);

            PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
            propertiesConfiguration.setDelimiterParsingDisabled(true);
            propertiesConfiguration.load("coincoin.properties");
            configuration.addConfiguration(propertiesConfiguration);
            return configuration;
        } catch (ConfigurationException e) {
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
