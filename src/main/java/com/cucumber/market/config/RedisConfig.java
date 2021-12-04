package com.cucumber.market.config;

import com.cucumber.market.dto.member.CurrentMemberInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession // 세션을 Redis에 저장. 여러 WAS를 사용해도 세션을 하나의 Redis에서 관리할 수 있음
public class RedisConfig {
    @Value("${cucumber.market.redis.host}")
    private String redisHost;

    @Value("${cucumber.market.redis.port}")
    private int redisPort;

    @Value("${cucumber.market.redis.password}")
    private String redisPwd;

    /*
     * 클래스(Object)와 JSON의 매핑을 담당
     *
     * JSON -> Object : readValue()
     * 사용 예시
     * 1) JSON에서 Object로 변환
     * 2) JSON File을 읽어 Object로 변환
     * 3) URI 정보로 리소스에 접근하여 리소스를 읽어온 후 Object로 변환
     * 4) String 형식의 JSON 데이터를 Object로 변환
     *
     * Object -> JSON : writeValue()
     * 사용 예시
     * 1) Object를 JSON File로 변환 후 저장
     * 2) byte[] 형태로 Object를 저장
     * 3) String 형태로 Object를 JSON형태로 저장
     *
     * writerWithDefaultPrettyPrint().writeValueAs... JSON 파일이 정렬된 상태로 저장
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModules(new JavaTimeModule(), new Jdk8Module());
        return mapper;
    }

    /*
     * Redis Client 서비스 비교분석
     *
     * Lettuce
     * Tomcat이 아니라 Netty 기반. 따라서 비동기 이벤트 기반으로 요청하기 때문에 Jedis에 비해 높은 성능을 가짐
     * TPS, CPU, Connection 개수, 응답속도 모두 Jedis에 비해 우수한 성능을 보인다는 테스트 사례가 있음
     *
     * Jedis
     * Connection pool을 사용하여 성능 향상과 안정성 개선이 가능하지만 Lettuce보다 상대적으로 하드웨어적인 자원이 많이 필요
     * 비동기 기능을 제공하지 않음
     * 멀티쓰레드환경에서 쓰레드 안전을 보장하지 않음
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPassword(redisPwd);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper()));
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer(objectMapper()));

        return redisTemplate;
    }
}