package com.licc.es.es;

import com.licc.es.entity.es.EsOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsOrderMapper extends ElasticsearchRepository<EsOrder, String> {

}
