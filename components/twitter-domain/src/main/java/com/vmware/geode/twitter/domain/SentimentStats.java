package com.vmware.geode.twitter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * data: {\"tweetRate\":\""+r.nextInt(23)+"\", \"avgPolarity\": \""+r.nextInt(5)+"\"}
 * @author Gregory Green
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SentimentStats
{
	private double avgPolarity;
	private int tweetRate;
}
