package com.example.ldemo.dao;

import com.example.ldemo.entity.Build;

public interface BuildMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Build record);

    int insertSelective(Build record);

    Build selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Build record);

    int updateByPrimaryKey(Build record);
}