package com.geek.test.redis.repository;

import com.geek.test.redis.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created  on 2020/04/10.
 *
 * @author biaolu
 */
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder,String> {
}