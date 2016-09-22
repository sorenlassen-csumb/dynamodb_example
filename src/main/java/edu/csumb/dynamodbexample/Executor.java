package edu.csumb.dynamodbexample;

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
        if(SelectExamples.get("test", 1)){ // create row in tablename = test, with id=1 and dataToStore=pop
            System.out.println("failed");
        }
    }
}
