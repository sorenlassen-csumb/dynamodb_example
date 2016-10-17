package edu.csumb.dynamodbexample;

import java.io.PrintStream;

/**
 * Created by ndavidson on 9/21/16.
 */

public class Executor {
    private static final String TABLE_NAME = "test";

    public static void execute(PrintStream out) {
        DBController controller = new DBController(); // connect to the dynamodb server

        IdDataTable table = IdDataTable.createTable(TABLE_NAME, controller);
        if (table != null) {
            out.println("Table Created!");
        } else {
            table = IdDataTable.openTable(TABLE_NAME, controller);
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

        table.put(1, "ping"); // create row with id=1 and data=ping
        out.println("Created row!");

        String data = table.get(1); // fetch with id=1
        out.println("Data from db: " + data); // print out ping

        // Update row!

        table.update(1, "pong");
        out.println("Updated Row!");

        data = table.get(1); // read from the DB again to check the value of dataStored
        out.println("New data value from db: " + data);

        table.delete(1); // Now delete the entry
        out.println("Deleted row!");

        data = table.get(1);
        if (data == null) {
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
