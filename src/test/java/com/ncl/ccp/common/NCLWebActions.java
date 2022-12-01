package com.ncl.ccp.common;

/************
 * Class Name       :   NCLWebActions.
 * Creation Date    :
 * Description      :   To Perform WebElement UserActions
 */

import com.ncl.ccp.data.OrderedHashtable;
import com.ncl.ccp.generic.NCLWebDriver;
import com.ncl.ccp.selenium.Commands;
import com.ncl.ccp.selenium.ExecuteCommands;
import com.ncl.ccp.selenium.ExecuteListCommands;
import com.ncl.ccp.selenium.ListCommands;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

//import static com.ncl.ccp.pages.ccp.HomePage;

@Component
public class NCLWebActions {
    private static final Logger LOGGER = LoggerFactory.getLogger(NCLWebActions.class);
    public WebDriver webDriver;

    @Autowired
    NCLWebDriver nclWebDriver;

    @Autowired
    ExecuteCommands executeCommands;

    @Autowired
    ExecuteListCommands executeListCommands;

    String errMessage = "";

    public boolean launchApplication(String appURL) {
        boolean flag = false;
        LOGGER.info("Launching Application " + appURL);
        webDriver = nclWebDriver.launchApp(appURL);
        return flag;
    }

    public boolean setValue(String action, String locator, String elementName) {
        boolean flag = false;
        String[] value;

        value = action.split("\\|");

        switch (value[0]) {
            case "IS_DISPLAYED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_DISPLAYED, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " NOT displayed";
            }
            case "NOT_DISPLAYED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_NOTDISPLAYED, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " displayed";
            }
            case "IS_ENABLED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_ENABLED, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " NOT enabled";
            }
            case "PREFILL" -> {
                //Syntax " PREFILL|EXP_VALUE Or PREFILL|GET_VAR
                if (value[1].equals("GET_VAR")) {
                    value[1] = NCLHooks.getRuntimeData(NCLHooks.CURR_PAGE + "." + elementName);
                }

                flag = executeCommands.elementActions(webDriver, Commands.PREFILL, locator, value[1], 10);
                if (!flag) errMessage = "Element " + elementName + " value not matched with " + value[1];
            }
            case "TYPE" -> {
                flag = executeCommands.elementActions(webDriver, Commands.JS_SET_VALUE, locator, value[1], 10);
                if (!flag) errMessage = "Element " + elementName + " failed to set value";
            }
            default -> {
                NCLHooks.setRuntimeData(NCLHooks.CURR_PAGE + "." + elementName, action);
                executeCommands.elementActions(webDriver, Commands.CLEAR, locator, action, 10);
                flag = executeCommands.elementActions(webDriver, Commands.SET_VALUE, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " failed to set value";
            }
        }
        if (!flag) {
            LOGGER.info(errMessage);
            throw new RuntimeException(errMessage);
        }

        return flag;
    }

    public boolean selectCalender(String action, String locator, String elementName) {
        boolean flag = false;
        String[] value;

        value = action.split("\\|");

        switch (value[0]) {
            case "IS_DISPLAYED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_DISPLAYED, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " NOT displayed";
            }
            case "NOT_DISPLAYED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_NOTDISPLAYED, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " displayed";
            }
            case "IS_ENABLED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_ENABLED, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " NOT enabled";
            }
            default -> {
                executeCommands.elementActions(webDriver, Commands.CLEAR, locator, action, 10);
                flag = executeCommands.elementActions(webDriver, Commands.SET_VALUE, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " failed to set value";
            }
        }
        if (!flag) {
            LOGGER.info(errMessage);
            throw new RuntimeException(errMessage);
        }

        return flag;
    }

    public boolean clickValue(String action, String locator, String elementName) {
        boolean flag = false;

        String[] value;
        value = action.split("\\|");

        switch (value[0]) {
            case "IS_DISPLAYED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_DISPLAYED, locator, value[0], 10);
                if (!flag) errMessage = "Element " + elementName + " NOT displayed";
            }
            case "IS_ENABLED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_ENABLED, locator, value[0], 10);
                if (!flag) errMessage = "Element " + elementName + " NOT enabled";
            }
            case "NOT_DISPLAYED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_DISPLAYED, locator, action, 10);
                if (!flag) flag = true;
                else {
                    errMessage = "Element " + elementName + " displayed";
                    flag = false;
                }
            }
            case "PREFILL" -> {
                //Syntax " PREFILL|EXP_VALUE Or PREFILL|GET_VAR
                if (value[1].equals("GET_VAR")) {
                    value[1] = NCLHooks.getRuntimeData(NCLHooks.CURR_PAGE + "." + elementName);
                }
//                else {
//                    value[1] = AZHooks.getRuntimeData(value[1]);
//                }

                flag = executeCommands.elementActions(webDriver, Commands.PREFILL, locator, value[1], 10);
                if (!flag) errMessage = "Element " + elementName + " value not mached with " + value[1];
            }
            default -> {
                flag = executeCommands.elementActions(webDriver, Commands.CLICK, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " failed to click";
            }
        }
        if (!flag) {
            LOGGER.info(errMessage);
            throw new RuntimeException(errMessage);
        }
        return flag;
    }

    public boolean selectPopup(String action, String locator, String elementName) {
        boolean flag = false;
        String rerult = "";

        String[] value;

        value = action.split("\\|");

        switch (value[0]) {
            case "IS_DISPLAYED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_DISPLAYED, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " NOT displayed";
            }
            case "NOT_DISPLAYED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_NOTDISPLAYED, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " displayed";
            }
            case "IS_ENABLED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_ENABLED, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " NOT enabled";
            }
            case "VERIFY_ITEMS" -> {
                rerult = executeListCommands.elementActions(webDriver, ListCommands.VERIFY_ITEMS, locator, action, 10);
                if (!rerult.equals(""))
                    errMessage = "Option items " + rerult + " missing in the Element " + elementName;
                else flag = true;
            }
            case "PREFILL" -> {
                //Syntax " PREFILL|EXP_VALUE Or PREFILL|GET_VAR
                if (value[1].equals("GET_VAR")) {
                    action = NCLHooks.getRuntimeData(NCLHooks.CURR_PAGE + "." + elementName);
                    System.out.println(action);
                } else {
                    action = NCLHooks.getRuntimeData(value[1]);
                }

                flag = executeCommands.elementActions(webDriver, Commands.PREFILL, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " value not mached with " + action;
            }
            default -> {
                rerult = executeListCommands.elementActions(webDriver, ListCommands.SELECT_ITEM, locator, action, 10);
                if (rerult.equals("")) errMessage = "Element " + elementName + " failed to select value";
                else flag = true;
            }
        }
        if (!flag) {
            LOGGER.info(errMessage);
            throw new RuntimeException(errMessage);
        }
        return flag;
    }

    ///Write a generic method to read Table Headers
    public boolean verifyTableHeaders(String value, String locator, String key) {
        ArrayList<String> actHeaders = new ArrayList<>();
        ArrayList<String> notMatchedHeaders = new ArrayList<>();
        List<WebElement> headerList = webDriver.findElements(By.xpath(locator));
        for (WebElement list : headerList) {
            String str = list.getText();
            actHeaders.add(str);
        }
        String[] expHeaders = value.split("#");
        boolean flag = false;
        for (int counter = 0; counter < expHeaders.length; counter++) {
            flag = actHeaders.contains(expHeaders[counter]);
            if (!flag) notMatchedHeaders.add(expHeaders[counter]);
            //if (!flag) errMessage = "Element " + key + " NOT Present";
        }
        if (notMatchedHeaders.size() > 0) {
            errMessage = "Table " + key + " following headers not matched - " + notMatchedHeaders + " with actual Headers - " + actHeaders;
            throw new RuntimeException(errMessage);
        }
        return flag;
    }

    public boolean selectOption(String action, String locator, String elementName) {
        boolean flag = false;
        String rerult = "";

        String[] value;

        value = action.split("\\|");

        switch (value[0]) {
            case "IS_DISPLAYED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_DISPLAYED, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " NOT displayed";
            }
            case "NOT_DISPLAYED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_NOTDISPLAYED, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " displayed";
            }
            case "IS_ENABLED" -> {
                flag = executeCommands.elementActions(webDriver, Commands.IS_ENABLED, locator, action, 10);
                if (!flag) errMessage = "Element " + elementName + " NOT enabled";
            }
            default -> {
                rerult = String.valueOf(executeCommands.elementActions(webDriver, Commands.SELECT_OPTION, locator, action, 10));
                if (rerult.equals("")) errMessage = "Element " + elementName + " failed to select value " + action;
                else flag = true;
            }
        }
        if (!flag) {
            LOGGER.info(errMessage);
            throw new RuntimeException(errMessage);
        }
        return flag;
    }

    public boolean swithFrames(String locator) {
        boolean flag = false;

        flag = executeCommands.elementActions(webDriver, Commands.SWITCH_TO_FRAME, locator, "", 10);

        if (!flag) {
            errMessage = "Failed to Switch to Frame " + locator;
            LOGGER.info(errMessage);
            throw new RuntimeException(errMessage);
        }
        return flag;
    }

    public void switchToDefault() {
        webDriver.switchTo().defaultContent();
    }


    //e.g. value = VERIFY_TABLE:Stateroom #|GETVAL
    public boolean verifyTableData(String locator, String value, OrderedHashtable data, String key) {
        boolean flag = false;
        if (value.contains(":")) {
            String[] arrValue = value.split(":");
            switch (arrValue[0]) {
                case "VERIFY_TABLE" -> {
                    // String[] arrActData = arrValue[1].split();
                }
            }
        }
        List<WebElement> headerList = webDriver.findElements(By.xpath(locator));
        return flag;
    }

    public boolean waitForElementClose(String locator) {
        boolean flag = false;
        try {
            Wait<WebDriver> wait = new FluentWait<>(webDriver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class,
                            StaleElementReferenceException.class);
            wait.until(ExpectedConditions.visibilityOfAllElements(webDriver.findElements(By.xpath(locator))));
            wait = new FluentWait<>(webDriver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class,
                            StaleElementReferenceException.class);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
            flag = true;
        } catch (Exception e) {
        }
        return flag;
    }
    public boolean waitForElement(String locator) {
        boolean flag = false;
        try {
            Wait<WebDriver> wait = new FluentWait<>(webDriver)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class,
                            StaleElementReferenceException.class);
            wait.until(ExpectedConditions.visibilityOfAllElements(webDriver.findElements(By.xpath(locator))));
            wait = new FluentWait<>(webDriver)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class,
                            StaleElementReferenceException.class);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            flag = true;
        } catch (Exception e) {
        }
        return flag;
    }
    public boolean verifyStateRoomTypes(String value, String locator, String key) {
        List<WebElement> rowslist = webDriver.findElements(By.xpath(locator));

        String[] types = value.split("#");
        boolean flag = false;
        for (int i = 0; i < rowslist.size(); i++) {
            boolean typeDisplayStatus = webDriver.findElement(By.xpath(locator + "[" + (i + 1) + "]//form//a[contains(@class,'ui-first-child')]")).getText().equals(types[i]);
            boolean basePriceDisplayStatus = webDriver.findElement(By.xpath(locator + "[" + (i + 1) + "]//form//div[8]//a")).isDisplayed();
            if (typeDisplayStatus == true && basePriceDisplayStatus == true) flag = true;
            else {
                flag = false;
                errMessage = key + " - " + types[i] + " not displayed state rooms categories.";
                break;
            }
        }
        if (!flag) throw new RuntimeException(errMessage);

        return flag;
    }

    public int lisOfMembers(String locator) {
        List<WebElement> list = webDriver.findElements(By.xpath(locator));
        return list.size();
    }

    //e.g. value = VERIFY_TABLE:Col|Val;Col|Val
    public boolean tableDataActions(String locator, String value, String key) {
        boolean flag = false;
        ArrayList<String> headers = new ArrayList<>();
        ArrayList<String> headerValues = new ArrayList<>();
        ArrayList headersList = new ArrayList();
        try {
            if (value.contains(":")) {
                String[] arrValue = value.split(":");
                switch (arrValue[0]) {
                    case "VERIFY_TABLE" -> {
                        String[] arrActData = arrValue[1].split(";");
                        for (String actData : arrActData) {
                            String[] colVal = actData.split("|");
                            headers.add(colVal[0]);
                            headerValues.add(colVal[1]);
                        }
                        headersList = getHeaderNumbers(locator, headers, key);

                        List<WebElement> tableData = webDriver.findElements(By.xpath(locator + "/tbody/tr"));
                        if (tableData.size() > 0) {
                            for (WebElement row : tableData) {
                                flag = false;
                                List<WebElement> cols = row.findElements(By.xpath("td"));
                                for (Object col : headersList) {
                                    flag = false;
                                    if (cols.get((int) col).getText().trim().equals(headerValues.get((int) col))) {
                                        flag = true;
                                    } else break;
                                }
                                if (flag) break;
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
        }
        if (!flag) {
            errMessage = "Matching data " + headerValues + " not displayed in table " + key;
        }

        return flag;
    }

    public String getText(String locator) {
        String value = webDriver.findElement(By.xpath(locator)).getText();
        return value;
    }

    public String randomGenderSelection(String locator) {
        List<WebElement> list = webDriver.findElements(By.xpath(locator));
        for (WebElement element : list) {
            boolean flag = element.getAttribute("class").contains("ui-radio-off");
            if (flag) {
                return element.getText();
            }
        }
        return null;
    }

    private ArrayList getHeaderNumbers(String locator, ArrayList headers, String key) {
        boolean flag = true;
        ArrayList headersList = new ArrayList();
        try {
            List<WebElement> tableHeaders = webDriver.findElements(By.xpath(locator + "/thead/tr/th"));

            for (int counter = 0; counter < headers.size(); counter++) {
                flag = false;
                int index = 0;
                for (WebElement ele : tableHeaders) {
                    if (headers.get(counter).equals(ele.getText())) {
                        headersList.add(index);
                        flag = true;
                        break;
                    }
                    index++;
                }
                if (!flag) {
                    errMessage = "Matching column header " + headers.get(counter) + " not displayed in table " + key;
                    break;
                }
            }
        } catch (Exception exp) {
            errMessage = "Failed to get the table " + key + " details";
        }
        if (!flag) throw new RuntimeException(errMessage);

        return headersList;
    }

    public double retriveTotalSumValue() {
        double totalAmount = 0;
        List<WebElement> tableRows = webDriver.findElements(By.xpath("//div[@role='dialog']/div/descendant::h1[text()='Credit Card Payment']/parent::div/following-sibling::div/descendant::tbody/tr"));
        if (tableRows.size() > 0) {
            for (WebElement row : tableRows) {
                List<WebElement> cols = row.findElements(By.tagName("td"));
                totalAmount = totalAmount + Double.parseDouble(cols.get(3).getText().replace("$", "").replaceAll(",", "").trim());
            }
        }
        return totalAmount;
    }

    public int getLastDropdownOption(int value) {
        String text = null;
        List<WebElement> li = webDriver.findElements(By.xpath("//a[text()='Hide Available Categories']/ancestor::fieldset/descendant::ul/li[" + value + "]/descendant::select/option"));
        return li.size() - 1;
    }


    public boolean updateStateroomSelection(String value)
    {
        boolean flag=false;
        flag = executeCommands.elementActions(webDriver, Commands.IS_DISPLAYED, "//div[text()='" + value + "']", "IS_DISPLAYED", 10);
        if (flag) {
            int size = webDriver.findElements(By.xpath("//div[text()='" + value + "']/ancestor::fieldset/descendant::ul/li")).size();
            for (int rowsCount = 1; rowsCount <= size; rowsCount++) {
                if (webDriver.findElement(By.xpath(" //a[text()='Hide Available Categories']/ancestor::fieldset/descendant::ul/li[" + rowsCount + "]/descendant::form/div[14]/descendant::a[@data-icon='vx-trash-can-white']")).isDisplayed()) {
                    webDriver.findElement(By.xpath(" //a[text()='Hide Available Categories']/ancestor::fieldset/descendant::ul/li[" + rowsCount + "]/descendant::form/div[14]/descendant::a[@data-icon='vx-trash-can-white']")).click();
                }
            }
        }
        return flag;
    }

    public boolean payNowButtonStatus(String locator) {
        boolean status = executeCommands.elementActions(webDriver, Commands.IS_DISPLAYED, locator, "IS_DISPLAYED", 10);
        return status;
    }

    public boolean addExcursions(String locator, int numberOfExcursions) {
        boolean flag = false;
        int count = 0;
        List<WebElement> rows = webDriver.findElements(By.xpath(locator));
        for (int row = 1; row <= rows.size(); row++) {
            WebElement element = webDriver.findElement(By.xpath(locator + "[" + row + "]//td[6]//a"));
            String button = element.getText();
            if (button.equals("Add")) {
                element.click();
                webDriver.findElement(By.xpath("//div[@class='ui-popup-container ui-popup-active']/descendant::a[text()='Apply']")).click();
                flag = true;
                count++;
                if (count == numberOfExcursions) {
                    break;
                }
            }
        }
        return flag;
    }

    public boolean deleteExcursions(String locator, int numberOfExcursions) {
        boolean flag = false;
        int count = 0;
        List<WebElement> rows = webDriver.findElements(By.xpath(locator));
        for (int row = 1; row <= rows.size(); row++) {
            waitForElementClose(locator + "[1]//td[13]//a");
            WebElement element = webDriver.findElement(By.xpath(locator + "[1]//td[13]//a"));
            element.click();
            flag = true;
            count++;
            if (count == numberOfExcursions) {
                break;
            }
        }
        return flag;
    }
}
