package com.example.product_management.config;

import com.example.product_management.entity.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class Redisconfig {

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {

        JacksonJsonRedisSerializer<Product> serializer =
                new JacksonJsonRedisSerializer<>(Product.class);

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(180))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(serializer)
                );
    }
}