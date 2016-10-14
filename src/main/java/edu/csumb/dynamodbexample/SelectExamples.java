package edu.csumb.dynamodbexample;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

/**
 * Created by ndavidson on 9/21/16.
 */
public class SelectExamples {
    public static Item get(String tablename, long id, DBController controller){
        Table table = controller.getDynamoDB().getTable(tablename); // Table Name = test
        Item item = table.getItem("Id", id, "Id, dataStored", null);
        if(item==null){
            return null;
        }else{
            return item;
        }
    }







    /*
        DOES NOT WORK DO NOT USE THIS!!!
    public static Item get(String tablename, String data, DBController controller){
        Table table = controller.getDynamoDB().getTable(tablename); // Table Name = test
        QuerySpec spec = new QuerySpec()
                .withProjectionExpression("Id, dataStored")
                .withFilterExpression("dataStored = :stored")
                .withValueMap(new ValueMap()
                        .withString(":stored", data));

        ItemCollection<QueryOutcome> items = table.query(spec);
        Iterator<Item> item = items.iterator();
        if(item.hasNext()){
            return null;
        }else{
            return item.next();
        }
    }*/
}
