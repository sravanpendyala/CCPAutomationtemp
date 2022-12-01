package com.ncl.ccp.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

/************
 * Class Name       :   ListCommands
 * Creation Date    :   20-Sep-22
 * Description      :   Perform Actions on List Web Elements
 */

@Component
public enum ListCommands {
    SELECT_ITEM{
        @Override
        public String executeListCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = false;
            String status = "";
            try {
                String[] arrValues = value.split("#");

                driver.findElement(By.xpath(locator)).click();

                wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

                List<WebElement> listItems = driver.findElements(By.xpath(listItemXPath.replace("REPLACE_STRING", value.trim())));
                wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
                js = (JavascriptExecutor)driver;
                if(listItems.size()>0){
                    js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", listItems.get(0));
                    Thread.sleep(500);
                    listItems.get(0).click();
                    Thread.sleep(100);
                    Duration defaultImp = driver.manage().timeouts().getImplicitWaitTimeout();
                    try {
                        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
                        WebElement close = driver.findElement(By.xpath("//div[contains(@id, 'listbox-popup') and contains(@class, 'ui-popup-active')]//following-sibling::a[text()='Close']"));
                        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", close);
                        close.click();
                    }catch (Exception e){}
                    driver.manage().timeouts().implicitlyWait(defaultImp);

                    flag = true;
                }
            }
            catch (Exception e){
                System.out.println("Fail - " + e.getMessage());
            }
            if(flag) status = "PASSED";
            return status;
        }
    },
    VERIFY_ITEMS{
        @Override
        public String executeListCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = false;
            String missingItems = "";
            try {
                String[] arrValues = value.split("#");
                driver.findElement(By.xpath(locator)).click();

                wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

                List<WebElement> listItems = driver.findElements(By.xpath(getListItemsXPath));
                wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
                if(listItems.size()>0){
                    missingItems = "";
                    for(String item:arrValues){
                        flag = false;
                        for(WebElement ele:listItems){
                            js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", ele);
                            String eleText = ele.getText();
                            if(eleText.contains(item)) {
                                flag = true;
                                break;
                            }
                        }
                        if(!flag) missingItems = missingItems + "#" + item;
                    }
                }
            }
            catch (Exception e){}
            return missingItems;
        }
    },
    MULTI_SEELECT_ITEMS{
        @Override
        public String executeListCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = false;
            String missingItems = "";
            try {
                String[] arrValues = value.split("#");
                driver.findElement(By.xpath(locator)).click();

                wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

                List<WebElement> listItems = driver.findElements(By.xpath(getListItemsXPath));
                wait.until(ExpectedConditions.visibilityOfAllElements(listItems));
                if(listItems.size()>0){
                    missingItems = "";
                    for(String item:arrValues){
                        flag = false;
                        for(WebElement ele:listItems){
                            js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", ele);
                            String eleText = ele.getText();
                            if(eleText.contains(item)) {
                                ele.click();
                                flag = true;
                                break;
                            }
                        }
                        if(!flag) missingItems = missingItems + "#" + item;
                    }
                }
            }
            catch (Exception e){}
            return missingItems;
        }
    };

    JavascriptExecutor js;
    WebDriverWait wait;
    String listItemXPath = "//div[contains(@id, 'listbox-popup') and contains(@class, 'ui-popup-active')]//following-sibling::ul//a[contains(text(), 'REPLACE_STRING')]";
    String getListItemsXPath = "//div[contains(@id, 'listbox-popup') and contains(@class, 'ui-popup-active')]//following-sibling::ul//a";

    public abstract String executeListCommand(WebDriver driver, String locator, String value, int timeOut);

}
