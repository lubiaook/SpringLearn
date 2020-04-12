package com.geek.test.redis.repository;

import com.geek.test.redis.model.CoffeeCache;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created  on 2020/04/12.
 *
 * @author lubiao
 */
public interface CoffeCacheRepository extends CrudRepository<CoffeeCache,Long>{
    Optional<CoffeeCache> findByName(String name);
}