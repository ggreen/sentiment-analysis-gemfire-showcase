package com.vmware.geode.twitter.repository;

import com.vmware.geode.twitter.domain.SentimentStats;
import io.pivotal.services.dataTx.geode.io.QuerierService;
import nyla.solutions.core.util.Organizer;
import org.apache.geode.management.DistributedRegionMXBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SentimentStatsGeodeRepositoryTest
{
    @Mock
    private DistributedRegionMXBean regionMXBean;

    @Mock
    private QuerierService queryService;

    private SentimentStatsGeodeRepository subject;

    @BeforeEach
    void setUp()
    {
        subject = new SentimentStatsGeodeRepository(regionMXBean,queryService);
    }

    @Test
    void findSentimentStats()
    {
        var expected = new SentimentStats(1,1);

        Collection<Object> collection = Organizer.toList(Double.valueOf(expected.getAvgPolarity()).doubleValue());
        when(regionMXBean.getPutsRate()).thenReturn(Integer.valueOf(expected.getTweetRate()).floatValue());
        when(queryService.query(anyString())).thenReturn(collection);
        assertEquals(expected,subject.findSentimentStats());
    }

    @Test
    void findSentimentStats_WhenEmptyPolarity_ThenReturnPolarity0()
    {
        var expected = new SentimentStats(0,1);

        when(regionMXBean.getPutsRate()).thenReturn(Integer.valueOf(expected.getTweetRate()).floatValue());
        assertEquals(expected,subject.findSentimentStats());
    }

    @Test
    void findSentimentStats_WhenEmpty_ThenReturnEmpty()
    {
        assertEquals(new SentimentStats(),subject.findSentimentStats());
    }
}