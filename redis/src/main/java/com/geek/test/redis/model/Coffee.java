package com.geek.test.redis.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created  on 2020/04/10.
 *
 * @author biaolu
 */
@Data
@Entity
@Table(name="T_COFFEE")
@Builder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Coffee extends BaseEntity {
    private String name;
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistenMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode",value = "CNY")})
    private Money price;
}