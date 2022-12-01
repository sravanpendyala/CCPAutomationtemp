package com.ncl.ccp.data;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ReadExcelData {

    public OrderedHashtable readData(String fileName, String sheetName, String rowID) {
        Fillo fillo = new Fillo();
        OrderedHashtable dataValues;
        Connection connection = null;
        Recordset recordset = null;
        try{
            dataValues = new OrderedHashtable();
            connection = fillo.getConnection(fileName);
            String strQuery="select * from " + sheetName + " where DATA_ID='" + rowID + "'";

            recordset = connection.executeQuery(strQuery);
            ArrayList dataColumns = recordset.getFieldNames();
            if(recordset.getCount()>0) {
                while (recordset.next()){
                    Recordset finalRecordset = recordset;
                    dataColumns.forEach(item -> {
                        try {
                            if(!item.equals("DATA_ID")){
                                if(!NAN(finalRecordset.getField(item.toString())).equals("")) {
                                    dataValues.put(item, finalRecordset.getField(item.toString()));
                                }
                            }
                        } catch (FilloException e) {
                            throw new RuntimeException("Failed to read test data from " + fileName + " for " + item);
                        }
                    });
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to read test data from " + fileName);
        }
        finally {
            if(connection!=null) {
                if(recordset!=null) recordset.close();
                connection.close();
            }
        }
        return dataValues;
    }

    private String NAN(String value) {
        if(value==null) value="";
        else if(value.isEmpty()) value = "";
        return value;
    }

}
