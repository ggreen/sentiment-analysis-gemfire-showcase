package com.vmware.geode.twitter.generator;

import com.vmware.geode.twitter.domain.Tweet;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeodeConfig
{



	@Bean(name = "tweets")
	public Region<String, Tweet> getTweetRates(ClientCache geodeClient)
	{
		return geodeClient.getRegion("tweets");
	}//------------------------------------------------	


}
