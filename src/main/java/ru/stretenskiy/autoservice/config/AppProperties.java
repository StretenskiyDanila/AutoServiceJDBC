package ru.stretenskiy.autoservice.config;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class AppProperties {

    private final String FILE_NAME = "src/main/resources/app.properties";
    private static final AppProperties configFile = new AppProperties();
    private final Properties properties = new Properties();
    private String msg = "";

    public AppProperties() {
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(FILE_NAME);
            properties.load(inputStream);
        }
        catch (IOException e) {
            msg = "Can't find/open property file";
            e.printStackTrace();
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getProperties(String key) {
        return properties.getProperty(key);
    }

    public static AppProperties getInstance() {
        return configFile;
    }
}
