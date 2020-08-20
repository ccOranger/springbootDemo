package com.example.ldemo.es;

import com.example.ldemo.entity.es.EsOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsOrderMapper extends ElasticsearchRepository<EsOrder, String> {

}
