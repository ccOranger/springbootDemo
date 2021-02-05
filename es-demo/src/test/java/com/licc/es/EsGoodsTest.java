package com.licc.es;


import com.licc.es.entity.es.Goods;
import com.licc.es.entity.es.Person;
import com.licc.es.es.GoodsMapper;
import com.licc.es.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EsGoodsTest {

    @Autowired
    private GoodsMapper goodsRepository;


    @Autowired
    private ElasticsearchRestTemplate template;


    @Autowired
    private GoodsService goodsService;

    @Test
    public void createIndex(){
        template.createIndex(Goods.class);
    }

    @Test
    public void createDoc(){


        Map map1 = new HashMap();
        map1.put("颜色","蓝色");
        map1.put("套餐","标准套餐");
        Goods goods1 = new Goods(2L,"Redmi Note7秘境黑优惠套餐16G+64G",100,"xxxx","2021-01-20 17:11:13",2L,"手机","小米",map1,100);

        Map map2 = new HashMap();
        map2.put("颜色","蓝色");
        map2.put("套餐","标准套餐");
        Goods goods2 = new Goods(3L,"Redmi Note7秘境黑优惠套餐16G+64G",500,"xxxx","2021-01-20 17:11:13",3L,"手机","小米",map2,100);

        Map map3 = new HashMap();
        map3.put("颜色","黑色");
        map3.put("尺寸","64寸");
        Goods goods3 = new Goods(4L,"小米电视 黑色 64寸 优惠套餐",1000,"xxxx","2021-01-20 17:11:13",4L,"电视","小米",map3,100);

        Map map4 = new HashMap();
        map4.put("颜色","金色");
        map4.put("尺寸","46寸");
        Goods goods4 = new Goods(5L,"华为电视 金色 46寸 优惠套餐",1500,"xxxx","2021-01-20 17:11:13",5L,"电视","华为",map4,100);

        Map map5 = new HashMap();
        map5.put("颜色","白金色");
        map5.put("网络制式","全网通5G");
        Goods goods5 = new Goods(6L,"华为P30 金色 全网通5G 优惠套餐",2000,"xxxx","2021-01-20 17:11:13",6L,"手机","华为",map5,100);
        List<Goods> list = new ArrayList<>();
        list.add(goods1);
        list.add(goods2);
        list.add(goods3);
        list.add(goods4);
        list.add(goods5);
        goodsService.saveAll(list);


/*        Map map1 = new HashMap();
        map1.put("颜色","紫色");
        map1.put("套餐","标准套餐");
        Goods goods1 = new Goods(7L,"小米 Mini9秘境黑优惠套餐16G+64G",100,"xxxx","2021-01-20 17:11:13",2L,"手机","小米",100);
        goodsRepository.save(goods1);*/
        // 使用saveAll批量存储
    }


    @Test
    public void  ss(){

        Map searchMap = new HashMap();
        searchMap.put("keywords","小米");

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 构建查询
        BoolQueryBuilder boolQueryBuilder = buildBasicQuery(searchMap);
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        // 分类聚合名
        String groupName = "sku_category";
        // 构建聚合查询
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms(groupName).field("categoryName");
        nativeSearchQueryBuilder.addAggregation(termsAggregationBuilder);
        // 获取聚合分页结果

        Object searchHits =  template.search(nativeSearchQueryBuilder.build(),Goods.class);
        System.out.println(searchHits);
    }

    /**
     * 搜索方法 - searchMap应该由前端传过来
     * searchMap里封装了一些条件，根据条件进行过滤
     */
    @Test
    public void search(){
        // 搜索条件Map
        Map searchMap = new HashMap();
        searchMap.put("keywords","小米");
//        searchMap.put("category","手机");
//        searchMap.put("brand","小米");
        Map map = new HashMap();
        map.put("颜色","紫色");
//        map.put("","");   // 其他规格类型
   //     searchMap.put("spec",map);
//        searchMap.put("price","0-3000");

        // 返回结果Map
        Map resultMap = new HashMap();
        // 查询商品列表
    //    resultMap.putAll(searchSkuList(searchMap));
        // 查询分类列表
        List<Goods> categoryList = searchCategoryList(searchMap);
        resultMap.put("categoryList",categoryList);

        System.out.println(resultMap);
    }

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
     * 查询分类列表
     * @param searchMap
     * @return
     */
    private List<Goods> searchCategoryList(Map searchMap) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 构建查询
        BoolQueryBuilder boolQueryBuilder = buildBasicQuery(searchMap);
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        // 分类聚合名
        String groupName = "sku_category";
        // 构建聚合查询
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms(groupName).field("categoryName");
        nativeSearchQueryBuilder.addAggregation(termsAggregationBuilder);
        // 获取聚合分页结果


        SearchHits<Goods> res  = template.search(nativeSearchQueryBuilder.build(),Goods.class);

        List<Goods> categoryList = res.stream().map(bucket -> bucket.getContent()).collect(Collectors.toList());


        //AggregatedPage<Goods> goodsList = (AggregatedPage<Goods>) goodsRepository.search(nativeSearchQueryBuilder.build());
        // 在查询结果中找到聚合 - 根据聚合名称
       /* StringTerms stringTerms = (StringTerms) goodsList.getAggregation(groupName);
        // 获取桶
        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
        // 使用流Stream 将分类名存入集合
        List<String> categoryList = buckets.stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());*/
        // 打印分类名称
        categoryList.forEach(System.out::println);
        return categoryList;
    }

    /**
     * 查询Sku集合 - 商品列表
     * @param searchMap 查询条件
     * @return
     */
/*    private Map searchSkuList(Map searchMap) {
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
        AggregatedPage<Goods> goodsPage = template.queryForPage(build, Goods.class, esResultMapper);
        long total = goodsPage.getTotalElements();  // 总数据量
        long totalPage = goodsPage.getTotalPages(); // 总页数
        // ...你还要将是否有上页下页等内容传过去
        List<Goods> goodsList = goodsPage.getContent();
        goodsList.forEach(System.out::println);
        resultMap.put("rows",goodsList);
        resultMap.put("total",total);
        resultMap.put("totalPage",totalPage);
        return resultMap;
    }*/



    /**
     * 搜索方法 - searchMap应该由前端传过来
     * searchMap里封装了一些条件，根据条件进行过滤
     */
    @Test
    public void search1(){
        // 搜索条件Map
        Map searchMap = new HashMap();
        searchMap.put("keywords","小米");
//        searchMap.put("category","手机");
//        searchMap.put("brand","小米");
        Map map = new HashMap();
        map.put("颜色","紫色");
//        map.put("","");   // 其他规格类型
  //      searchMap.put("spec",map);
//        searchMap.put("price","0-3000");


       Map map1 =  goodsService.searchSkuList(searchMap);
        System.out.println(map1);
    }


    @Test
    public void testGetAll() {
        Long c = goodsRepository.count();
        System.out.println(c);
        Iterable<Goods> iterable = goodsRepository.findAll();
        iterable.forEach(e->System.out.println(e.toString()));
    }


}



