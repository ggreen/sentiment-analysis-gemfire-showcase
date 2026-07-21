package com.vmware.geode.twitter.processor;

import com.vmware.geode.twitter.analysis.TweetSentimentAnalysis;
import com.vmware.geode.twitter.analysis.listener.DetermineTweetSentimentListener;
import com.vmware.geode.twitter.domain.Tweet;
import com.vmware.geode.twitter.domain.TweetSentiment;
import io.pivotal.services.dataTx.geode.client.GeodeClient;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class GeodeConfig
{
	@Bean
	GeodeClient getGeodeClient()
	{

		return GeodeClient.connect();
	}

	@Bean(name = "gemfireCache")
    public ClientCache getGemfireClientCache(GeodeClient geodeClient) throws Exception {
		
		 return geodeClient.getClientCache();
    }

	@Bean
	public TweetSentimentAnalysis tweetSentimentAnalysis()
	{
		return new TweetSentimentAnalysis();
	}

	@Bean
	public Function<Tweet, TweetSentiment> toTweetSentiment(TweetSentimentAnalysis tweetSentimentAnalysis)
	{
		return t ->  tweetSentimentAnalysis.analyzeTweet(t);
	}

	@Bean
    public DetermineTweetSentimentListener listener(GeodeClient geodeClient, Function<Tweet, TweetSentiment> toTweetSentiment)
	{
		Region<String, TweetSentiment> tweet_sentiment_region = getGeodeClient().getRegion("tweet_sentiments");
		return new DetermineTweetSentimentListener(tweet_sentiment_region,toTweetSentiment);
	}

	@Bean(name = "tweet_sentiments")
	public Region<String,TweetSentiment> tweet_sentiments(GeodeClient geodeClient)
	{
		Region<String,TweetSentiment> region = geodeClient.getRegion("tweet_sentiments");
		return region;
	}

	@Bean(name = "tweets")
	public Region<String,Tweet> tweets(GeodeClient geodeClient, DetermineTweetSentimentListener listener)
	{
		Region<String,Tweet> region = geodeClient.getRegion("tweets");
		region.registerInterestForAllKeys();
		region.getAttributesMutator().addCacheListener(listener);
		return region;
	}

}
