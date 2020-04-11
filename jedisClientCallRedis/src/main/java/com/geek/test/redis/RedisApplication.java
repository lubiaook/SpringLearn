package com.geek.test.redis;

import com.geek.test.redis.model.Coffee;
import com.geek.test.redis.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
public class RedisApplication implements ApplicationRunner {

    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private JedisPoolConfig jedisPoolConfig;


    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }


    @Bean
    @ConfigurationProperties("redis")
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool(@Value("${redis.host}") String host) {
        return new JedisPool(jedisPoolConfig(),host);
    }


    @Bean
    public RedisTemplate<String, Coffee> redisTemplate(RedisConnectionFactory redisCollectionFactory) {
        RedisTemplate<String, Coffee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisCollectionFactory);
        return template;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.error(jedisPoolConfig.toString());
        try (Jedis jedis = jedisPool.getResource()) {
            coffeeService.findAllCoffee().forEach(t -> {
                jedis.hset("springbucks-menu",
                        t.getName(),
                        Long.toString(t.getPrice().getAmountMajorLong()));
            });
            Map<String, String> menu = jedis.hgetAll("springbucks-menu");
            log.error("Menu {}", menu);
            String price = jedis.hget("springbucks-menu", "espresso");
            log.error("expresso -{}", Money.ofMinor(CurrencyUnit.of("CNY"), Long.parseLong(price)));
        }
    }

}
