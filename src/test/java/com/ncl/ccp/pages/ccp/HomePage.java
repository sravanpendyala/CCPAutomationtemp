package com.ncl.ccp.pages.ccp;

import com.ncl.ccp.data.ReadExcelData;
import com.ncl.ccp.common.NCLEnvData;
import com.ncl.ccp.common.NCLWebActions;
import com.ncl.ccp.data.OrderedHashtable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Enumeration;

@Component
public class HomePage extends NCLEnvData {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomePage.class);

    private static final String sheetName = "HomePage";
    @Autowired
    NCLWebActions nclWebActions;

    public void launchApplication() {
        nclWebActions.launchApplication(getAppConfigVal("test.app.url"));
    }

    public void performActions(String dataID) {
        boolean flag = false;

        OrderedHashtable data = readData(sheetName, dataID);

        Enumeration<String> keys = data.enumerateKeys();

        while (keys.hasMoreElements()) {
            flag = false;
            String key = keys.nextElement();
            String value = data.get(key).toString();
            switch (key) {
                case "Username" -> {
                flag = nclWebActions.setValue(value, getObjMap("HomePage.Username"), key);
                }
                case "Password" -> {
                    flag = nclWebActions.setValue(value, getObjMap("HomePage.Password"), key);
                }
                case "LoginButton" -> {
                    flag = nclWebActions.clickValue(value, getObjMap("HomePage.Signin"), key);
                 }
                case "UserLoginname" -> {
                    String a = "sravan.bar";
                    flag = nclWebActions.clickValue(value, getObjMap("HomePage.UserLoginname",a), key);
                }
                default -> {
                    throw new RuntimeException("Field " + key + " is not defined in page " + sheetName + " class");
                }
            }
            if (!flag) throw new RuntimeException("Failed at " + key + " in page " + sheetName + " class");
        }
    }
}