package com.aesopwow.subsubclipclop.config;

import com.aesopwow.subsubclipclop.domain.alarm.message.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisMessageConfig {
    private final RedisSubscriber redisSubscriber;

    @Bean
    public MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(redisSubscriber);
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("alarm");
    }
}
