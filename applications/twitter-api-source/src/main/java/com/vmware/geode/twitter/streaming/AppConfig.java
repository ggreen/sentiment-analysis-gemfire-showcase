package com.vmware.geode.twitter.streaming;

import com.vmware.geode.twitter.service.polarity.PolarityComputeService;
import nyla.solutions.core.net.http.Http;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * HttpConfig
 *
 * @author Gregory Green
 */
@Configuration
@ComponentScan(basePackageClasses = {PolarityComputeService.class})
public class AppConfig
{
    @Bean
    public Http http()
    {
        return new Http();
    }
}
