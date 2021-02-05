package com.licc.es.entity.es;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Document(indexName = "goods_sku")
@Data
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Field(type = FieldType.Long,store = true)
    private Long id;            // 主键Id
    @Field(type = FieldType.Text,analyzer = "ik_smart",store = true)
    private String name;        // 商品名称
    @Field(type = FieldType.Integer,store = true)
    private Integer price;      // 商品价格
    @Field(type = FieldType.Text,store = true,index = false)
    private String image;       // 商品图片src
    @Field(type = FieldType.Text,store = true,index = false)
    private String createTime;    // 商品创建时间
    @Field(type = FieldType.Long,store = true,index = false)
    private Long spuId;         // Spu的Id
    @Field(type = FieldType.Keyword,store = true)
    private String categoryName;// 分类名称
    @Field(type = FieldType.Keyword,store = true)
    private String brandName;   // 品牌名称
    @Field(type = FieldType.Text,store = true)
    private Map spec;           // 规格Map Map<String,String>，如<"颜色","黑色">
    @Field(type = FieldType.Integer,store = true,index = false)
    private Integer saleNum;    // 销量


    public Goods(){

    }

    public Goods(Long id, String name, Integer price, String image, String createTime, Long spuId, String categoryName, String brandName, Map spec, Integer saleNum) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.createTime = createTime;
        this.spuId = spuId;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.spec = spec;
        this.saleNum = saleNum;
    }

}
