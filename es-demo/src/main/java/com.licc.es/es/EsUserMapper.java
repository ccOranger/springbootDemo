package com.licc.es.es;

import com.licc.es.entity.es.EsUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsUserMapper extends ElasticsearchRepository<EsUser, Integer> {
}
