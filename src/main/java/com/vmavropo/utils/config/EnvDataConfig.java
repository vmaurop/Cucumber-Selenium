package com.vmavropo.utils.config;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Properties;

/**
 * The {@link EnvDataConfig} exposes all properties contained in env.properties derived from {@link ResourcesConfig} to the tests.
 */
public class EnvDataConfig {

    public static final String URL = ".URL";

    public static final String API_URL = ".API.URL";

    public static final String ENVIRONMENT = "ENV";

    public static final String TIMEOUT_ELEMENT_FIND = "TIMEOUT.ELEMENT.FIND";

    public static final String TIMEOUT_ENDPOINT_RESPONSE = "TIMEOUT.ENDPOINT.RESPONSE";

    public static final String TIMEOUT_DECLARATION_STATUS = "TIMEOUT.DECLARATION.STATUS";

    public static final String INTERVAL = "INTERVAL";

    public static final String BROWSER = "BROWSER";

    public static final String DATETIME_FORMAT = "DATETIME.FORMAT";

    public static final String DRIVER_REMOTE_URL = "DRIVER.REMOTE.URL";

    public static final String HEADLESS_MODE = "HEADLESS.MODE";

    public static final String USERNAME = ".USERNAME";

    public static final String PASSWORD = ".PASSWORD";

    public static final String DEBUG_MODE = "DEBUG.MODE";

    public static final String DRIVER_MODE = "DRIVER.MODE";


    ResourcesConfig resourcesConfig;

    public EnvDataConfig() {
        resourcesConfig = new ResourcesConfig();
    }

    public String getUrl(String applicationName) {
        return getEnvProperties().getProperty(applicationName + URL);
    }

    public String getEnvironment() {
        return getEnvProperties().getProperty(ENVIRONMENT);
    }

    public String getApiUrl(String applicationName) {
        return removeTrailingSlash(getEnvProperties().getProperty(applicationName + API_URL));
    }

    public String getTimeout() {
        return getEnvProperties().getProperty("TIMEOUT");
    }

    public String getTimeoutElementFind() {
        return getEnvProperties().getProperty(TIMEOUT_ELEMENT_FIND);
    }

    public String getInterval() {
        return getEnvProperties().getProperty(INTERVAL);
    }

    public String getBrowser() {
        return getEnvProperties().getProperty(BROWSER);
    }

    public String getDriverRemoteURL() {
        return removeTrailingSlash(getEnvProperties().getProperty(DRIVER_REMOTE_URL));
    }

    public Boolean getHeadlessMode() {
        return Boolean.parseBoolean(getEnvProperties().getProperty(HEADLESS_MODE));
    }

    public String getUsername(String userRole) {
        return getEnvProperties().getProperty(userRole + USERNAME);
    }

    public String getPassword(String userRole) {
        return getEnvProperties().getProperty(userRole + PASSWORD);
    }

    public Boolean getDebugMode() {
        return Boolean.parseBoolean(getEnvProperties().getProperty(DEBUG_MODE));
    }

    public String getDriverMode() {
        return getEnvProperties().getProperty(DRIVER_MODE);
    }

    private Properties getEnvProperties() {
        return getProperties(loadProperties(resourcesConfig.getEnvironmentProperties()));
    }

    private static Properties loadProperties(String testDataFile) {
        Properties prop = new Properties();
        try {
            InputStream inputStream = new FileInputStream(testDataFile);
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            prop.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }

    private static Properties getProperties(Properties params) {
        Properties result = new Properties();
        Enumeration<?> names = params.propertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            result.put(name, params.get(name));
        }
        return result;
    }

    private static String removeTrailingSlash(String url) {
        for (int i = 0; i < url.length(); i++) {
            if (url.endsWith("/")) {
                url = StringUtils.removeEnd(url, "/");
            } else {
                break;
            }
        }
        return url;
    }

}
