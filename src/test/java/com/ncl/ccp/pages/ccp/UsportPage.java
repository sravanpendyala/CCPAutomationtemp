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
                     flag = nclWebActions.setValueAndPressEnterKey(value, getObjMap("UsportPage.Searchdatavalue"), key);
                }
                case "Editoffload" -> {
                        flag = nclWebActions.clickValue(value, getObjMap("UsportPage.Editoffload"), key);
                }
                case "Numberofoffloads" -> {
                    flag = nclWebActions.clickValue(value, getObjMap("UsportPage.Numberofoffloads"), key);
                }
                case "Offloaddata" -> {
                    flag = nclWebActions.setValueAndPressEnterKey(value, getObjMap("UsportPage.offloaddatav"), key);
                }
                case "Offloadupdate" -> {
                    flag = nclWebActions.clickValue(value, getObjMap("UsportPage.Offloadupdates"), key);
//                    if (flag)
//                    flag = nclWebActions.clickValue(value, getObjMap("UsportPage.Offloadupdates"), key);
                }
                case "Qty" -> {
                    String a = "Yes (2)";
                    flag = nclWebActions.clickValue(value, getObjMap("UsportPage.qty",a), key);
                }
                default -> {
                    throw new RuntimeException("Field " + key + " is not defined in page " + sheetName + " class");
                }
            }
            if (!flag) throw new RuntimeException("Failed at " + key + " in page " + sheetName + " class");
        }
    }
}