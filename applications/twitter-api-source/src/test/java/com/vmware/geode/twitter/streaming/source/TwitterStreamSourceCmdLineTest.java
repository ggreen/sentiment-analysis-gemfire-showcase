package com.vmware.geode.twitter.streaming.source;

import com.vmware.geode.twitter.domain.Tweet;
import com.vmware.geode.twitter.service.polarity.PolarityComputeService;
import nyla.solutions.core.exception.FormatException;
import nyla.solutions.core.net.http.Http;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TwitterStreamSourceCmdLineTest
{
    @Mock
    private PolarityComputeService polarityComputeService;

    @Mock
    private Function<String, Tweet> toTweet;

    @Mock
    private Http http;

    private String token = "TOKEN";
    private TwitterStreamSourceCmdLine subject;

    @BeforeEach
    void setUp() throws MalformedURLException
    {
        subject = new TwitterStreamSourceCmdLine(polarityComputeService,http, toTweet,token);
    }

    @Test
    void run() throws Exception
    {
        String tweetJsonText = "{\"data\":{\"id\":\"1497310240282398722\",\"text\":\"RT @PLAISport: ‼️ COMPETITION " +
                "TIME ‼️\\n\\n\uD83D\uDC5F WIR...\\n\\n1️⃣ \uD83D\uDC4D LIKE OUR PAGE…\"}," +
                "\"matching_rules\":[{\"id\":\"1477262727555031048\",\"tag\":\"\"}]}";
        BufferedReader reader = new BufferedReader(new StringReader(tweetJsonText));
        when(http.getWithReader(any())).thenReturn(reader);

        subject.run(null);

        verify(polarityComputeService,atLeastOnce()).saveTweet(any());
    }

    @Test
    void when_JsonException_Then_Skip() throws Exception
    {
        String tweetJsonText = "INVALID";
        BufferedReader reader = new BufferedReader(new StringReader(tweetJsonText));
        when(http.getWithReader(any())).thenReturn(reader);
        when(toTweet.apply(anyString())).thenThrow(FormatException.class);
        var subject = new TwitterStreamSourceCmdLine(polarityComputeService,http, toTweet,token);
        subject.run(null);

        verify(polarityComputeService,never()).saveTweet(any());
    }

    @Test
    void when_Empty_Then_Skip() throws Exception
    {
        String tweetJsonText = " ";
        BufferedReader reader = new BufferedReader(new StringReader(tweetJsonText));
        when(http.getWithReader(any())).thenReturn(reader);
        var subject = new TwitterStreamSourceCmdLine(polarityComputeService,http, toTweet,token);
        subject.run(null);

        verify(toTweet,never()).apply(any());
    }
}