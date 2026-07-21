package com.vmware.geode.twitter;

import com.vmware.geode.twitter.domain.TweetSentiment;
import com.vmware.geode.twitter.listener.LiveTweetListenerStompPublisher;
import io.pivotal.services.dataTx.geode.client.GemFireJmxClient;
import io.pivotal.services.dataTx.geode.client.GeodeClient;
import io.pivotal.services.dataTx.geode.io.QuerierService;
import nyla.solutions.core.patterns.jmx.JMX;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.query.*;
import org.apache.geode.management.DistributedRegionMXBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeodeConfig
{
	@Value("${geode.locator.jmx.hostname:127.0.0.1}")
	private String geodeLocatorJmxHostname = "127.0.0.1";

	@Value("${geode.locator.jmx.port:1099}")
	private int geodeLocatorJmxPort;

	@Value("${geode.locator.jmx.user:admin}")
	private String geodeUser;

	@Value("${geode.locator.jmx.password:admin}")
	private char[] geodePassword = "".toCharArray();

	@Value("${spring.application.name:twitter-analysis-app}")
	private String applicationName;

	@Value("${tweet.sentiment.continuous.query}")
	private String continuousQuery;

	@Bean
	GeodeClient getGeodeClient()
	{

		return GeodeClient.connect();
	}

	@Bean
	public QuerierService querierService(GeodeClient geodeClient) throws Exception {

		return geodeClient.getQuerierService();
	}

	@Bean(name = "gemfireCache")
    public ClientCache getGemfireClientCache(GeodeClient geodeClient) throws Exception {
		
		 return geodeClient.getClientCache();
    }


	@Bean(name = "tweets")
	public Region<String, TweetSentiment> tweets(GeodeClient geodeClient)
	{
		return geodeClient.getRegion("tweets");
	}

	@Bean(name = "tweet_sentiments")
	public Region<String, TweetSentiment> tweet_sentiments(GeodeClient geodeClient, LiveTweetListenerStompPublisher liveTweetListenerStompPublisher)
	throws CqException, CqExistsException, RegionNotFoundException
	{
		Region<String, TweetSentiment>  region = geodeClient.getRegion("tweet_sentiments");
		QueryService queryService = geodeClient.getClientCache().getQueryService();
		CqAttributesFactory cqf = new CqAttributesFactory();
		cqf.addCqListener(liveTweetListenerStompPublisher);
		CqAttributes cqa = cqf.create();
		CqQuery cqQuery = queryService.newCq(applicationName, continuousQuery, cqa);
		cqQuery.execute();
		region.registerInterestForAllKeys();
		return region;
	}

	@Bean
	public JMX jmx()
	{

		return JMX.connect(geodeLocatorJmxHostname,geodeLocatorJmxPort,geodeUser,geodePassword);
	}
	@Bean
	public DistributedRegionMXBean tweetsRegionMXBean(JMX jmx)
	{
         return  GemFireJmxClient.getRegionMBean("tweets",jmx);
	}

}
