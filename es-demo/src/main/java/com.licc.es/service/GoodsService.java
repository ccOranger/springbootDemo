package com.licc.es.service;

import com.licc.es.entity.es.Goods;
import com.licc.es.entity.es.Person;
import com.licc.es.es.PersonMapper;
import com.licc.es.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class GoodsService {



    @Autowired
    private GoodsService goodsService;


    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 构建基本查询 - 搜索关键字、分类、品牌、规格、价格
     * @param searchMap
     * @return
     */
    private BoolQueryBuilder buildBasicQuery(Map searchMap) {
        // 构建布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 关键字查询
        boolQueryBuilder.must(QueryBuilders.matchQuery("name",searchMap.get("keywords")));
        // 分类、品牌、规格 都是需要精准查询的，无需分词
        // 商品分类过滤
        if (searchMap.get("category") != null){
            boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("categoryName",searchMap.get("category")));
        }
        // 商品品牌过滤
        if(searchMap.get("brand") != null){
            boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("brandName",searchMap.get("brand")));
        }
        // 规格过滤
        if(searchMap.get("spec") != null){
            Map<String,String> map = (Map) searchMap.get("spec");
            for(Map.Entry<String,String> entry : map.entrySet()){
                // 规格查询[spec.xxx],因为规格是不确定的，所以需要精确查找，加上.keyword，如spec.颜色.keyword
                boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("spec." + entry.getKey() + ".keyword",entry.getValue()));
            }
        }
        // 价格过滤
        if(searchMap.get("price") != null){
            // 价格： 0-500  0-*
            String[] prices = ((String)searchMap.get("price")).split("-");
            if(!prices[0].equals("0")){  // 加两个0是，因为价格转换成分
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gt(prices[0] + "00"));
            }
            if(!prices[1].equals("*")){  // 价格有上限
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").lt(prices[1] + "00"));
            }
        }
        return boolQueryBuilder;
    }



    /**
     * 查询Sku集合 - 商品列表
     * @param searchMap 查询条件
     * @return
     */
    public Map searchSkuList(Map searchMap) {
        Map resultMap = new HashMap();
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = buildBasicQuery(searchMap);
        // 查询
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        // 排序
        String sortField = (String)searchMap.get("sortField");      // 排序字段
        String sortRule = (String)searchMap.get("sortRule");        // 排序规则 - 顺序(ASC)/倒序(DESC)
        if(sortField!= null && !"".equals(sortField)){
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortField).order(SortOrder.valueOf(sortRule)));
        }
        // 构建分页
        nativeSearchQueryBuilder.withPageable(PageRequest.of(0,15));

        // 构建高亮查询
        HighlightBuilder.Field field = new HighlightBuilder.Field("name").preTags("<font style='color:red'>").postTags("</font>");
        nativeSearchQueryBuilder.withHighlightFields(field);  // 名字高亮
        NativeSearchQuery build = nativeSearchQueryBuilder.build();
        // 获取查询结果

        SearchHits<Goods> searchHits = elasticsearchRestTemplate.search(build,Goods.class);


        List list = new ArrayList();
        System.out.println(searchHits);
        searchHits.forEach(personSearchHit -> {
            System.out.println(personSearchHit);
            list.add(personSearchHit.getContent());
        });


        long total = searchHits.getTotalHits();  // 总数据量
        // ...你还要将是否有上页下页等内容传过去
        resultMap.put("rows",searchHits);
        resultMap.put("total",total);
        resultMap.put("list",list.toString());

        return resultMap;
    }

}
