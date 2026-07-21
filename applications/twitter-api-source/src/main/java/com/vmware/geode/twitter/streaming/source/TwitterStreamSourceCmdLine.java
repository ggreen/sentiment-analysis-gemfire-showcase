package com.vmware.geode.twitter.streaming.source;

import com.vmware.geode.twitter.domain.Tweet;
import com.vmware.geode.twitter.service.polarity.PolarityComputeService;
import nyla.solutions.core.net.http.Http;
import nyla.solutions.core.util.Debugger;
import org.apache.commons.collections.functors.ExceptionPredicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Function;

/**
 * {"data":{"id":"1497249457309728770","text":"@Pet_SimXLeaks @LeakGangRoblox The lucky cat!!!"},"matching_rules":[{
 * "id":"1477262727555031048","tag":""}]}
 * {"data":{"id":"1497249458878390298","text":"RT @MoonriverNW: 1/🔥Moonriver now has new functionality that adds
 * native cross-chain token capabilities!🔥\n\nThis marks the introduction of X…"},"matching_rules":[{"id
 * ":"1477262727555031048","tag":""}]}
 * {"data":{"id":"1497249456009486358","text":"New story \"Avery Construction Gets $1 Million For Expansion\" has
 * been published on https://t.co/2MRHpTS1rC - https://t.co/yv66YHMssS #sault #SaultNews #SaultOnline #ONNtv
 * #LoveLocal https://t.co/QEAW5RYmsz"},"matching_rules":[{"id":"1477262727555031048","tag":""},{"id
 * ":"1477264087721590786","tag":""}]}
 *
 * {"data":{"id":"1497249458945470472","text":"RT @SneakerScouts: #SneakerScouts The New Balance 574 Rugged
 * 'Indigo/Red' is now available via @champssports for $60! (retail $85) #ad @New…"},"matching_rules":[{"id
 * ":"1477262727555031048","tag":""}]}
 */

/**
 * TwitterStreamSourceCmdLine
 *
 * @author Gregory Green
 */
@Component
public class TwitterStreamSourceCmdLine implements CommandLineRunner
{
    private final PolarityComputeService polarityComputeService;
    private final Http http;
    private final String urlString = "https://api.twitter.com/2/tweets/search/stream";
    private final URL url;
    private final Function<String, Tweet> toTweet;

    public TwitterStreamSourceCmdLine(PolarityComputeService polarityComputeService, Http http,
                                      Function<String, Tweet> toTweet,
                                      @Value("${tweet.api.token}")
                                      String token)
    {

        this.polarityComputeService = polarityComputeService;
        this.http = http;
        http.setHeader("Authorization", " Bearer "+token);
        this.toTweet = toTweet;

        try {
            url = new URL(urlString);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("ERROR:"+e+" URL:"+urlString);
        }
    }

    @Override
    public void run(String... args) throws Exception
    {
        BufferedReader bufferedReader = http.getWithReader(url);

        String line = null;
        while((line = bufferedReader.readLine()) != null)
        {
            if(line.trim().isEmpty())
                continue;

            try
            {
                var tweet = toTweet.apply(line);
                polarityComputeService.saveTweet(tweet);
            }
            catch(Exception e)
            {
                Debugger.printError(this,"ERROR:"+e+" line:"+line);
            }
        }
    }
}
