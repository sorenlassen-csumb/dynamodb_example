Circle CI build testing on diff

--Testing the Build--
1s

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
