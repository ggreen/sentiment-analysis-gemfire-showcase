package com.vmware.geode.twitter.listener;

import com.vmware.geode.twitter.service.polarity.PolarityComputeService;
import org.apache.geode.cache.EntryEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TweetTextCacheListenerTest
{
    @Mock
    private PolarityComputeService polarityComputeService;

    @Mock
    private EntryEvent<String, String> event;

    @Test
    void afterCreate_WhenTweet_ThenGenerate()
    {
        var subject = new TweetTextCacheListener(polarityComputeService);

        var tweet = "Hello world";
        when(event.getNewValue()).thenReturn(tweet);
        subject.afterCreate(event);

        verify(polarityComputeService).polarity_compute(any());
    }

    @Test
    void afterUpdate_WhenTweet_ThenGenerate()
    {
        var subject = new TweetTextCacheListener(polarityComputeService);

        var tweet = "Hello world";
        when(event.getNewValue()).thenReturn(tweet);
        subject.afterUpdate(event);

        verify(polarityComputeService).polarity_compute(any());
    }
}