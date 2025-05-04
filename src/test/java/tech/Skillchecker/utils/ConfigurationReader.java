package tech.Skillchecker.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader {

    private static Properties properties;

    static {
        try {
            String path = "src/test/resources/configuration.properties";
            FileInputStream inputStream = new FileInputStream(path);

            properties = new Properties();
            properties.load(inputStream);

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось загрузить файл конфигурации: " + e.getMessage());
        }
    }

    public static String get(String keyName) {
        return properties.getProperty(keyName);
    }
}