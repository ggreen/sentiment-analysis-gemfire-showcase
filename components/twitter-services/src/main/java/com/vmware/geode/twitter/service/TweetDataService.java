package com.vmware.geode.twitter.service;

import com.vmware.geode.twitter.domain.SentimentStats;
import com.vmware.geode.twitter.domain.TweetSentiment;
import com.vmware.geode.twitter.repository.SentimentStatsRepository;
import io.pivotal.services.dataTx.geode.io.QuerierService;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * TweetDataService
 *
 * @author Gregory Green
 */
@Service
public class TweetDataService implements TweetService
{
    private static final String TWEETS_RATE_KEY = "tweet_rates";

    private final Region<String, TweetSentiment> tweetSentimentRegion;
    private final SentimentStatsRepository sentimentStatsRepository;
    private QuerierService queryService;

    public TweetDataService(@Qualifier("tweet_sentiments") Region<String, TweetSentiment> tweetSentimentRegion,
                            SentimentStatsRepository sentimentStatsRepository)
    {
        this.tweetSentimentRegion = tweetSentimentRegion;
        this.sentimentStatsRepository = sentimentStatsRepository;
    }

    public int countTweets()
    {
        return this.tweetSentimentRegion.sizeOnServer();
    }

    @Override
    public SentimentStats findSentimentStats()
    {
        var sentimentStats = this.sentimentStatsRepository.findSentimentStats();
        if(sentimentStats == null)
            return new SentimentStats();

        return sentimentStats;


    }


}
