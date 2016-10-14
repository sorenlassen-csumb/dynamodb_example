package edu.csumb.dynamodbexample;

import com.amazonaws.services.dynamodbv2.document.Item;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by nathanieldavidson on 10/13/16.
 */
public class DatabaseIT {
    private DBController controller;
    @Before
    public void ConnectToDB(){
        try {
            controller = new DBController();
        }catch(Exception e){
        }
        CreateExamples.createTable("movies", controller);
    }
    @After
    public void disconnect(){
        assertTrue(DeleteExamples.deleteTable("movies", controller));
    }

    @Test
    public void createNewMovie(){
        assertTrue(CreateExamples.createRow("movies", 1, "Deadpool", controller));
    }

    @Test
    public void createAndVerifyMovie(){
        CreateExamples.createRow("movies", 2, "Frozen", controller);

        Item item = SelectExamples.get("movies", 2, controller);

        assertTrue(item.get("dataStored").equals("Frozen"));
    }

    @Test
    public void deletedMovie(){
        CreateExamples.createRow("movies", 3, "The Accountant", controller);

        DeleteExamples.delete("movies", 3, controller);

        assertEquals(SelectExamples.get("movies", 3, controller), null);
    }
}
