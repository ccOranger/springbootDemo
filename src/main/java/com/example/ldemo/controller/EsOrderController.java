package com.example.ldemo.controller;

import com.example.ldemo.entity.es.EsOrder;
import com.example.ldemo.entity.es.EsUser;
import com.example.ldemo.es.EsOrderMapper;
import com.example.ldemo.es.EsUserMapper;
import com.example.ldemo.service.EsUserService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
    public Iterable<EsOrder> getSearch(String search,int page,int limit ) {

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
