package com.ncl.ccp.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.springframework.stereotype.Component;

import java.time.Duration;

/************
 * Class Name       :   Commands
 * Creation Date    :   25-Aug-22
 * Description      :   Perform Actions on Web Elements
 */

@Component
public enum Commands {
    NAVIGATE_APP {
        @Override
        public boolean executeCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = false;
            if(driver!= null) {
                driver.navigate().to(value);
                flag = true;
            }
            return flag;
        }
    },
    SET_VALUE {
        @Override
        public boolean executeCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = false;
            try {
                wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
                driver.findElement(By.xpath(locator)).sendKeys(value);
                flag = true;
            }
            catch (Exception e) { }
            return flag;
        }
    },
    CLICK {
        @Override
        public boolean executeCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = false;
            try {
                wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
                driver.findElement(By.xpath(locator)).click();
                flag = true;
            }
            catch (Exception e){}
            return flag;
        }
    },
    WAIT_FOR_ELEMENT {
        @Override
        public boolean executeCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = false;
            try {
                Wait<WebDriver> wait = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(timeOut))
                        .pollingEvery(Duration.ofSeconds(1))
                        .ignoring(NoSuchElementException.class,
                                StaleElementReferenceException.class );
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
                flag = true;
            }
            catch (Exception e){}
            return flag;
        }
    },
    IS_DISPLAYED{
        @Override
        public boolean executeCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = false;
            try {
                Wait<WebDriver> wait = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(timeOut))
                        .pollingEvery(Duration.ofSeconds(1))
                        .ignoring(NoSuchElementException.class,
                                StaleElementReferenceException.class );
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
                flag = true;
            }
            catch (Exception e){}
            return flag;
        }
    },
    IS_NOTDISPLAYED{
        @Override
        public boolean executeCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = true;
            try {
                Wait<WebDriver> wait = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(timeOut))
                        .pollingEvery(Duration.ofSeconds(1))
                        .ignoring(NoSuchElementException.class,
                                StaleElementReferenceException.class );
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
                flag = false;
            }
            catch (Exception e){}
            return flag;
        }
    },
    IS_ENABLED{
        @Override
        public boolean executeCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = false;
            try {
                flag = driver.findElement(By.xpath(locator)).isEnabled();
            }
            catch (Exception e){}
            return flag;
        }
    },
    WAIT_FOR_ELEMENT_CLICKABLE {
        @Override
        public boolean executeCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = false;
            try {
                wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
                flag = true;
            }
            catch (Exception e){}
            return flag;
        }
    },
    SELECT_OPTION{
        @Override
        public boolean executeCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = false;
            try {
                Select select = new Select(driver.findElement(By.xpath(locator)));
                select.selectByVisibleText(value);
                flag = true;
            }
            catch (Exception e){}
            return flag;
        }
    },

    CLEAR{
        @Override
        public boolean executeCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = false;
            try {
                driver.findElement(By.xpath(locator)).clear();
                flag=true;
            }
            catch (Exception e){}
            return flag;
        }
    },
    PREFILL {
        @Override
        public boolean executeCommand(WebDriver driver, String locator, String value, int timeOut) {

            boolean flag = false;
            String actValue = "";

            try {
                WebElement ele = driver.findElement(By.xpath(locator));
                if(ele.getTagName().contains("input")){
                    actValue = driver.findElement(By.xpath(locator)).getAttribute("value");
                }
                else {
                    actValue = driver.findElement(By.xpath(locator)).getText();
                }
                if(actValue.equals(value)) flag=true;
            }
            catch (Exception e){}
            return flag;
        }
    },

    SWITCH_TO_FRAME {
        @Override
        public boolean executeCommand (WebDriver driver, String locator, String value,int timeOut){
            boolean flag = false;
            try {
                WebElement iframe = driver.findElement(By.xpath(locator));
                driver.switchTo().frame(iframe);
                flag = true;
            } catch (Exception e) {
            }
            return flag;
        }
    },
    JS_SET_VALUE {
        @Override
        public boolean executeCommand(WebDriver driver, String locator, String value, int timeOut) {
            boolean flag = false;
            try {
                js = (JavascriptExecutor)driver;
                wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
                WebElement ele = driver.findElement(By.xpath(locator));
                js.executeScript("arguments[0].value='"+ value + "';", ele);
                ele.sendKeys(Keys.TAB);
                Thread.sleep(100);
                flag = true;
            } catch (Exception e) {
            }
            return flag;
        }
    };



    JavascriptExecutor js;
    WebDriverWait wait;

    public abstract boolean executeCommand(WebDriver driver, String locator, String value, int timeOut);

}
