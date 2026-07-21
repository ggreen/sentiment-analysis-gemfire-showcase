package com.vmware.geode.twitter.service.polarity;

import com.vmware.geode.twitter.domain.Sentiments;
import com.vmware.geode.twitter.domain.Tweet;
import nyla.solutions.core.util.Digits;
import nyla.solutions.core.util.Text;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * PolarityComputeService
 *
 * @author Gregory Green
 */
@Service
public class PolarityComputeService
{

    private final Region<String,Tweet> tweets;
    private Digits digits = new Digits();


    public PolarityComputeService(@Qualifier("tweets") Region tweets)
    {
        this.tweets = tweets;
    }


    public void polarity_compute(Sentiments sentiments)
    {
        String[] texts = sentiments.getData();
        for (int i = 0; i < texts.length; i++)
        {
            Tweet tweet = toTweet(texts[i]);
            saveTweet(tweet);
        }
    }

    public void saveTweet(Tweet tweet)
    {
        this.tweets.put(tweet.getId(), tweet);
    }

    protected Tweet toTweet(String sentiment)
    {
        Tweet tweet = new Tweet();
        tweet.setText(sentiment);
        tweet.setId(Text.generateId());

        return tweet;
    }
}
