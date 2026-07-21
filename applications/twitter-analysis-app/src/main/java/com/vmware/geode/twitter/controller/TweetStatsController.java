package com.vmware.geode.twitter.controller;

import com.vmware.geode.twitter.service.TweetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * TweetStatsController
 *
 * @author Gregory Green
 */
@RestController
public class TweetStatsController
{
    private ExecutorService nonBlockingService = Executors
            .newCachedThreadPool();

    private final TweetService service;
    private final Supplier<SseEmitter> sseEmitterSupplier;

    public TweetStatsController(TweetService service,Supplier<SseEmitter> sseEmitterSupplier )
    {
        this.service = service;
        this.sseEmitterSupplier = sseEmitterSupplier;
    }

    @GetMapping("countTweets")
    public int count()
    {
        return service.countTweets();
    }

    @RequestMapping("/num_tweets")
    public SseEmitter countSse()
    {
        return countSse(sseEmitterSupplier.get());
    }

    private SseEmitter countSse(SseEmitter emitter)
    {
        nonBlockingService.execute(() -> {
            try {
                emitter.send(count());

                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }

    @GetMapping("/tweet_rate")
    public SseEmitter sentimentStatsSse()
    {
        SseEmitter emitter = sseEmitterSupplier.get();

        nonBlockingService.execute(() -> {
            try {
                emitter.send(this.service.findSentimentStats());

                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }
}
