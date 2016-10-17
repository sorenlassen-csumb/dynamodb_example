package edu.csumb.dynamodbexample;

import com.amazonaws.services.dynamodbv2.document.Item;

import javax.ws.rs.core.Response;
import java.io.PrintStream;

/**
 * Created by ndavidson on 9/21/16.
 */

public class Executor {
    public static void execute(PrintStream out){
        DBController controller = new DBController(); // connect to the dynamodb server
        if (CreateExamples.createTable("test", controller)) {
            out.println("Table Created!");
        } else {
            out.println("Table Already Exists!");
        }

    /*
                                         _
                                        | |
                      ___ _ __ _   _  __| |
                     / __| '__| | | |/ _` |
                    | (__| |  | |_| | (_| |
                     \___|_|   \__,_|\__,_|

    */

        if (CreateExamples.createRow("test", 1, "ping", controller)) { // create row in tablename = test, with id=1 and dataToStore=ping
            out.println("Created row!");
        } else {
            out.println("Failed to create row!");
        }

        Item item = SelectExamples.get("test", 1, controller); // fetch from the table "test" with the id = 1

        out.println("Data from db: " + item.get("dataStored")); // print out ping

        // Update row!

        if (UpdateExamples.update("test", 1, "pong", controller)) {
            out.println("Updated Row!");

            item = SelectExamples.get("test", 1, controller); // read from the DB again to check the value of dataStored
            out.println("New data value from db: " + item.get("dataStored"));
        } else {
            out.println("Failed to update row!");
        }


        if (DeleteExamples.delete("test", 1, controller)) { // Now delete the entry
            out.println("Deleted row!");
        } else {
            out.println("Failed to delete row!");
        }

        item = SelectExamples.get("test", 1, controller);
        if (item == null) {
            out.println("Row does not exist!");
        } else {
            out.println("Woops, row was not deleted!");
        }

        controller.close();
    }

    public static void main(String[] args){
        execute(System.out);
    }
}
