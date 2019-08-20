package com.example.ldemo.entity;

import java.io.Serializable;

/**
 * @package:        com.example.ldemo.entity
 * @className:      Role
 * @description:    类作用描述
 * @author:         李臣臣
 * @createDate:     2019/8/20 17:23
 * @updateUser:     李臣臣
 * @updateDate:     2019/8/20 17:23
 * @updateRemark:   The modified content
 * @version:        1.0
 * <p>copyright: Copyright (c) 2019/8/20</p>
 *
 */
public class Role implements Serializable {
    private Long id;
    private String name;
    private String nameZh;

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
