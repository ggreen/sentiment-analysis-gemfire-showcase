package com.vmware.geode.twitter.listener;

import org.apache.geode.cache.Operation;
import org.apache.geode.cache.query.CqEvent;
import org.apache.geode.cache.util.CqListenerAdapter;

import java.util.Queue;

/**
 * TweetContinuousQuery
 *
 * @author Gregory Green
 */
public class TweetContinuousQuery extends CqListenerAdapter
{
    private final Queue queue;
    public TweetContinuousQuery(Queue queue)
    {
        this.queue = queue;
    }

    @Override
    public void onEvent(CqEvent event)
    {
        Operation ops = event.getBaseOperation();

        if( ops.isCreate() || ops.isUpdate())
        {
            queue.add(event.getNewValue());
        }
    }
}
