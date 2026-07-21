package com.vmware.geode.twitter.service;

import com.vmware.geode.twitter.domain.SentimentStats;

/**
 * TweetService
 *
 * @author Gregory Green
 */
public interface TweetService
{
    int countTweets();

    SentimentStats findSentimentStats();
}
