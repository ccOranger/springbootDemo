package com.licc.es.controller;

import com.licc.es.entity.es.EsOrder;
import com.licc.es.es.EsOrderMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EsOrderController {

    @Autowired
    private EsOrderMapper esOrderMapper;


    @PostMapping("/addOrder")
    public String addOrder(@RequestBody EsOrder esOrder) {
        return String.valueOf(esOrderMapper.save(esOrder));// 返回id做验证
    }


    @GetMapping("/getOrder")
    public EsOrder getOrder(String id) {
        return esOrderMapper.findById(id).get();
    }

    @GetMapping("/getAllOrders")
    public Iterable<EsOrder> getAllOrders() {
        return esOrderMapper.findAll();
    }


    @GetMapping("/getSearch")
    public Iterable<EsOrder> getSearch(String search, int page, int limit ) {

        BoolQueryBuilder qb = QueryBuilders.boolQuery();

        //should --- 对多个字段都匹配，只要有一个字段匹配成功就可以   must --- 全部字段都匹配成功才可以
      //qb.must(QueryBuilders.matchQuery("username", search));
        qb.should(QueryBuilders.matchQuery("username", search));
        qb.should(QueryBuilders.matchQuery("password", search));

        //分页从0开始的
        Pageable pageable = PageRequest.of(page, limit);

        return (Page<EsOrder>)esOrderMapper.search(qb,pageable);

    }
}
