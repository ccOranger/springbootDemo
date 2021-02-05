package com.licc.es.es;

import com.licc.es.entity.es.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsMapper extends ElasticsearchRepository<Goods, Long> {

}
