package com.vmware.geode.twitter.streaming.source.conversion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToTweetFunctionTest
{

    @Test
    void apply()
    {
        String expectedText = "Hello";
        String tweetJsonText = "{\"data\":{\"id\":\"1497310240282398722\",\"text\":\""+expectedText+"\"}," +
                "\"matching_rules\":[{\"id\":\"1477262727555031048\",\"tag\":\"\"}]}";

        var subject = new ToTweetFunction();
        var actual = subject.apply(tweetJsonText);
        assertNotNull(actual);
        assertEquals("1497310240282398722",actual.getId());
        assertEquals(expectedText,actual.getText());
    }
}