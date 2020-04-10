package com.aws.geek.mybatis.repository;

import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Created  on 2020/04/08.
 *
 * @author biaolu
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Coffee {
    private Long id;
    @NotBlank()
    private String name;
    private Money price;
    private Date createTime;
    private Date updateTime;
}