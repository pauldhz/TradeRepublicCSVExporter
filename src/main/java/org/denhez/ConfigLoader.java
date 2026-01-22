package org.denhez;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ConfigLoader {

    public static String PREDICT_URL = "categorization-api.url";

    private Map<String, String> config;

    public ConfigLoader() {
        loadConfig();
    }

    private void loadConfig() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            config = yaml.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de charger application.yml", e);
        }
    }

    public Map<String, String> getConfig() {
        return config;
    }
}
