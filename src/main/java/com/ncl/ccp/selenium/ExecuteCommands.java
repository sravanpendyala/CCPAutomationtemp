package com.ncl.ccp.selenium;

/************
 * Class Name       :   ExecuteCommands
 * Creation Date    :   25-Aug-22
 * Description      :   Perform Generic Actions on Elements
 */

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExecuteCommands {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteCommands.class);
    public boolean elementActions(WebDriver driver, Commands commands, String locator, String value, int timeOut){
        Date d1 = new Date();
        Date d2 = new Date();
        boolean flag = false;
        LOGGER.info("Element " + locator + " action begins");
        flag = commands.executeCommand(driver, locator, value, timeOut);
        LOGGER.info("Element " + locator + " action ends in " + (d2.getTime()-d1.getTime())/1000 + " seconds");
        return flag;
    }
}
