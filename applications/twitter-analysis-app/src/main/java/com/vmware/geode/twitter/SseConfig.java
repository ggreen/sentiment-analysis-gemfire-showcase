package com.vmware.geode.twitter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.function.Supplier;

/**
 * SseConfig
 *
 * @author Gregory Green
 */
@Configuration
public class SseConfig
{
    @Bean
    public Supplier<SseEmitter> sseEmitterSupplier()
    {
        return ()-> new SseEmitter();
    }
}
