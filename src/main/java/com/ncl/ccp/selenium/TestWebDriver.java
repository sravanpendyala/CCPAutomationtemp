package com.ncl.ccp.selenium;
/************
 * Class Name       :   TestWebDriver
 * Creation Date    :   24-Aug-22
 * Description      :   Creates WebDriver based on browser type
 */

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestWebDriver {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestWebDriver.class);

    @Value("${test.config.useProxy}")
    private String useProxy;

    @Value("${test.config.proxyUser}")
    private String proxyUser;

    @Value("${test.config.proxyPassword}")
    private String proxyPassword;

    @Value("${test.web.headless}")
    private String runHeadless;

    public WebDriver getChromiumDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--disable-popup-blocking");

        if (useProxy.equalsIgnoreCase("TRUE")) {
            WebDriverManager.chromedriver().config().setProxyUser(proxyUser);
            WebDriverManager.chromedriver().config().setProxyPass(proxyPassword);
        }

        WebDriverManager.chromedriver().setup();

        // Headless mode
        if (runHeadless.equalsIgnoreCase("TRUE")) {
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("window-size=1920,1080");
        }

        WebDriver webDriver = new ChromeDriver(chromeOptions);
        return webDriver;
    }

    public WebDriver getEdgeDriver() {

        if (useProxy.equalsIgnoreCase("TRUE")) {
            WebDriverManager.edgedriver().config().setProxyUser(proxyUser);
            WebDriverManager.edgedriver().config().setProxyPass(proxyPassword);
        }

        WebDriverManager.edgedriver().setup();

        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--start-maximized");
        edgeOptions.addArguments("--ignore-certificate-errors");
        edgeOptions.addArguments("--disable-popup-blocking");
        // Headless mode
        if (runHeadless.equalsIgnoreCase("TRUE")) {
            edgeOptions.addArguments("--headless");
            edgeOptions.addArguments("--disable-gpu");
            edgeOptions.addArguments("window-size=1920,1080");
        }

        WebDriver webDriver = new EdgeDriver(edgeOptions);
        return webDriver;
    }

    public WebDriver getFirefoxDriver() {

        if (useProxy.equalsIgnoreCase("TRUE")) {
            WebDriverManager.firefoxdriver().config().setProxyUser(proxyUser);
            WebDriverManager.firefoxdriver().config().setProxyPass(proxyPassword);
        }

        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--start-maximized");
        firefoxOptions.addArguments("--ignore-certificate-errors");
        firefoxOptions.addArguments("--disable-popup-blocking");
        // Headless mode
        if (runHeadless.equalsIgnoreCase("TRUE")) {
            firefoxOptions.addArguments("--headless");
            firefoxOptions.addArguments("--disable-gpu");
            firefoxOptions.addArguments("window-size=1920,1080");
        }

        WebDriver webDriver = new FirefoxDriver(firefoxOptions);
        return webDriver;
    }
}
