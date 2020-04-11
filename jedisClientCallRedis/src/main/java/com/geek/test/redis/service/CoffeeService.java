package com.geek.test.redis.service;

import com.geek.test.redis.model.Coffee;
import com.geek.test.redis.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

/**
 * Created  on 2020/04/10.
 *
 * @author biaolu
 */
@Slf4j
@Service
@Transactional
public class CoffeeService {
    public static final String CACHE = "springbucks-coffee";
    @Autowired
    CoffeeRepository coffeeRepository;

    @Autowired
    private RedisTemplate<String, Coffee> redisTemplate;

    public List<Coffee> findAllCoffee() {
        return coffeeRepository.findAll();
    }

    public Optional<Coffee> findOneCoffee(String name) {
        HashOperations<String, String, Coffee> hashOperations = redisTemplate.opsForHash();
        if (redisTemplate.hasKey(CACHE) &&
                hashOperations.hasKey(CACHE, name)) {
            log.info("Coffee Find {}", name);
            return Optional.of(hashOperations.get(CACHE, name));
        }
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name",exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Find {}", coffee);
        if (coffee.isPresent()) {
            log.info("put Coffee to Redis {}", name);
            hashOperations.put(CACHE,name,coffee.get());
            redisTemplate.expire(CACHE, 1, TimeUnit.MINUTES);
        }
        return coffee;
    }
}