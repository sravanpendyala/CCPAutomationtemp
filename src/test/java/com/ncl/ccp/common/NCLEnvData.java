package com.ncl.ccp.common;

import com.ncl.ccp.data.OrderedHashtable;
import com.ncl.ccp.data.ReadExcelData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileReader;
import java.util.Properties;

public class NCLEnvData {
    @Value("${test.config.env}")
    private String currEnv;

    @Autowired
    ReadExcelData readExcelData;

    protected String envDataFile = "test.app.datafile";

    protected String envDateFormat = "test.data.dateFormat";

    protected String getDataPath() {
        String[] currAppName;
        String appName = "";
        String className = this.toString();

        //System.out.println("Current package " + className);

        currAppName = className.split("\\.");
        appName = currAppName[currAppName.length-2];
        return System.getProperty("user.dir") + "/config/" + currEnv.trim().toLowerCase() + "/" + appName.trim().toLowerCase() + "/";

    }

    protected String getAppConfigVal(String value) {
        String dataFilePath = "";
        Properties prop;
        try {
            dataFilePath = getDataPath() + "config.properties";

            FileReader reader=new FileReader(dataFilePath);

            prop=new Properties();
            prop.load(reader);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read config data from " + dataFilePath);
        }

        return prop.getProperty(value);
    }

    protected String getObjMap(String value) {
        String dataFilePath = "";
        Properties prop;
        try{
            dataFilePath = getDataPath() + "objectmap.properties";

            FileReader reader=new FileReader(dataFilePath);

            prop=new Properties();
            prop.load(reader);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to read Object Map data from " + dataFilePath);
        }

        return prop.getProperty(value);
    }

    protected String getObjMap(String value, String replace) {
        String dataFilePath = "";
        try{
            dataFilePath = getDataPath() + "objectmap.properties";

            FileReader reader=new FileReader(dataFilePath);

            Properties prop=new Properties();
            prop.load(reader);

            value = prop.getProperty(value);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to read Object Map data from " + dataFilePath);
        }

        return value.replace("REPLACE_VAL", replace);
    }

    protected OrderedHashtable readData(String sheetName, String dataID){
        return readExcelData.readData(getDataPath() + getAppConfigVal(envDataFile), sheetName, dataID);
    }


}
