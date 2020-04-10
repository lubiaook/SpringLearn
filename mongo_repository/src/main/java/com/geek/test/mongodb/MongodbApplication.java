package com.geek.test.mongodb;

import com.geek.test.mongodb.converter.MoneyReadConverter;
import com.geek.test.mongodb.model.Coffee;
import com.geek.test.mongodb.repository.CoffeeRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@EnableMongoRepositories
@SpringBootApplication
public class MongodbApplication implements ApplicationRunner {

	@Autowired
	CoffeeRepository coffeeRepository;
	public static void main(String[] args) {
		SpringApplication.run(MongodbApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Coffee coffee = Coffee.builder().name("拿铁").
				price(Money.of(CurrencyUnit.of("CNY"), 20)).
				createTime(new Date()).
				updateTime(new Date()).build();
		Coffee coffeeKaBu = Coffee.builder().name("卡布奇诺").
				price(Money.of(CurrencyUnit.of("CNY"), 20)).
				createTime(new Date()).
				updateTime(new Date()).build();
		coffeeRepository.insert(Arrays.asList(coffee, coffeeKaBu));
		coffeeRepository.findAll(Sort.by("name")).forEach(c->log.info("Saved coffe {}",c));
        Thread.sleep(1000);
		coffee.setPrice( Money.of(CurrencyUnit.of("CNY"), 99));
		coffee.setUpdateTime(new Date());
		coffeeRepository.save(coffee);
		coffeeRepository.findByName("拿铁").forEach(c->log.error("update 拿铁 {}",c));
		coffeeRepository.deleteAll();
	}


	//注入Money转换
    @Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
	}


}
