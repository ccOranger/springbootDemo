package com.licc.es.service;

import com.alibaba.fastjson.JSONArray;
import com.licc.es.entity.es.Person;
import com.licc.es.es.PersonMapper;
import com.licc.es.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class PersonService {



    @Autowired
    private PersonMapper personMapper;


    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    public long count() {
        return personMapper.count();
    }

    public Person save(Person person) {
        return personMapper.save(person);
    }

    public void delete(Person person) {
        personMapper.delete(person);
//        commodityRepository.deleteById(person.getSkuId());
    }

    public Iterable<Person> getAll() {
        return personMapper.findAll();
    }
    public List<Person> getByName(String name) {


        List<Person> list = new ArrayList<>();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("username", name);
        Iterable<Person> iterable = personMapper.search(matchQueryBuilder);
        iterable.forEach(e->list.add(e));
        return list;
    }

    public Page<Person> pageQuery(Integer pageNo, Integer pageSize, String kw) {


        NativeSearchQueryBuilder nativeSearchQueryBuilder =new NativeSearchQueryBuilder();

        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchPhraseQuery("username", kw))
                .withPageable(PageRequest.of(pageNo, pageSize))
                .build();
        return personMapper.search(nativeSearchQueryBuilder.build());


    }


    /**
     *  查询不分页
     * @param kw
     * @return
     */
    public List<Person> getList( String kw){

        List<Person> list = new ArrayList<>();
        NativeSearchQueryBuilder nativeSearchQueryBuilder =new NativeSearchQueryBuilder();

        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchPhraseQuery("username", kw));


        SearchHits<Person> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(),Person.class);


        searchHits.forEach(personSearchHit -> {
            list.add(personSearchHit.getContent());
            System.out.println(personSearchHit.getContent());
        });


        return list;

    }



    public List<Person> getList1( String kw){


        List<Person> list = new ArrayList<>();
        NativeSearchQueryBuilder nativeSearchQueryBuilder =new NativeSearchQueryBuilder();

        nativeSearchQueryBuilder.withQuery(
                QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("username", kw))
                .must(QueryBuilders.queryStringQuery("ERROR").field("message")));


        SearchHits<Person> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(),Person.class);


        searchHits.forEach(personSearchHit -> {
            list.add(personSearchHit.getContent());
            System.out.println(personSearchHit.getContent());
        });


        return list;

    }


    /**
     *  @author: 李臣臣
     *  @Date: 2021/02/05 0005 09:51
     *  @Description: 查询分页
     */
    public PageUtils<Person> getPageList(Integer pageNo, Integer pageSize, String kw){

        List list  = new ArrayList();

        NativeSearchQueryBuilder nativeSearchQueryBuilder =new NativeSearchQueryBuilder();

        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchPhraseQuery("username", kw))
                .withPageable(PageRequest.of(pageNo, pageSize));

        SearchHits<Person> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(),Person.class);

        System.out.println(searchHits);
        searchHits.forEach(personSearchHit -> {
            System.out.println(personSearchHit);
            list.add(personSearchHit.getContent());
        });

        log.info("返回总数为：" + searchHits.getTotalHits());
        int total = (int)searchHits.getTotalHits();



        PageUtils<Person> page = new PageUtils<>();
        // 封装分页
        page.setList(list);
        page.setPageNum(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(total);
        page.setPages(total== 0 ? 0: (total%pageSize == 0 ? total / pageSize : (total / pageSize) + 1));


        return page;

    }

    
}
