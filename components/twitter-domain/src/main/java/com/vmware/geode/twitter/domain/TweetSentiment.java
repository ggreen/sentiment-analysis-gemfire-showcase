package com.vmware.geode.twitter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TweetSentiment
 *
 * @author Gregory Green
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TweetSentiment
{
    private Tweet tweet;
    private SentimentType sentimentType;
    private double polarity;
}
