package com.ncl.ccp.pages.ccp;

import com.ncl.ccp.data.ReadExcelData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ncl.ccp.common.NCLEnvData;
import com.ncl.ccp.common.NCLWebActions;
import com.ncl.ccp.data.OrderedHashtable;

import java.util.Enumeration;

@Component
public class LoginPage extends NCLEnvData {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

    private final String sheetName = "LoginPage";
    @Autowired
    NCLWebActions nclWebActions;

    public void performActions(String dataID) {
        boolean flag = false;

        OrderedHashtable data = readData(sheetName, dataID);

        Enumeration<String> keys = data.enumerateKeys();

        while (keys.hasMoreElements()){
            flag = false;
            String key = keys.nextElement();
            String value = data.get(key).toString();
            switch (key){
                case "UserName" -> {
                    flag = nclWebActions.setValue(value, getObjMap( "LoginPage.UserName"), key);
                }
                case "Password" -> {
                    flag = nclWebActions.setValue(value, getObjMap( "LoginPage.Password"), key);
                }
                case "LoginButton" -> {
                    flag = nclWebActions.clickValue(value, getObjMap( "LoginPage.Login"), key);
                }
                default -> {
                    throw new RuntimeException("Key " + key + " sent is not defined in page " + sheetName + " class ");
                }
            }
            if(!flag) throw new RuntimeException("Failed at " + key + " in page " + sheetName + " class");
        }


    }

}
