package com.licc.es;


import com.licc.es.entity.es.EsUser;
import com.licc.es.entity.es.Person;
import com.licc.es.es.PersonMapper;
import com.licc.es.service.PersonService;
import com.licc.es.utils.PageUtils;
import javafx.beans.binding.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.assertj.core.api.BDDAssertions.and;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EsTest {

    @Autowired
    private PersonMapper personMapper;


    @Autowired
    private PersonService personService;


    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void add() {



            Person person = new Person();
            person.setAge(14);
            person.setId(System.currentTimeMillis()+"");
            person.setPassword("123456789");
            person.setUsername("张三5");

            personMapper.save(person);

    }



    @Test
    public void testGetAll() {
        Long c = personMapper.count();
        System.out.println(c);
        Iterable<Person> iterable = personMapper.findAll();
        iterable.forEach(e->System.out.println(e.toString()));
    }



    @Test
    public void testGetByName() {
        List<Person> list = personService.getByName("面包");
        System.out.println(list);
    }

    @Test
    public void testPage() {
        Page<Person> page = personService.pageQuery(0, 10, "切片");
        System.out.println(page.getTotalPages());
        System.out.println(page.getNumber());
        System.out.println(page.getContent());
    }

    @Test
    public void getList() {
        List<Person> page = personService.getList("张三19");
        System.out.println(page);
    }


    @Test
    public void getPageList() {
        PageUtils<Person> page = personService.getPageList(1, 10,"张三");
        System.out.println(page.toString());
        page.getList().forEach(e->System.out.println(e.toString()));
    }



    @Test
    public void addList() {

        List<Person> personList =new ArrayList<>();

        for (int i=0;i<10;i++){

            Person person = new Person();
            person.setAge(123);
            person.setId(System.currentTimeMillis()+"");
            person.setPassword("123456");
            person.setUsername("张三1"+i);
            personList.add(person);
        }

        personMapper.saveAll(personList);


    }


    @Test
    public void findById() {

        Optional<Person> person=  personMapper.findById("1608708241055");

            System.out.println(person);
    }

    @Test
    public void findAll() {



        // 用户名倒序
        Sort sort =  Sort.by(Sort.Direction.DESC,"username.keyword");

        Iterable<Person> personList =  personMapper.findAll(sort);

        log.debug(personList.toString());

    }


    @Test
    public void findPage() {

        Pageable pageable = PageRequest.of(1, 2);

        Page<Person> personList =  personMapper.findAll(pageable);

        log.debug(personList.toString());
    }



    @Test
    public void find() {

        NativeSearchQueryBuilder nativeSearchQueryBuilder =new NativeSearchQueryBuilder();
       NativeSearchQuery nativeSearchQuery =  nativeSearchQueryBuilder.build();

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery("username", "query"));

        elasticsearchRestTemplate.search(nativeSearchQuery, EsUser.class);
    }

}



