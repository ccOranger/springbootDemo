package com.example.ldemo.dao;

import com.example.ldemo.entity.Organ;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Organ record);

    int insertSelective(Organ record);

    Organ selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Organ record);

    int updateByPrimaryKey(Organ record);
}