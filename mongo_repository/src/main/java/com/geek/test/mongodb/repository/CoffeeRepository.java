package com.geek.test.mongodb.repository;

import com.geek.test.mongodb.model.Coffee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created  on 2020/04/10.
 *
 * @author biaolu
 */
public interface  CoffeeRepository extends MongoRepository<Coffee,String> {
    List<Coffee> findByName(String name);
}