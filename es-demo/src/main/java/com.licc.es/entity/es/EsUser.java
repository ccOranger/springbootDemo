package com.licc.es.entity.es;


import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "users",type = "user")
public class EsUser {

    private Integer id;
    private String username;
    private String password;
    private int age;

}
