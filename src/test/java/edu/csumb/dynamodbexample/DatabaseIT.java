package edu.csumb.dynamodbexample;

import com.amazonaws.services.dynamodbv2.document.Item;
import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by nathanieldavidson on 10/13/16.
 */
public class DatabaseIT {
    private DBController controller;
    @Before
    public void ConnectToDB() throws IOException{
        controller = new DBController();
        DeleteExamples.deleteTable("movies_dev", controller); // just in case its not deleted
        CreateExamples.createTable("movies_dev", controller); // create new fresh table
    }
    @After
    public void disconnect(){
        DeleteExamples.deleteTable("movies_dev", controller); // delete table
    }

    /*
        Create movie wait for confirmation from AWS
     */
    @Test
    public void createNewMovie(){
        assertTrue(CreateExamples.createRow("movies_dev", 1, "Deadpool", controller));
    }


    /*
        Create movie and then check to see if the movie exists by comparing dataStored
     */
    @Test
    public void createAndVerifyMovie(){
        CreateExamples.createRow("movies_dev", 2, "Frozen", controller);

        Item item = SelectExamples.get("movies_dev", 2, controller);

        assertTrue(item.get("dataStored").equals("Frozen"));
    }

    /*
        Create movie, then delete the movie, then make sure that the row does not exist
     */
    @Test
    public void deletedMovie(){
        CreateExamples.createRow("movies_dev", 3, "The Accountant", controller);

        DeleteExamples.delete("movies_dev", 3, controller);

        assertNull(SelectExamples.get("movies_dev", 3, controller));
    }

    /*
        Create movie entry, update dataStored, make sure the update held
     */
    @Test
    public void updateMovieRecordConfirm(){
        CreateExamples.createRow("movies_dev", 4, "Terminator", controller);

        UpdateExamples.update("movies_dev", 4, "Terminator 2", controller);

        Item item = SelectExamples.get("movies_dev", 4, controller);
        assertNotNull(item);
        assertTrue(item.get("dataStored").equals("Terminator 2"));
    }
}