package edu.csumb.dynamodbexample;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
/**
 * Created by ndavidson on 9/21/16.
 */
public class DBConnector {
    private static DynamoDB dynamoDB;

    private static DynamoDBProxyServer server=null;
    private static AmazonDynamoDB client;

    public static void initiate(){
        client = new AmazonDynamoDBClient(new BasicAWSCredentials("access", "secret"));
        // connect to dynamodb local server
        client.setEndpoint("http://localhost:8000");
        dynamoDB = new DynamoDB(client);
    }

    public static void close(){
        dynamoDB.shutdown();
        client.shutdown();
    }

    public static DynamoDB getDynamoDB() {
        return dynamoDB;
    }

    public static DynamoDBProxyServer getServer() {
        return server;
    }
}