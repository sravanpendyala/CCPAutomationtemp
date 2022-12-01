package com.ncl.ccp.selenium;

/************
 * Class Name       :   ExecuteListCommands
 * Creation Date    :   21-Sep-22
 * Description      :   Perform Generic Actions on Elements
 */

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExecuteListCommands {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteListCommands.class);
    public String elementActions(WebDriver driver, ListCommands listCommands, String locator, String value, int timeOut){
        Date d1 = new Date();
        Date d2 = new Date();
        String result = "";
        LOGGER.info("Element " + locator + " action begins");
        result = listCommands.executeListCommand(driver, locator, value, timeOut);
        LOGGER.info("Element " + locator + " action ends in " + (d2.getTime()-d1.getTime())/1000 + " seconds");

        return result;
    }
}
