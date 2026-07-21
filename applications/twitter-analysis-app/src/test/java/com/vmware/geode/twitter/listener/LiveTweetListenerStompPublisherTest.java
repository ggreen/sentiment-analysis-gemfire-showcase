package com.vmware.geode.twitter.listener;

import com.vmware.geode.twitter.domain.TweetSentiment;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.Operation;
import org.apache.geode.cache.query.CqEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LiveTweetListenerStompPublisherTest
{
    @Mock
    private SimpMessagingTemplate messageTemplate;

    @Mock
    private CqEvent event;
    private TweetSentiment eventValue = JavaBeanGeneratorCreator.of(TweetSentiment.class).create();
    private String destination = "test";

    @Test
    void afterCreate_then_sendMessage()
    {
        Operation newOps = Operation.CREATE;
        when(event.getQueryOperation()).thenReturn(newOps);
        when(event.getNewValue()).thenReturn(eventValue);
        var subject = new LiveTweetListenerStompPublisher(messageTemplate, destination);

        subject.onEvent(event);
        verify(messageTemplate).convertAndSend(anyString(),any(TweetSentiment.class));
    }

    @Test
    void afterUpdate_then_sendMessage()
    {
        Operation updateOps = Operation.CREATE;
        when(event.getQueryOperation()).thenReturn(updateOps);
        when(event.getNewValue()).thenReturn(eventValue);
        var subject = new LiveTweetListenerStompPublisher(messageTemplate, destination);

        subject.onEvent(event);
        verify(messageTemplate).convertAndSend(anyString(),any(TweetSentiment.class));
    }

    @Test
    void afterDelete_then_DoNot_sendMessage()
    {
        Operation deleteOps = Operation.DESTROY;
        when(event.getQueryOperation()).thenReturn(deleteOps);
        var subject = new LiveTweetListenerStompPublisher(messageTemplate, destination);

        subject.onEvent(event);
        verify(messageTemplate,never()).convertAndSend(anyString(),any(TweetSentiment.class));
    }
}