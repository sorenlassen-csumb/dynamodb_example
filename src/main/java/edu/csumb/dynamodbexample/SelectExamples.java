package edu.csumb.dynamodbexample;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

/**
 * Created by ndavidson on 9/21/16.
 */
public class SelectExamples {
    public static Item get(String tablename, long id){
        Table table = DBConnector.getDynamoDB().getTable(tablename); // Table Name = test
        Item item = table.getItem("Id", id, "Id, dataStored", null);
        if(item==null){
            return null;
        }else{
            return item;
        }
    }
}
