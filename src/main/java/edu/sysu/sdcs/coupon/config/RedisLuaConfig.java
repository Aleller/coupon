package edu.sysu.sdcs.coupon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class RedisLuaConfig {
    @Bean
    public DefaultRedisScript<Boolean> DecrByCountScript () {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/decrByCount.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
}
