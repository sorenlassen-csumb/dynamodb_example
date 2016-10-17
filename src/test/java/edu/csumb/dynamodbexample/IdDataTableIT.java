package edu.csumb.dynamodbexample;

import static org.junit.Assert.*;

import org.junit.*;

public class IdDataTableIT {
    private static final String TABLE_NAME = "movies_dev";

    private DBController controller;
    private IdDataTable table;

    @Before
    public void connect(){
        controller = new DBController();
        // just in case it's not deleted:
        IdDataTable.openTable(TABLE_NAME, controller).deleteTable();
        table = IdDataTable.createTable(TABLE_NAME, controller);
    }

    @After
    public void disconnect(){
        table.deleteTable();
        controller.close();
    }

    /*
        Create movie and then check to see if the movie exists by comparing dataStored
     */
    @Test
    public void createAndVerifyMovie(){
        table.put(2, "Frozen");

        String data = table.get(2);

        assertEquals("Frozen", data);
    }

    /*
        Create movie, then delete the movie, then make sure that the row does not exist
     */
    @Test
    public void deletedMovie(){
        table.put(3, "The Accountant");

        table.delete(3);

        assertNull(table.get(3));
    }

    /*
        Create movie entry, update dataStored, make sure the update held
     */
    @Test
    public void updateMovieRecordConfirm(){
        table.put(4, "Terminator");

        table.update(4, "Terminator 2");

        String data = table.get(4);
        assertEquals("Terminator 2", data);
    }
}
