package com.example.ldemo.entity.es;


import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "orders",type = "order")
public class EsOrder {

    private String id;
    private String username;
    private String password;
    private int age;

}
