package com.example.ldemo;


import com.example.ldemo.entity.es.EsUser;
import com.example.ldemo.es.EsUserMapper;
import com.example.ldemo.service.EsUserService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEs {


    @Autowired
    private EsUserMapper esUserMapper;
    @Autowired
    private EsUserService esUserService;


    @Test
    public void addUser() {

        for (int i=0;i<10;i++){

            EsUser user = new EsUser();
            user.setUsername("username"+i);
            user.setPassword("password"+i);
            user.setAge(i);
            user.setId(i);
            String.valueOf(esUserService.save(user));// 返回id做验证
        }
    }
    @Test
    public void searchs() {

        System.out.println(esUserService.searchs("user"));
    }

    /**模糊匹配*/
    @Test
    public void match(String query){
        QueryBuilder qb = QueryBuilders.wildcardQuery("username", "*"+query+"*");
        System.out.println((Page<EsUser>)esUserMapper.search(qb));
    }


    //使用BoolQueryBuilder进行复合查询
    @Test
    public void  tt(){

    //模糊查询
        WildcardQueryBuilder queryBuilder1 = QueryBuilders.wildcardQuery(
                "name", "*jack*");//搜索名字中含有jack的文档
        WildcardQueryBuilder queryBuilder2 = QueryBuilders.wildcardQuery(
                "interest", "*read*");//搜索interest中含有read的文档

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//name中必须含有jack,interest中必须含有read,相当于and
        boolQueryBuilder.must(queryBuilder1);
        boolQueryBuilder.must(queryBuilder2);

    }



}
