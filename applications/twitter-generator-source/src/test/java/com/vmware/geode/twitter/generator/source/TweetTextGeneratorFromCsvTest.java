package com.vmware.geode.twitter.generator.source;

import com.vmware.geode.twitter.domain.Tweet;
import nyla.solutions.core.io.IO;
import org.apache.geode.cache.Region;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TweetTextGeneratorFromCsvTest
{
    @Mock
    private Region<String, Tweet> rawTweet;
    @Mock
    private Resource resource;

    @Test
    void generateTweet() throws IOException
    {
        var csv = Paths.get("src/main/resources/generator-TWEETS/tweets.csv").toFile();
        InputStream inputStream = IO.getFileInputStream(csv.getAbsolutePath());
        when(resource.getInputStream()).thenReturn(inputStream);
        var subject = new TweetTextGeneratorFromCsv(rawTweet,resource);

        subject.generate();
        verify(rawTweet,atLeastOnce()).put(anyString(),any(Tweet.class));
    }
}