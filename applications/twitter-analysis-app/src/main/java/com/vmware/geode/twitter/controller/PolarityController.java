package com.vmware.geode.twitter.controller;

import com.vmware.geode.twitter.service.polarity.PolarityComputeService;
import com.vmware.geode.twitter.domain.Sentiments;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * PolarityController
 *
 * @author Gregory Green
 */
@RestController
public class PolarityController
{
    private final PolarityComputeService service;

    public PolarityController(PolarityComputeService service)
    {
        this.service = service;
    }

    @PostMapping("/polarity_compute")
    public void polarity_compute(@RequestBody Sentiments sentiments)
    {
        service.polarity_compute(sentiments);
    }
}
