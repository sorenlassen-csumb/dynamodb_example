package edu.csumb.dynamodbexample;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

/**
 * Created by ndavidson on 9/21/16.
 */
public class SelectExamples {
    public static boolean get(String tablename, long id){
        Table tb = DBConnector.getDynamoDB().getTable(tablename); // Table Name = test
        Item i = tb.getItem("id", id, "id, dataStored", null);
        if(i==null){
            System.out.println("not existing");
        }else{
            System.out.println(i.get("dataStored"));
        }
        return false;
    }
}
