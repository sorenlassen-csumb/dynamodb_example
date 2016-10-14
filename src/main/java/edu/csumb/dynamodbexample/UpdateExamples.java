package edu.csumb.dynamodbexample;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

/**
 * Created by nathanieldavidson on 9/22/16.
 */
public class UpdateExamples {
    public static boolean update(String tablename, long id, String newDataToStore, DBController controller){
        Table table = controller.getDynamoDB().getTable(tablename); // Table Name = test
        try{
            UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("Id", id)
                .withUpdateExpression("set #sd=:data")
                .withNameMap(
                        new NameMap().with("#sd", "dataStored")
                )
                .withValueMap(
                        new ValueMap().withString(":data", newDataToStore) // store new session data
                );

            table.updateItem(updateItemSpec);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
