package com.aws.geek.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.aws.geek.mybatis.mapper")
public class MybatisApplication  {
	public static void main(String[] args) {
		SpringApplication.run(MybatisApplication.class, args);
	}
}
