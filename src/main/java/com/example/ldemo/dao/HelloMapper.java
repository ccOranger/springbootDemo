package com.example.ldemo.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @package: com.example.ldemo.dao
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/4/11 15:39
 * @updateUser: 李臣臣
 * @updateDate: 2019/4/11 15:39
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@Repository
public interface HelloMapper {


    @Select("select * from user where id = #{id} ")
    Map findByUserName(Integer id);


    Map findByUserId();
}
