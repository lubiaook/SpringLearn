package com.aws.geek.mybatis;

import com.alibaba.fastjson.JSON;
import com.aws.geek.mybatis.mapper.CoffeeMapper;
import com.aws.geek.mybatis.repository.Coffee;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2020/04/08.
 *
 * @author biaolu
 */
@RestController
@RequestMapping("/mybatis/test")
public class MybatisController {
    @Autowired
    CoffeeMapper coffeeMapper;

    @PostMapping("/create/coffee")
    public String creatCoffee(@RequestBody Coffee coffee) {
        int count = coffeeMapper.save(coffee);

        return JSON.toJSONString(coffee);
    }
}