package com.vmware.geode.twitter.repository;

import com.vmware.geode.twitter.domain.SentimentStats;
import io.pivotal.services.dataTx.geode.io.QuerierService;
import org.apache.geode.management.DistributedRegionMXBean;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * SentimentStatsGeodeRepository
 *
 * @author Gregory Green
 */
@Component
public class SentimentStatsGeodeRepository implements SentimentStatsRepository
{
    private final DistributedRegionMXBean tweetsRegionMXBean;
    private final QuerierService queryService;
    private final String avgPolarityQuery = "select avg(sentimentType.ordinal) from /tweet_sentiments";

    public SentimentStatsGeodeRepository(DistributedRegionMXBean tweetsRegionMXBean, QuerierService queryService)
    {
        this.tweetsRegionMXBean = tweetsRegionMXBean;
        this.queryService = queryService;
    }


    @Override
    public SentimentStats findSentimentStats()
    {
        SentimentStats sentimentStats = new SentimentStats();
        sentimentStats.setTweetRate(Float.valueOf(tweetsRegionMXBean.getPutsRate()).intValue());
        Collection<Number> results = queryService.query(avgPolarityQuery);

        if(results != null && !results.isEmpty())
            sentimentStats.setAvgPolarity(results.iterator().next().doubleValue());

        return sentimentStats;
    }
}
