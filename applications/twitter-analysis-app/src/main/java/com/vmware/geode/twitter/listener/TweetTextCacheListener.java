package com.vmware.geode.twitter.listener;

import com.vmware.geode.twitter.domain.Sentiments;
import com.vmware.geode.twitter.service.polarity.PolarityComputeService;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.util.CacheListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * TweetTestCacheListener
 *
 * @author Gregory Green
 */
@Component
public class TweetTextCacheListener extends CacheListenerAdapter<String,String>
{
    private final PolarityComputeService polarityComputeService;

    public TweetTextCacheListener(PolarityComputeService polarityComputeService)
    {
        this.polarityComputeService = polarityComputeService;
    }

    @Override
    public void afterCreate(EntryEvent<String, String> event)
    {
        processEvent(event);
    }
    @Override
    public void afterUpdate(EntryEvent<String, String> event)
    {
        processEvent(event);
    }

    private void processEvent(EntryEvent<String, String> event)
    {
        Sentiments sentiments = new Sentiments();
        String[] data = {event.getNewValue()};
        sentiments.setData(data);
        this.polarityComputeService.polarity_compute(sentiments);
    }
}
