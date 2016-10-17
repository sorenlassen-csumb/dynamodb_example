package edu.csumb.dynamodbexample;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;

/**
 * DynamoDB table with numerical key in column named id and values in column named dataStored.
 */
public class IdDataTable {
    private static final String KEY_COLUMN = "id";
    private static final String DATA_COLUMN = "dataStored";

    public static IdDataTable createTable(String tablename, DBController controller) {
        AttributeDefinition attributeDefinition = new AttributeDefinition()
                .withAttributeName(KEY_COLUMN)
                .withAttributeType(ScalarAttributeType.N);
        KeySchemaElement keySchemaElement = new KeySchemaElement()
                .withAttributeName(KEY_COLUMN)
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
            return null;
        }
        try {
            table.waitForActive();
        } catch (InterruptedException ex) {
            // See http://www.yegor256.com/2015/10/20/interrupted-exception.html
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        }
        return new IdDataTable(table);
    }

    public static IdDataTable openTable(String tablename, DBController controller) {
        Table table = controller.getDynamoDB().getTable(tablename);
        return new IdDataTable(table);
    }

    private Table table;

    private IdDataTable(Table table) {
        this.table = table;
    }

    public boolean deleteTable() {
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

    public void put(long id, String data) {
        Item sessionRow = new Item()
            .withPrimaryKey(KEY_COLUMN, id)
            .withString(DATA_COLUMN, data);
        table.putItem(sessionRow);
    }

    public void update(long id, String newData){
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
            .withPrimaryKey(KEY_COLUMN, id)
            .withUpdateExpression("set #sd=:data")
            .withNameMap(new NameMap().with("#sd", DATA_COLUMN))
            .withValueMap(new ValueMap().withString(":data", newData));
        table.updateItem(updateItemSpec);
    }

    public String get(long id) {
        String projection = KEY_COLUMN + "," + DATA_COLUMN;
        Item item = table.getItem(KEY_COLUMN, id, projection, null);
        if (item != null) {
            return item.getString(DATA_COLUMN);
        } else {
            return null;
        }
    }

    public void delete(long id) {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withPrimaryKey(KEY_COLUMN, id);
        table.deleteItem(deleteItemSpec);
    }
}
