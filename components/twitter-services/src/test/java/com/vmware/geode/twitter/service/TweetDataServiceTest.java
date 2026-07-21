package com.vmware.geode.twitter.service;

import com.vmware.geode.twitter.domain.SentimentStats;
import com.vmware.geode.twitter.domain.TweetSentiment;
import com.vmware.geode.twitter.repository.SentimentStatsRepository;
import com.vmware.geode.twitter.service.TweetDataService;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TweetDataServiceTest
{
    @Mock
    private Region<String, TweetSentiment> tweetSentimentRegion;


    @Mock
    private SentimentStatsRepository sentimentStatsRepository;

    private TweetDataService subject;

    @BeforeEach
    void setUp()
    {
        subject = new TweetDataService(tweetSentimentRegion,sentimentStatsRepository);
    }

    @Test
    void count()
    {
        int expected = 10;
        when(tweetSentimentRegion.sizeOnServer()).thenReturn(expected);

        assertEquals(expected,subject.countTweets());
    }

    @Test
    void findSentimentStats()
    {
        SentimentStats expected = JavaBeanGeneratorCreator.of(SentimentStats.class).create();
        when(sentimentStatsRepository.findSentimentStats()).thenReturn(expected);
        assertEquals(expected,subject.findSentimentStats());
    }

    @Test
    void findSentimentStats_When_Empty_Then_Zeros()
    {
        SentimentStats expected = new SentimentStats(0,0);
        assertEquals(expected,subject.findSentimentStats());
    }
}