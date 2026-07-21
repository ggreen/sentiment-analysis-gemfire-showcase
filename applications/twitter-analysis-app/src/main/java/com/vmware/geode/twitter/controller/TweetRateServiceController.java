package com.vmware.geode.twitter.controller;

import com.vmware.geode.twitter.domain.SentimentStats;
import com.vmware.geode.twitter.domain.TweetSentiment;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Queue;

/**
 * TweetRateService
 *
 * @author Gregory Green
 */
//@Service
public class TweetRateServiceController
{
    private final Region<String, SentimentStats> tweet_rates;
    private final Region<String,TweetSentiment> tweetSentimentRegion;

    private final Queue<TweetSentiment> liveTweetsQueue;

    public TweetRateServiceController(@Qualifier("tweet_rates") Region<String, SentimentStats> tweet_rates,
                                      Queue<TweetSentiment> liveTweetsQueue,
                                      @Qualifier("tweet_sentiments") Region<String,TweetSentiment> tweetSentimentRegion)
    {
        this.tweet_rates = tweet_rates;
        this.liveTweetsQueue = liveTweetsQueue;
        this.tweetSentimentRegion = tweetSentimentRegion;
    }


    /**
     * Example Data
     * <p>
     * data: {"tweet": "RT @maggieNYT: The whole \"nationalist vs New York wing\" fight was smart branding for ppl trying to rally troops.. Not accurate, but clever\u2026", "polarity": "0.87"}
     * <p>
     * data: {"tweet": "RT @edko426: @4AllSoulKind @03Ava @cgm807 @zackwack123 @Barbarajean117 @WalkerkillR @nfraizi @GTBighair1 @dynamex @MiceeMouse\u2026 ", "polarity": "0.98"}
     * <p>
     * data: {"tweet": "RT @ChelseaFC: Superb header by Gary Cahill to put us back in front, tremendous bravery by the skipper and wonderful execution. #CHESOU", "polarity": "0.87"}
     * <p>
     * data: {"tweet": "RT @BNightengale: The #Marlins sale of course won't be finalized until #MLB approval. The next owners meeting is next month in New York", "polarity": "0.56"}
     * <p>
     * data: {"tweet": "#3RMXi v0.1.2 had just been released!\nThe Language component State is decoupled from the UI now!\nhttps://t.co/y3D4tIurAf\n#react #javascript", "polarity": "0.67"}
     *
     * @param response
     * @throws IOException
     */
    public void live_tweets(HttpServletResponse response)
    throws IOException
    {
        //TODO: calculate this data from PCC
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/event-stream");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        TweetSentiment tweetSentiment = null;

        int tweetRate = liveTweetsQueue.size();
        Double avgPolarity = 0.0;
        int cnt = 0;

        double polarity = 0;
        while ((tweetSentiment = liveTweetsQueue.poll()) != null)
        {
            if(tweetSentiment == null)
                continue;

            polarity = tweetSentiment.getPolarity();

            response.getWriter().println(String.format("data: {\"tweet\": \"%s\", \"polarity\": \"%s\"}\r\n", tweetSentiment.getTweet().getText(), polarity));

            avgPolarity += polarity;
            cnt++;

        }

        if (cnt > 0)
        {
            avgPolarity = (avgPolarity) / (cnt * 1.0);
        }

        this.tweet_rates.put("tweet_rates", SentimentStats.builder()
                                                          .tweetRate(tweetRate)
                                                          .avgPolarity(avgPolarity.intValue())
                                                          .build());

    }
}
