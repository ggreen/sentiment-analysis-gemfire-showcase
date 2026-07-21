package com.vmware.geode.twitter.streaming.source.conversion;

import com.vmware.geode.twitter.domain.Tweet;
import com.vmware.geode.twitter.streaming.source.data.TwitterStreamRecord;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.util.function.Function;

/**
 * ToTweetFunction
 *
 * @author Gregory Green
 */
@Component
public class ToTweetFunction implements Function<String, Tweet>
{
    private final JsonMapper objectMapper;
    public ToTweetFunction()
    {
        this.objectMapper = JsonMapper.builder().configure(tools.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
                .build();
//        objectMapper = new ObjectMapper(tools.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false).
//        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
            var record = this.objectMapper.readValue(tweetDataJsonText, TwitterStreamRecord.class);
            return record.getData();
    }
}
