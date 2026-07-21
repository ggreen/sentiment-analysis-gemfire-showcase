# Twitter Demo PCC Services

This web application is a Data Science based demonstration for Twitter Feeds.
It uses Pivotal Cloud Cache to store and aggregate tweets.
It calculates tweets per second using technologies such
as  PCC/Apache Geode continuous queries and HTTP Server Side Events.


Java version of the web UI application has been deployed to PWS.

[http://twitterdemopccservices.cfapps.io/](http://twitterdemopccservices.cfapps.io/)

The current version uses stubbed Java based Spring Boot based web 
services to simulate twitter feeds, popularity, rates, etc.
Also, the application has PCC/Gemfire enabled in embedded mode.

Once access to a PCC service is available, we can be integrate the connection to 
a centralized PCC based cluster.

Still need to work on the module to write twitter feeds data to PCC, that the web 
UI application would read/analysis.

## Building

	cd unified-demonstration-sentiment_01/pccServices/twitterDemoPccServices
	mvn clean package

##PWS Deployment 

	cf login
	cf push -p target/twitterDemoPccServices-0.0.1-SNAPSHOT.jar

 
