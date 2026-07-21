package com.vmware.geode.twitter.repository;

import com.vmware.geode.twitter.domain.SentimentStats;

/**
 * SentimentStatsRepository
 *
 * @author Gregory Green
 */
public interface SentimentStatsRepository
{
    SentimentStats findSentimentStats();
}
