package edu.csumb.dynamodbexample;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;

/**
 * Created by ndavidson on 9/21/16.
 */
public class CreateExamples {

    public static boolean createTable(String tablename, DBController controller){
        AttributeDefinition attributeDefinition = new AttributeDefinition()
                .withAttributeName("Id")
                .withAttributeType(ScalarAttributeType.N);

        KeySchemaElement keySchemaElement = new KeySchemaElement()
                .withAttributeName("Id")
                .withKeyType(KeyType.HASH); //Partition key

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tablename)
                .withKeySchema(keySchemaElement)
                .withAttributeDefinitions(attributeDefinition)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(5L)
                        .withWriteCapacityUnits(6L));
        Table table;
        try {
            table = controller.getDynamoDB().createTable(request);
        } catch (ResourceInUseException ex) {
            return false;
        }
        try {
            table.waitForActive();
        } catch (InterruptedException ex) {
            // See http://www.yegor256.com/2015/10/20/interrupted-exception.html
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        }
        return true;
    }

    public static boolean createRow(String tablename, long id, String dataToStore, DBController controller){
        Table table = controller.getDynamoDB().getTable(tablename);
        try{
            Item sessionRow = new Item()
                .withPrimaryKey("Id", id)
                .withString("dataStored", dataToStore); // dataStored is used because data is a protected dynamodb column name

            table.putItem(sessionRow);

            table.waitForActiveOrDelete();

            return true;
        }catch(Exception e){
            return false;
        }
    }

}
