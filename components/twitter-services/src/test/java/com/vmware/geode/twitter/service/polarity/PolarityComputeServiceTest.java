package com.vmware.geode.twitter.service.polarity;

import com.vmware.geode.twitter.domain.Tweet;
import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PolarityComputeServiceTest
{
    @Mock
    private Region<String, Tweet > tweets;

    private PolarityComputeService subject;

    @BeforeEach
    void setUp()
    {
        subject = new PolarityComputeService(tweets);
    }

    @Test
    void toTweet()
    {
        String expected = "sentiment";
        var actual = subject.toTweet(expected);
        assertEquals(expected,actual.getText());
        assertNotNull(actual.getId());
    }
}