# Twitter Demo PCC Services

This web application is a Data Science based demonstration for Twitter Feeds.
It uses Apache Gedoe to store and aggregate tweets.
It calculates tweets per second using technologies such
as  Apache Geode continuous queries and HTTP Server Side Events.


The current version uses stubbed Java based Spring Boot based web 
services to simulate twitter feeds, popularity, rates, etc.
Also, the application has PCC/Gemfire enabled in embedded mode.

Once access to a PCC service is available, we can be integrate the connection to 
a centralized PCC based cluster.

Still need to work on the module to write twitter feeds data to PCC, that the web 
UI application would read/analysis.


## Initialize Gfsh

	
	gfsh
	
	gfsh>connect --use-http --url=http://gemfire-f0850d0a-bfe3-4165-9cfb-586127874250.run.pez.pivotal.io/gemfire/v1 --user=cluster_operator
	password: *********************



 
create region --name=tweets --type=PARTITION
create region --name=tweet_rates --type=REPLICATE



# Rest End point

Use the following  URL REST endpoint to push tweets to PCC in the Pivotal PEZ environment


	http://gedi-geode-extensions-rest.cfapps.pez.pivotal.io



Post JSON objects to the following end point

	http://gedi-geode-extensions-rest.cfapps.pez.pivotal.io/region/tweets/key

Example JSON


	{
	  "tweet" : "Hello world",
	  "polarity": 0.5
	}

