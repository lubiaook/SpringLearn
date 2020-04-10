package com.aws.geek.mybatis.mapper;

import com.aws.geek.mybatis.repository.Coffee;
import org.apache.ibatis.annotations.*;

/**

 * Created  on 2020/04/08.
 *
 * @author biaolu
 */
@Mapper
public interface CoffeeMapper {
    @Insert("insert into t_coffee (name,price,create_time,update_time)"
    +"values(#{name},#{price},now(),now())")
    @Options(useGeneratedKeys = true)
    public abstract int save(Coffee coffee);

    @Select("select * form t_coffee where id =#{id}")
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "create_time",property = "createTime")
    })
    public abstract Coffee findById(@Param("id") Long id);
}