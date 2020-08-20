package com.example.ldemo.dao;

import com.example.ldemo.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    User loadUserByUsername(String userName);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}