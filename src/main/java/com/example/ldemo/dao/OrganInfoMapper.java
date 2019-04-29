package com.example.ldemo.dao;

import com.example.ldemo.entity.OrganInfo;

public interface OrganInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrganInfo record);

    int insertSelective(OrganInfo record);

    OrganInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrganInfo record);

    int updateByPrimaryKey(OrganInfo record);
}