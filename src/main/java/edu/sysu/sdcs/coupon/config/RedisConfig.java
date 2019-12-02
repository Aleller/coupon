package edu.sysu.sdcs.coupon.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    //redis连接工厂
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * 配置自定义redisTemplate
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);

        //使用FastJsonRedisSerializer序列化和反序列化redis的key、value值
        template.setValueSerializer(fastJsonRedisSerializer());
        template.setKeySerializer(fastJsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * fastjson序列化Bean
     * @return
     */
    @Bean
    public FastJsonRedisSerializer<?> fastJsonRedisSerializer() {
        return new FastJsonRedisSerializer<>(Object.class);
    }
}