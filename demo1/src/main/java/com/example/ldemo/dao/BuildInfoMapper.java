package com.example.ldemo.dao;

import com.example.ldemo.entity.BuildInfo;

public interface BuildInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BuildInfo record);

    int insertSelective(BuildInfo record);

    BuildInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BuildInfo record);

    int updateByPrimaryKey(BuildInfo record);
}