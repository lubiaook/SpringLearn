package com.geek.test.redis;

import com.geek.test.redis.model.Coffee;
import com.geek.test.redis.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.data.redis.connection.RedisConnectionFactory;
@Slf4j
@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication
public class RedisApplication implements ApplicationRunner {

	@Autowired
	private CoffeeService coffeeService;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private JedisPoolCon
	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

	}

	@Bean
	public RedisTemplate<String, Coffee> redisTemplate(RedisConnectionFactory redisCollectionFactory) {
		RedisTemplate<String, Coffee> template = new RedisTemplate<>();
		template.setConnectionFactory(redisCollectionFactory);
		return template;
	}

	public JedisPool
}
