DynamoDB Examples

#Installation of DynamoDB Local 

.tar.gz format: http://dynamodb-local.s3-website-us-west-2.amazonaws.com/dynamodb_local_latest.tar.gz

.zip format: http://dynamodb-local.s3-website-us-west-2.amazonaws.com/dynamodb_local_latest.zip

Execute once unzipped / untarred 

"java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb -dbPath ."


#Running This Example (Application)

Clone the repo

Run this "mvn compile exec:java -Dexec.mainClass=edu.csumb.dynamodbexample.Executor"

#Running This Example (AWS Beanstalk DynamoDB)

Clone the rep

Run "mvn package"

Create new beanstalk, upload the .war in /target

Open environment variables, enter secret and access tokens for your aws


#Running This Locally

Clone the repo

Run "mvn jetty:run" 
