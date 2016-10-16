package edu.csumb.dynamodbexample;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.model.Region;

import java.io.File;

/**
 * Created by ndavidson on 9/21/16.
 */
public class DBController {
    private static final String CREDENTIALS_FILE_PATH = "./access.ini";

    private DynamoDB dynamoDB;
    private AmazonDynamoDB client;

    private static AWSCredentialsProvider getCredentialsProvider() {
        if (new File(CREDENTIALS_FILE_PATH).exists()) {
            // reads properties accessKey, secretKey
            return new PropertiesFileCredentialsProvider(CREDENTIALS_FILE_PATH);
        } else {
            // first looks at env vars AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY
            // then sys props aws.accessKeyId, aws.secretKey
            // then file ~/.aws/credentials
            // see http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
            return new DefaultAWSCredentialsProviderChain();
        }
    }

    public DBController(){
        client = new AmazonDynamoDBClient(getCredentialsProvider());
        client.setRegion(Region.US_West.toAWSRegion()); // US West
        dynamoDB = new DynamoDB(client);
    }

    public void close(){
        dynamoDB.shutdown();
        client.shutdown();
    }

    public DynamoDB getDynamoDB() {
        return dynamoDB;
    }
}
