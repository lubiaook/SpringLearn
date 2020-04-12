package com.geek.test.redis.converter;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;


/**
 * Created  on 2020/04/12.
 *
 * @author lubiao
 */
@WritingConverter
public class MoneyToBytesConverter implements Converter<Money,byte[]> {

    @Nullable
    @Override
    public byte[] convert(Money money) {
        String value = Long.toString(money.getAmountMajorLong());
        return value.getBytes(StandardCharsets.UTF_8);
    }
}