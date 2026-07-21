package com.vmware.geode.twitter.analysis.listener;

import com.vmware.geode.twitter.domain.Tweet;
import com.vmware.geode.twitter.domain.TweetSentiment;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DetermineTweetSentimentListenerTest
{
    @Mock
    private Region<String, TweetSentiment> tweet_sentiment_region;

    @Mock
    private Function<Tweet,TweetSentiment> function;
    @Mock
    private EntryEvent<String, Tweet> event;

    private Tweet tweet = JavaBeanGeneratorCreator.of(Tweet.class).create();

    private DetermineTweetSentimentListener subject;

    @BeforeEach
    void setUp()
    {
        subject = new DetermineTweetSentimentListener(tweet_sentiment_region,function);
    }

    @Test
    void afterCreate_Then_SaveSentiment()
    {
        when(event.getNewValue()).thenReturn(tweet);

        subject.afterCreate(event);
        verify(function).apply(any(Tweet.class));
        verify(tweet_sentiment_region).put(anyString(),any());
    }

    @Test
    void afterUpdate_Then_SaveSentiment()
    {
        when(event.getNewValue()).thenReturn(tweet);

        subject.afterUpdate(event);
        verify(function).apply(any(Tweet.class));
        verify(tweet_sentiment_region).put(anyString(),any());
    }
}