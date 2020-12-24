package com.licc.es.es;

import com.licc.es.entity.es.EsUser;
import com.licc.es.entity.es.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonMapper extends ElasticsearchRepository<Person, String> , QuerydslPredicateExecutor<Person> {
}
