package edu.csumb.dynamodbexample.rest;

import com.amazonaws.services.dynamodbv2.document.Item;
import edu.csumb.dynamodbexample.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by nathanieldavidson on 9/27/16.
 */

@Path("/")
public class RestExecutor {

    @GET
    @Path("/executor")
    public Response index(){
        String html = "";
        DBConnector.initiate(); // connect to the dynamodb server
        if(CreateExamples.createTable("test")){
            html += "Table Created!<br/>";
        }else {
            html += "Table Already Exists!<br/>";
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
            html += "Created row!<br/>";
        }else{
            html += "Failed to create row!<br/>";
        }

        Item item = SelectExamples.get("test", 1); // fetch from the table "test" with the id = 1

        html += "Data from db: "+item.get("dataStored")+"<br/>"; // print out ping

        // Update row!

        if(UpdateExamples.update("test", 1, "pong")){
            html += "Updated Row!<br/>";

            item = SelectExamples.get("test", 1); // read from the DB again to check the value of dataStored
            html += "New data value from db: "+item.get("dataStored")+"<br/>";
        }else{
            html += "Failed to update row!<br/>";
        }


        if(DeleteExamples.delete("test", 1)) { // Now delete the entry
            html += "Deleted row!<br/>";
        }else{
            html += "Failed to delete row!<br/>";
        }

        item = SelectExamples.get("test", 1);
        if(item==null){
            html += "Row does not exist!<br/>";
        }else{
            html += "Woops, row was not deleted!<br/>";
        }

        DBConnector.close();
        return Response.ok(html).build();
    }

}
