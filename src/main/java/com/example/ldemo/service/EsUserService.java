package com.example.ldemo.service;


import com.example.ldemo.entity.es.EsUser;
import com.example.ldemo.es.EsUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EsUserService {

    @Autowired
    private EsUserMapper esUserMapper;

    public Integer save(EsUser user){

       return esUserMapper.save(user).getId();
    }



    public String deleteById(Integer id) {
        esUserMapper.deleteById(id);
        return "Success!";
    }

    public String updateUser(EsUser user) {
        return String.valueOf(esUserMapper.save(user).getId());// 返回id做验证
    }

    public EsUser findById(Integer id) {
        return esUserMapper.findById(id).get();
    }

    public Iterable<EsUser> findAll() {

        return esUserMapper.findAll();
    }
    public Iterable<EsUser> searchs(String query) {


        //创建查询构建器
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery("username", query));


        return esUserMapper.search(queryBuilder);
    }

}
