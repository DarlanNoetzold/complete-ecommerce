package tech.noetzold.ecommerce.config.cache;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig {

    @Bean
    RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> {
            Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
            configurationMap.put("product", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(20)));
            configurationMap.put("order", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(10)));
            configurationMap.put("cart", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(15)));
            configurationMap.put("category", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(20)));
            builder.withInitialCacheConfigurations(configurationMap);
        };
    }

}