package com.vmware.geode.twitter.streaming;

import com.vmware.geode.twitter.domain.Tweet;
import io.pivotal.services.dataTx.geode.client.GeodeClient;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

	@Bean(name = "tweets")
	public Region<String, Tweet> getTweetRates(GeodeClient geodeClient)
	{
		return geodeClient.getRegion("tweets");
	}//------------------------------------------------	


}
