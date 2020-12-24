package com.licc.es.entity.es;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.Document;



/**
 *  @author: 李臣臣
 *  @Date: 2020/12/23 0023 15:42
 *  @Description:
 *  该@Document标注有一个参数createIndex。如果将此参数设置为true（这是默认值），则Spring Data Elasticsearch将在启动应用程序启动时引导存储库支持期间检查由@Document注释定义的索引是否存在。
 * 如果不存在，那么将创建索引
 */
@Data
@TypeAlias("human")
@Document(indexName = "person")
public class Person implements Persistable<String> {

    @Id
    private String  id;
    private String username;
    private String password;
    private int age;

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
