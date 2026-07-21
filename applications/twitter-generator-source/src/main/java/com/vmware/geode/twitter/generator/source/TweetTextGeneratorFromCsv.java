package com.vmware.geode.twitter.generator.source;

import com.vmware.geode.twitter.domain.Tweet;
import nyla.solutions.core.io.IO;
import nyla.solutions.core.io.csv.CsvReader;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * TweetTextGenerator
 *
 * @author Gregory Green
 */
@Component
public class TweetTextGeneratorFromCsv
{
    private final Region<String, Tweet> tweetsRegion;
    private final Resource csv;

    public TweetTextGeneratorFromCsv(
            @Qualifier("tweets") Region<String, Tweet> tweetRegion,
            @Value("classpath:generator-TWEETS/tweets.csv")
            Resource csv) throws IOException
    {
        this.tweetsRegion = tweetRegion;
        this.csv = csv;
    }

    @Scheduled(fixedDelay = 5000)
    public void generate() throws IOException
    {
            CsvReader reader = new CsvReader(IO.toReader(csv.getInputStream()));
            for (List<String> row : reader) {
                tweetsRegion.put(row.get(0),
                        Tweet.builder()
                             .id(row.get(0))
                             .text(row.get(1)).build());
            }
    }
}
