package com.geek.test.redis.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created  on 2020/04/10.
 *
 * @author biaolu
 */
@Entity
@Table(name = "T_ORDER")
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeOrder extends BaseEntity {
    private String customer;
    @ManyToMany
    @JoinTable(name="T_ORDER_COFFEE")
    @OrderBy("id")
    private List<Coffee> items;
    @Enumerated
    @Column(nullable = true)
    private OrderState state;

}