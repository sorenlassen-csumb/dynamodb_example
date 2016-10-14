package edu.csumb.dynamodbexample;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.s3.model.Region;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by ndavidson on 9/21/16.
 */
public class DBController {
    private DynamoDB dynamoDB;
    private AmazonDynamoDB client;

    public DBController(){
        try {

            String access="none";
            String secret="none";
            if (new File("./access.ini").exists()) { //file stored properties

                Properties prop = new Properties();
                prop.load(new FileInputStream("./access.ini"));
                access = prop.getProperty("Access"); //from access.ini
                secret = prop.getProperty("Secret"); //from access.ini

            } else { // environment variables

                access = System.getenv("Access");
                secret = System.getenv("Secret");

            }

            client = new AmazonDynamoDBClient(new BasicAWSCredentials(access, secret));
            client.setRegion(Region.US_West.toAWSRegion()); // US West
            dynamoDB = new DynamoDB(client);

        } catch (IOException e){

        }
    }

    public void close(){
        dynamoDB.shutdown();
        client.shutdown();
    }

    public DynamoDB getDynamoDB() {
        return dynamoDB;
    }
}