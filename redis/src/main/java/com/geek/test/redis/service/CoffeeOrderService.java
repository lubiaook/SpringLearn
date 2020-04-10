package com.geek.test.redis.service;

import com.geek.test.redis.model.Coffee;
import com.geek.test.redis.model.CoffeeOrder;
import com.geek.test.redis.model.OrderState;
import com.geek.test.redis.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created  on 2020/04/10.
 *
 * @author biaolu
 */
@Slf4j
@Service
@Transactional
public class CoffeeOrderService {
    @Autowired
    CoffeeOrderRepository orderRepository;

    public CoffeeOrder createOrder(String customer, Coffee... coffees) {
        CoffeeOrder order = CoffeeOrder.builder().
                customer(customer).
                items(new ArrayList<Coffee>(Arrays.asList(coffees))).state(OrderState.INIT).build();
        CoffeeOrder saved = orderRepository.save(order);
        log.error("new Order:{}",saved);
        return saved;
    }

    public boolean updateState(CoffeeOrder coffeeOrder, OrderState orderState) {
        if (orderState.compareTo(coffeeOrder.getState())<=0) {
            log.warn("wrong Status order:{},{}",orderState,coffeeOrder.getState());
        }
        coffeeOrder.setState(orderState);
        orderRepository.save(coffeeOrder);
        return true;
    }
}