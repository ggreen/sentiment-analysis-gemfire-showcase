package com.vmware.geode.twitter.controller;

import com.vmware.geode.twitter.domain.SentimentStats;
import com.vmware.geode.twitter.service.TweetService;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TweetSentimentStatsControllerTest
{
    @Mock
    private TweetService service;

    @Mock
    private SseEmitter emitter;
    @Mock
    private Supplier<SseEmitter> supplier;

    @Mock
    private SseEmitter sseEmitter;

    private TweetStatsController subject;


    @BeforeEach
    void setUp()
    {

        subject = new TweetStatsController(service,supplier);
    }

    @Test
    void sentimentStatsSse() throws InterruptedException, IOException
    {
        SentimentStats expected = JavaBeanGeneratorCreator.of(SentimentStats.class).create();
        when(supplier.get()).thenReturn(sseEmitter);
        when(service.findSentimentStats()).thenReturn(expected);

        SseEmitter sseEmitter = subject.sentimentStatsSse();

        Thread.sleep(1000);
        verify(sseEmitter).send(any(SentimentStats.class));
    }

    @Test
    void count()
    {
        int expected = 10;

        when(service.countTweets()).thenReturn(expected);

        assertEquals(expected,subject.count());
    }

    @Test
    void countSse() throws IOException, InterruptedException
    {
        when(supplier.get()).thenReturn(sseEmitter);
        var sseEmitter = subject.countSse();

        Thread.sleep(1000);
        verify(sseEmitter).send(anyInt());
    }
}