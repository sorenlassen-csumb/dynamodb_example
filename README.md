DynamoDB Examples

#Installation of DynamoDB Local 

.tar.gz format: http://dynamodb-local.s3-website-us-west-2.amazonaws.com/dynamodb_local_latest.tar.gz
.zip format: http://dynamodb-local.s3-website-us-west-2.amazonaws.com/dynamodb_local_latest.zip

Execute once unzipped / untarred "java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb -dbPath ."


#Running This Example
Clone the repo
Run this "mvn compile exec:java -Dexec.mainClass=edu.csumb.dynamodbexample.Executor"
