package edu.csumb.dynamodbexample.rest;

import edu.csumb.dynamodbexample.Executor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by nathanieldavidson on 9/27/16.
 */

@Path("/")
public class RestExecutor {
    @GET
    @Path("/executor")
    public Response index(){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Executor.execute(new PrintStream(out));
        String lineSeparator = System.getProperty("line.separator", "\n");
        String html = out.toString().replace(lineSeparator, "<br/>\n");
        return Response.ok(html).build();
    }
}
