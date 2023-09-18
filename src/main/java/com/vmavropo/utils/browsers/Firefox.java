package com.vmavropo.utils.browsers;

import com.vmavropo.utils.config.EnvDataConfig;
import com.vmavropo.utils.config.ResourcesConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Firefox {

    ResourcesConfig resourcesConfig = new ResourcesConfig();

    EnvDataConfig envDataConfig = new EnvDataConfig();

    public WebDriver start() {
        String remoteURL = envDataConfig.getDriverRemoteURL();
        if (!remoteURL.isEmpty()) {
            try {
                RemoteWebDriver driver = new RemoteWebDriver(new URL(remoteURL), getFirefoxOptions());
                driver.setFileDetector(new LocalFileDetector());
                return driver;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return new FirefoxDriver(getFirefoxOptions());
        }
    }

    private FirefoxOptions getFirefoxOptions() {
        if (envDataConfig.getDriverMode().equalsIgnoreCase("manual")) {
            System.setProperty("webdriver.gecko.driver", resourcesConfig.getFirefoxDriver());
        } else {
            WebDriverManager.firefoxdriver().setup();
        }

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(getFirefoxProfile());
        if (envDataConfig.getHeadlessMode()) {
            options.setHeadless(true);
        }
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        return options;
    }

    private FirefoxProfile getFirefoxProfile() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.dir", resourcesConfig.getOutputDir());
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/zip," +
                "text/csv," +
                "text/plain," +
                "image/jpeg," +
                "application/octet-stream," +
                "application/xml,text/xml," +
                "application/pdf," +
                "application/x-pdf," +
                "application/vnd.ms-excel," +
                "application/msword," +
                "application/vnd.ms-powerpoint," +
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet," +
                "application/vnd.openxmlformats-officedocument.presentationml.presentation," +
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        );
        profile.setPreference("pdfjs.disabled", true);
        profile.setPreference("plugin.disable_full_page_plugin_for_types",
                "application/pdf,application/vnd.adobe.xfdf,application/vnd.fdf,application/vnd.adobe.xdp+xml");
        return profile;
    }

}
