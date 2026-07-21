package com.vmware.geode.twitter.streaming.source.conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.geode.twitter.domain.Tweet;
import com.vmware.geode.twitter.streaming.source.data.TwitterStreamRecord;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * ToTweetFunction
 *
 * @author Gregory Green
 */
@Component
public class ToTweetFunction implements Function<String, Tweet>
{
    private final ObjectMapper objectMapper;
    public ToTweetFunction()
    {
        objectMapper = new ObjectMapper().
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    /**
     * Applies this function to the given argument.
     *
     * @param tweetDataJsonText data Json Tweet
     * @return the function result
     */
    @Override
    public Tweet apply(String tweetDataJsonText)
    {
        try {
            var record = this.objectMapper.readValue(tweetDataJsonText, TwitterStreamRecord.class);
            return record.getData();
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException("ERROR:"+e+" tweet:"+tweetDataJsonText,e);
        }
    }
}
