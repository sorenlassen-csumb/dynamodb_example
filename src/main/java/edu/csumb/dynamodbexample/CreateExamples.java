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
                .withAttributeName("id")
                .withAttributeType(ScalarAttributeType.S));
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("dataStored")
                .withAttributeType(ScalarAttributeType.S));

        ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement()
                .withAttributeName("id")
                .withKeyType(KeyType.HASH)); //Partition key
        keySchema.add(new KeySchemaElement()
                .withAttributeName("dataStored")
                .withKeyType(KeyType.RANGE));

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
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
