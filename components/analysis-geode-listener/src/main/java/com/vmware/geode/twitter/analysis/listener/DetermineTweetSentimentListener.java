package com.vmware.geode.twitter.analysis.listener;

import com.vmware.geode.twitter.domain.Tweet;
import com.vmware.geode.twitter.domain.TweetSentiment;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.util.CacheListenerAdapter;

import java.util.function.Function;

/**
 * DetermineTweetSentimentListener
 *
 * @author Gregory Green
 */
public class DetermineTweetSentimentListener extends CacheListenerAdapter<String, Tweet>
{
    private final Region<String, TweetSentiment> tweet_sentiment_region;
    private final Function<Tweet, TweetSentiment> toTweetSentiment;

    public DetermineTweetSentimentListener(Region<String, TweetSentiment> tweet_sentiment_region, Function<Tweet,
            TweetSentiment> toTweetSentiment)
    {
        this.tweet_sentiment_region = tweet_sentiment_region;
        this.toTweetSentiment = toTweetSentiment;
    }

    @Override
    public void afterCreate(EntryEvent<String, Tweet> event)
    {
        this.processEvent(event);
    }

    @Override
    public void afterUpdate(EntryEvent<String, Tweet> event)
    {
        this.processEvent(event);
    }

    private void processEvent(EntryEvent<String, Tweet> event)
    {
        var tweet = event.getNewValue();
        var tweetSentiment = toTweetSentiment.apply(tweet);

        this.tweet_sentiment_region.put(tweet.getId(),tweetSentiment);
    }
}
