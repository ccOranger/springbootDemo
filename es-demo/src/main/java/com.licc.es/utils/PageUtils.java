package com.licc.es.utils;

import com.licc.es.entity.es.Person;
import lombok.Data;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

/**
 *  @author: 李臣臣
 *  @Date: 2021/02/05 0005 09:56
 *  @Description: 分页封装类
 */
@Data
public class PageUtils<T>  {

    private   Integer pageNum;
    private    Integer pageSize;
    private   Integer total;
    private    Integer pages;
    private    List<T> list;


}
