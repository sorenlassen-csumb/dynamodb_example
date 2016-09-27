package edu.csumb.dynamodbexample;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.s3.model.Region;

/**
 * Created by ndavidson on 9/21/16.
 */
public class DBConnector {
    private static DynamoDB dynamoDB;
    private static AmazonDynamoDB client;

    public static void initiate(){
        String access = System.getProperty("access");
        String secret = System.getProperty("secret");
        if(access==null || secret==null || access.equals("") || secret.equals("")) {
            client = new AmazonDynamoDBClient(new BasicAWSCredentials("access", "secret"));
            // connect to dynamodb local server
            client.setEndpoint("http://localhost:8000");
        }else{
            client = new AmazonDynamoDBClient(new BasicAWSCredentials(access, secret));
            client.setRegion(Region.US_West.toAWSRegion());
        }
        dynamoDB = new DynamoDB(client);
    }

    public static void close(){
        dynamoDB.shutdown();
        client.shutdown();
    }

    public static DynamoDB getDynamoDB() {
        return dynamoDB;
    }
}