package edu.csumb.dynamodbexample;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;

/**
 * Created by ndavidson on 9/21/16.
 */
public class CreateExamples {


    public static boolean createTable(String tablename){
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("Id")
                .withAttributeType(ScalarAttributeType.N));

        ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement()
                .withAttributeName("Id")
                .withKeyType(KeyType.HASH)); //Partition key

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tablename)
                .withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(5L)
                        .withWriteCapacityUnits(6L));
        try {
            DBConnector.getDynamoDB().createTable(request);
        }catch (Exception e){
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean createRow(String tablename, long id, String dataToStore){
        Table table = DBConnector.getDynamoDB().getTable(tablename);
        try{
            Item sessionRow = new Item()
                .withPrimaryKey("Id", id)
                .withString("dataStored", dataToStore); // dataStored is used because data is a protected dynamodb column name

            table.putItem(sessionRow);

            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
