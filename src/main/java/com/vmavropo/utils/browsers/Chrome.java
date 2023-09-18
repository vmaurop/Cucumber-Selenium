package com.vmavropo.utils.browsers;

import com.vmavropo.utils.config.EnvDataConfig;
import com.vmavropo.utils.config.ResourcesConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

public class Chrome {

    public static final int ZERO = 0;

    public static final String SLASH = "/";

    public static final String BACK_SLASH = "\\";

    public static final String TRUE = "true";

    public static final String FALSE = "false";

    ResourcesConfig resourcesConfig = new ResourcesConfig();

    EnvDataConfig envDataConfig = new EnvDataConfig();

    public WebDriver start() {
        String remoteURL = envDataConfig.getDriverRemoteURL();
        if (!remoteURL.isEmpty()) {
            try {
                RemoteWebDriver driver = new RemoteWebDriver(new URL(remoteURL), getChromeOptions());
                driver.setFileDetector(new LocalFileDetector());
                return driver;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return new ChromeDriver(getChromeOptions());
        }
    }

    private ChromeOptions getChromeOptions() {
        if (envDataConfig.getDriverMode().equalsIgnoreCase("manual")) {
            System.setProperty("webdriver.chrome.driver", resourcesConfig.getChromeDriver());
        } else {
            WebDriverManager.chromedriver().setup();
        }

        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", ZERO);
        chromePrefs.put("download.default_directory", resourcesConfig.getOutputDir().replace(SLASH, BACK_SLASH));
        chromePrefs.put("safebrowsing.enabled", TRUE);
        chromePrefs.put("download.directory_upgrade", TRUE);
        chromePrefs.put("download.prompt_for_download", FALSE);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("lang=en");
        if (envDataConfig.getHeadlessMode()) {
            options.addArguments("headless");
        }
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--ignore-certificate-errors"); //Suppressed Certificate errors
        options.setCapability(ChromeOptions.CAPABILITY, options);
        options.setCapability("chrome.switches", Arrays.asList("incognito"));
        options.addArguments("--remote-allow-origins=*"); //Allows remote logging to suppress problematic Chrome v111
        return options;
    }
}
