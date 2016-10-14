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
            if (new File("./access.ini").exists()) {
                Properties prop = new Properties();
                prop.load(new FileInputStream("./access.ini"));
                String access = prop.getProperty("Access");
                String secret = prop.getProperty("Secret");
                client = new AmazonDynamoDBClient(new BasicAWSCredentials(access, secret));
                client.setRegion(Region.US_West.toAWSRegion());
                dynamoDB = new DynamoDB(client);
            } else {
                String access = System.getProperty("Access");
                String secret = System.getProperty("Secret");
                if (access == null || secret == null || access.equals("") || secret.equals("")) {
                    System.exit(-1);
                } else {
                    client = new AmazonDynamoDBClient(new BasicAWSCredentials(access, secret));
                    client.setRegion(Region.US_West.toAWSRegion());
                }
                dynamoDB = new DynamoDB(client);
            }
        }catch (IOException e){

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