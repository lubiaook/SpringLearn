package com.geek.test.redis.model;

import lombok.*;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * Created  on 2020/04/12.
 *
 * @author lubiao
 */
@RedisHash(value = "springbucks-coffee", timeToLive = 60)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoffeeCache {
    @Id
    private Long id;
    @Indexed
    private String name;
    private Money price;
}