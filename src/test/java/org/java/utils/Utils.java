package org.java.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {
    private Utils() {

    }

    public static String getBaseUrl() {
        try (InputStream is = Utils.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties props = new Properties();
            props.load(is);
            return props.getProperty("baseUrl");
        } catch (IOException e) {
            throw new RuntimeException("Не удалось найти application.properties", e);
        }
    }
}