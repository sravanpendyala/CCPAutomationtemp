package com.ncl.ccp.generic;
/************
 * Class Name       :   NCLWebDriver
 * Creation Date    :   29-Aug-22
 * Description      :   Returns WebDriver based on browser type
 */

import com.ncl.ccp.selenium.TestWebDriver;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class NCLWebDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(NCLWebDriver.class);
    @Value("${test.web.browser}")
    private String browserType;

    @Value("${test.web.timeout}")
    private String webTimeout;

    @Autowired
    TestWebDriver testWebDriver;

    enum browsers  {CHROME, EDGE, FIREFOX};

    public WebDriver launchApp(String appURL){
        WebDriver webDriver = null;

        switch (browsers.valueOf(browserType.toUpperCase())){
            case CHROME -> {
                webDriver = testWebDriver.getChromiumDriver();
            }
            case EDGE -> {
                webDriver = testWebDriver.getEdgeDriver();
            }
            case FIREFOX -> {
                webDriver = testWebDriver.getFirefoxDriver();
            }
        }
        webDriver.get(appURL);
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(webTimeout)));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(webTimeout)));
        return webDriver;
    }
}
