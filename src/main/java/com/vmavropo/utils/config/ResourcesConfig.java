package com.vmavropo.utils.config;

import java.io.File;
import java.nio.file.Paths;


public class ResourcesConfig {

    public static final String ENV_PROPERTIES = "env.properties";

    public static final String TEST = "test";

    public static final String MAIN = "main";

    public static final String TEST_CLASSES = "test-classes/";

    public static final String SRC = "src/";

    public static final String RESOURCES = "/resources";

    public static final String TARGET_TEST_CLASSES = "/target/test-classes";

    public static final String CLASSES = "classes/";

    public String getOutputDir() {
        return getTestResourcesPath() + "/test-data/outputDir";
    }

    public String getInputDir() {
        EnvDataConfig envDataConfig = new EnvDataConfig();
        return getTestResourcesPath() + "/test-data/inputDir";
    }

    public String getEnvironmentProperties() {
        if (System.getProperty(ENV_PROPERTIES) == null)
            return getResourcesPath() + "/" + ENV_PROPERTIES;
        else
            return getAbsolutePath() + System.getProperty(ENV_PROPERTIES);
    }


    public String getAbsolutePath() {
        String absPath = Paths.get(".")
                .toAbsolutePath().normalize().toString().replace("\\", "/");

        String modulePath = this.getClass().getClassLoader().getResource(".").getPath();
        modulePath = modulePath.replace("\\", "/");
        modulePath = modulePath.replace(TARGET_TEST_CLASSES, "");
        modulePath = modulePath.replace(absPath, "");
        modulePath = modulePath.replace("//", "/");

        return absPath + modulePath;
    }

    public String getTargetPath() {
        String absPath = Paths.get(".")
                .toAbsolutePath().normalize().toString().replace("\\", "/");

        String modulePath = this.getClass().getClassLoader().getResource(".").getPath();
        modulePath = modulePath.replace("\\", "/");
        modulePath = modulePath.replace(absPath, "");
        modulePath = modulePath.replace("//", "/");
        modulePath = modulePath.replace(TEST_CLASSES, "");
        modulePath = modulePath.replace(CLASSES, "");

        return absPath + modulePath;
    }

    public String getChromeDriver() {
        return getTestResourcesPath() + "/drivers/chromedriver.exe";
    }

    public String getFirefoxDriver() {
        return getTestResourcesPath() + "/drivers/geckodriver.exe";
    }

    public String getEdgeDriver() {
        return getTestResourcesPath() + "/drivers/msedgedriver.exe";
    }

    private String getTestResourcesPath() {
        return getResourcesPath(TEST);
    }

    private String getResourcesPath() {
        return getResourcesPath(MAIN);
    }

    private String getResourcesPath(String packageName) {
        String filePathString = getAbsolutePath() + SRC + packageName + RESOURCES;
        File f = new File(filePathString);
        if (!f.exists())
            filePathString = getAbsolutePath();
        return filePathString;
    }
}
