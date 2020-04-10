package com.geek.test.mongodb;

import com.geek.test.mongodb.converter.MoneyReadConverter;
import com.geek.test.mongodb.model.Coffee;
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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootApplication
public class MongodbApplication implements ApplicationRunner {

	@Autowired
	MongoTemplate mongoTemplate;
	public static void main(String[] args) {
		SpringApplication.run(MongodbApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Coffee coffee = Coffee.builder().name("拿铁").
				price(Money.of(CurrencyUnit.of("CNY"), 20)).
				createTime(new Date()).
				updateTime(new Date()).build();
		Coffee saveCoffee = mongoTemplate.save(coffee);
		log.info("Coffee {}", saveCoffee);
		List<Coffee> list = mongoTemplate.find(Query.query(Criteria.where("name").is("拿铁")),Coffee
		.class);
		list.stream().forEach(t->{
			log.info("Coffee {}", t);
		});
		//todo Criteria.byExample()
        //为了查看更新时间
        Thread.sleep(1000);
		UpdateResult result = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is("拿铁")),
				new Update().set("price",Money.ofMinor(CurrencyUnit.of("USD"),5)), Coffee.class);
		log.info("update Result：{}", result.getModifiedCount());
		Coffee oneCoffee = mongoTemplate.findById(saveCoffee.getId(), Coffee.class);
		log.info("update Result：{}", oneCoffee);
		mongoTemplate.remove(oneCoffee);
	}


	//注入Money转换
    @Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
	}


}
