package com.licc.es.controller;

import com.licc.es.entity.es.EsUser;
import com.licc.es.es.EsUserMapper;
import com.licc.es.service.EsUserService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
public class EsUserController {

    @Autowired
    private EsUserService esUserService;

    @Autowired
    private EsUserMapper esUserMapper;


    @PostMapping("/addUser")
    public String addUser(Integer id ,String username, String password, Integer age) {
        EsUser user = new EsUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setAge(age);
        user.setId(id);
        return String.valueOf(esUserService.save(user));// 返回id做验证
    }


    @DeleteMapping("/deleteUser")
    public String deleteUser(Integer id) {
        esUserService.deleteById(id);
        return "Success!";
    }

    @PutMapping("/updateUser")
    public String updateUser(Integer id, String username, String password, Integer age) {
        EsUser user = new EsUser();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setAge(age);
        return String.valueOf(esUserService.updateUser(user));// 返回id做验证
    }

    @GetMapping("/getUser")
    public EsUser getUser(Integer id) {
        return esUserService.findById(id);
    }

    @GetMapping("/getAllUsers")
    public Iterable<EsUser> getAllUsers() {
        return esUserService.findAll();
    }

    @GetMapping("/searchs")
    public Iterable<EsUser> searchs(String param) {
        return esUserService.searchs(param);
    }


    /**模糊匹配*/
    @GetMapping("/fuzzyQuery")
    public Page<EsUser> fuzzyQuery(String query){
        QueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("username", query);
        return (Page<EsUser>)esUserMapper.search(queryBuilder);
    }
    /**前缀匹配*/
    @GetMapping("/match")
    public Page<EsUser> match(String query){
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.matchQuery("username", query));
        return (Page<EsUser>)esUserMapper.search(qb);
    }


    /**
     * WildcardQuery  --  模糊查询，?匹配单个字符，*匹配多个字符
     *multiMatchQuery
     *
     *
     */
    /**模糊匹配*/
    @GetMapping("/wildcardQuery")
    public Page<EsUser> wildcardQuery(String query){
        QueryBuilder qb = QueryBuilders.wildcardQuery("username", "*"+query+"*");
        return (Page<EsUser>)esUserMapper.search(qb);
    }
    /**模糊匹配*/
    @GetMapping("/queryBuilder1")
    public Page<EsUser> queryBuilder1(String query){
//模糊查询
        WildcardQueryBuilder queryBuilder1 = QueryBuilders.wildcardQuery(
                "username", "*"+query+"*");//搜索名字中含有jack的文档
        WildcardQueryBuilder queryBuilder2 = QueryBuilders.wildcardQuery(
                "password", "*"+query+"*");//搜索interest中含有read的文档


        //复合查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//name中必须含有jack,interest中必须含有read,相当于and
        boolQueryBuilder.must(queryBuilder1);
        boolQueryBuilder.must(queryBuilder2);

        return (Page<EsUser>)esUserMapper.search(boolQueryBuilder);
    }

    /**短语模糊匹配*/
    @GetMapping("/matchPhrase")
    public Page<EsUser> matchPhraseQuery(String query){
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.matchPhraseQuery("username", query));
        return (Page<EsUser>)esUserMapper.search(qb);
    }

    /**精确匹配*/
    @GetMapping("/term")
    public Page<EsUser> term(String query){
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.termQuery("username", query));
        return (Page<EsUser>)esUserMapper.search(qb);
    }

    /**分页查询*/
    @GetMapping("/page")
    public Page<EsUser> page(String query,
                             @PageableDefault(page = 0, size = 5, sort = "time", direction = Sort.Direction.DESC) Pageable pageable){
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if(query != null) {
            qb.must(QueryBuilders.matchQuery("username", query));
        }
        return esUserMapper.search(qb, pageable);
    }


    /**范围查询*/
    @GetMapping("/range")
    public Page<EsUser> range(long query){
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.rangeQuery("id").gt(query));
        //qb.must(QueryBuilders.rangeQuery("time").from(query).to(System.currentTimeMillis()));//大于query，小于当前时间
        return (Page<EsUser>)esUserMapper.search(qb);
    }

}
