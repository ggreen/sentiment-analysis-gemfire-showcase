package com.vmware.geode.twitter.listener;

import com.vmware.geode.twitter.domain.TweetSentiment;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.Operation;
import org.apache.geode.cache.query.CqEvent;
import org.apache.geode.cache.util.CacheListenerAdapter;
import org.apache.geode.cache.util.CqListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * SentimentCacheListenerStompPublisher
 *
 * @author Gregory Green
 */

@Component
public class LiveTweetListenerStompPublisher extends CqListenerAdapter
        //extends CacheListenerAdapter<String, TweetSentiment>
{
    private final SimpMessagingTemplate messageTemplate;
    private final String destination;

    public LiveTweetListenerStompPublisher(SimpMessagingTemplate messageTemplate, @Value("${tweet.stomp.destination}") String destination)
    {
        this.messageTemplate = messageTemplate;
        this.destination = destination;
    }

    public void onEvent(CqEvent cqEvent)
    {
        Operation queryOperation = cqEvent.getQueryOperation();

        if(!queryOperation.isUpdate() && !queryOperation.isCreate())
            return;

        this.messageTemplate.convertAndSend(destination,cqEvent.getNewValue());
    }
}
