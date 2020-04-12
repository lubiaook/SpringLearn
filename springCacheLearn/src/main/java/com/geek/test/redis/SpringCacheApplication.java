package com.geek.test.redis;

import com.geek.test.redis.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
@EnableCaching(proxyTargetClass = true)
public class SpringCacheApplication implements ApplicationRunner {

    @Autowired
    private CoffeeService coffeeService;


    public static void main(String[] args) {
        SpringApplication.run(SpringCacheApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        coffeeService.findAllCoffee().forEach(t -> {
            log.error("{}", Long.toString(t.getPrice().getAmountMajorLong()));
        });
        for (int i = 0; i < 9; i++) {
            log.error("Data from Cache");
        }
        //手动刷新spring自带缓存
//        coffeeService.reloadCoffee();
        Thread.sleep(5000);
        coffeeService.findAllCoffee().forEach(t -> {
            log.error("{}", Long.toString(t.getPrice().getAmountMajorLong()));
        });
    }

}
