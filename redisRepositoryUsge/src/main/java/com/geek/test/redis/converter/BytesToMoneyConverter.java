package com.geek.test.redis.converter;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;


/**
 * Created  on 2020/04/12.
 *
 * @author lubiao
 */
@ReadingConverter
public class BytesToMoneyConverter implements Converter<byte[],Money> {

    @Nullable
    @Override
    public Money convert(byte[] bytes) {
        String value = new String(bytes, StandardCharsets.UTF_8);
        return Money.ofMinor(CurrencyUnit.of("CNY"), Long.parseLong(value));
    }
}