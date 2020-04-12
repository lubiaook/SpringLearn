package com.geek.test.redis.service;

import com.geek.test.redis.model.Coffee;
import com.geek.test.redis.model.CoffeeCache;
import com.geek.test.redis.repository.CoffeCacheRepository;
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
    @Autowired
    CoffeeRepository coffeeRepository;


    @Autowired
    CoffeCacheRepository coffeCacheRepository;
    public List<Coffee> findAllCoffee() {
        return coffeeRepository.findAll();
    }

    public Optional<Coffee> findSimpleCoffeeFromCache(String name) {
        ///从缓存中获取coffeeCache
        Optional<CoffeeCache> coffeeCache = coffeCacheRepository.findByName(name);
        if (coffeeCache.isPresent()) {
            CoffeeCache coffeeCached = coffeeCache.get();
//            Coffee coffee=Coffee.builder().
//                    name(coffeeCached.getName())
//                    .price(coffeeCached.getPrice()).
//                            id(coffeeCached.getId()).buid();
            Coffee coffee = Coffee.builder().
                    name(coffeeCached.getName())
                    .price(coffeeCached.getPrice())
                    .build();
            log.error("Coffee find in cache :{}", coffee);
            return Optional.of(coffee);
        } else {
            Optional<Coffee> coffee = findOneCoffee(name);
            coffee.ifPresent(c->{
                CoffeeCache coffeeCacheNew = CoffeeCache.builder().
                        id(c.getId()).
                        name(c.getName()).
                        price(c.getPrice()).
                        build();
                log.error("Save Coffee to cache ",c);
                coffeCacheRepository.save(coffeeCacheNew);
            });
            return coffee;
        }

    }

    public Optional<Coffee> findOneCoffee(String name) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(
                Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Found: {}", coffee);
        return coffee;
    }
}