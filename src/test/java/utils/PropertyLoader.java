package utils;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

public final class PropertyLoader {
    private static final String FILENAME = "configuration.properties";
    private static final CompositeConfiguration config = new CompositeConfiguration();

    static {
        Configurations configuration = new Configurations();
        try {
            PropertiesConfiguration propertiesConfiguration = configuration.properties(FILENAME);
            config.addConfiguration(propertiesConfiguration);
        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration file: " + FILENAME, e);
        }
    }

    public static String getString(String key) {
        return config.getString(key);
    }

}
