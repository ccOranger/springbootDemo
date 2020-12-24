package com.licc.es;


import com.licc.es.entity.es.EsUser;
import com.licc.es.entity.es.Person;
import com.licc.es.es.PersonMapper;
import javafx.beans.binding.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
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


    @Test
    public void add() {

        Person person = new Person();
        person.setAge(123);
        person.setId(System.currentTimeMillis()+"");
        person.setPassword("123456");
        person.setUsername("张三2");

        personMapper.save(person);


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
    }

}



