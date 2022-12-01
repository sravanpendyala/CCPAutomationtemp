package com.ncl.ccp.pages.ccp;

import com.ncl.ccp.common.NCLEnvData;
import com.ncl.ccp.common.NCLWebActions;
import com.ncl.ccp.data.OrderedHashtable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Enumeration;

@Component
public class UsportPage extends NCLEnvData {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsportPage.class);

    private static final String sheetName = "UsportPage";
    @Autowired
    NCLWebActions nclWebActions;

    public void performActions(String dataID) {
        boolean flag = false;

        OrderedHashtable data = readData(sheetName, dataID);

        Enumeration<String> keys = data.enumerateKeys();

        while (keys.hasMoreElements()) {
            flag = false;
            String key = keys.nextElement();
            String value = data.get(key).toString();
            switch (key) {
                case "Vesselsoffloads" -> {
                    flag = nclWebActions.clickValue(value, getObjMap("UsportPage.Vesselsoffloads"), key);
                }
                case "Offloadresponses" -> {
                    flag = nclWebActions.clickValue(value, getObjMap("UsportPage.Offloadresponse"), key);
                }
                case "oedrpages" -> {
                    flag = nclWebActions.clickValue(value, getObjMap("UsportPage.Searchdatavalue"), key);
                }
                case "Searchdatavalues" -> {
                    nclWebActions.waitForElement("UsportPage.Numofoffloads");
                    //flag = nclWebActions.waitForElementClose(value, getObjMap("UsportPage.Searchdatavalue"), key)
                    flag = nclWebActions.clickValue("CLICK", getObjMap("UsportPage.Numofoffloads"), key);
                   if (flag)
                        flag = nclWebActions.setValue(value, getObjMap("UsportPage.Searchdatavalue"), key);
                }
                case "Searchclicks" -> {
                    flag = nclWebActions.clickValue(value, getObjMap("UsportPage.Searchclick"), key);
                }
                case "Editoffload" -> {
                    nclWebActions.waitForElement("UsportPage.Editoffload");
                    flag = nclWebActions.clickValue(value, getObjMap("UsportPage.Editoffload"), key);
                }
                case "Numberofoffloads" -> {
                    nclWebActions.waitForElement("UsportPage.Numberofoffloads");
                    flag = nclWebActions.clickValue(value, getObjMap("UsportPage.Numberofoffloads"), key);
                }

                case "offloaddata" -> {
                    nclWebActions.waitForElement("UsportPage.offloaddata");
                    flag = nclWebActions.setValue(value, getObjMap("UsportPage.offloaddata"), key);
                }
                case "Offloadupdate" -> {
                    flag = nclWebActions.clickValue(value, getObjMap("UsportPage.Offloadupdate"), key);
                }
                default -> {
                    throw new RuntimeException("Field " + key + " is not defined in page " + sheetName + " class");
                }
            }
            if (!flag) throw new RuntimeException("Failed at " + key + " in page " + sheetName + " class");
        }
    }
}