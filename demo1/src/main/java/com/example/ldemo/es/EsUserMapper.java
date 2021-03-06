package com.example.ldemo.es;

import com.example.ldemo.entity.es.EsUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsUserMapper extends ElasticsearchRepository<EsUser, Integer> {
}
