/*
 * @Description:
 * @Author: 唐健峰
 * @Date: 2023-04-15 01:21:51
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-16 11:07:16
 */
package tjf.emuseum.emuseum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

@Configuration
@PropertySource("classpath:application.properties")
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.password}")
    private char[] password;
    @Value("${spring.data.redis.database}")
    private int database;
    private RedisTemplate<String, Object> redisTemplate;

    @Bean("jackson2JsonRedisSerializer")
    public Jackson2JsonRedisSerializer<Object> getJackson2JsonRedisSerializer() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                .build()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }

    @Bean("connectionFactory")
    public LettuceConnectionFactory getLettuceConnectionFactory() {
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(host, port);
        redisConfiguration.setPassword(RedisPassword.of(password));
        redisConfiguration.setDatabase(database);
        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                // .readFrom(REPLICA_PREFERRED)
                // .useSsl()
                .build();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration,
                clientConfiguration);
        lettuceConnectionFactory.setValidateConnection(true);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    @Bean("redisTemplate")
    @Autowired
    @SuppressWarnings(value = { "unchecked", "rawtypes" })
    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory connectionFactory,
            Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        this.redisTemplate = redisTemplate;
        return redisTemplate;
    }
}
