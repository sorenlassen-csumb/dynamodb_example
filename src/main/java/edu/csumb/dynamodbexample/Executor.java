package edu.csumb.dynamodbexample;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;

/**
 * Created by ndavidson on 9/21/16.
 */
public class Executor {
    public static void main(String[] args){
        DBConnector.initiate(); // connect to the dynamodb server
        if(CreateExamples.createTable("test")){
            System.out.println("Table Created!");
        }else {
            System.out.println("Table Already Exists!");
        }

        /*
                                             _
                                            | |
                          ___ _ __ _   _  __| |
                         / __| '__| | | |/ _` |
                        | (__| |  | |_| | (_| |
                         \___|_|   \__,_|\__,_|

        */

        if(CreateExamples.createRow("test", 1, "ping")) { // create row in tablename = test, with id=1 and dataToStore=ping
            System.out.println("Created row!");
        }else{
            System.out.println("Failed to create row!");
        }

        Item item = SelectExamples.get("test", 1); // fetch from the table "test" with the id = 1

        System.out.println("Data from db: "+item.get("dataStored")); // print out ping

        // Update row!

        if(UpdateExamples.update("test", 1, "pong")){
            System.out.println("Updated Row!");

            item = SelectExamples.get("test", 1); // read from the DB again to check the value of dataStored
            System.out.println("New data value from db: "+item.get("dataStored"));
        }else{
            System.out.println("Failed to update row!");
        }


        if(DeleteExamples.delete("test", 1)) { // Now delete the entry
            System.out.println("Deleted row!");
        }else{
            System.out.println("Failed to delete row!");
        }

        item = SelectExamples.get("test", 1);
        if(item==null){
            System.out.println("Row does not exist!");
        }else{
            System.out.println("Woops, row was not deleted!");
        }

        DBConnector.close();
    }
}
