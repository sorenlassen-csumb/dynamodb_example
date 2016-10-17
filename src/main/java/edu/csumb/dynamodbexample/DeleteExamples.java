package edu.csumb.dynamodbexample;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;

/**
 * Created by nathanieldavidson on 9/22/16.
 */
public class DeleteExamples {
    public static boolean delete(String tablename, long id, DBController controller){
        Table table = controller.getDynamoDB().getTable(tablename); // Table Name = test
        try{
            DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withPrimaryKey("Id", id);

            table.deleteItem(deleteItemSpec);
            table.waitForActiveOrDelete();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteTable(String tableName, DBController controller){
        Table table = controller.getDynamoDB().getTable(tableName);
        try {
            table.delete();
        } catch (ResourceNotFoundException ex) {
            return false; // didn't exist
        }
        try {
            table.waitForDelete();
        } catch (InterruptedException ex) {
            // See http://www.yegor256.com/2015/10/20/interrupted-exception.html
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        }
        return true;
    }
}
