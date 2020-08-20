package com.example.ldemo.entity;

/**
 * 实体名称 build
 * @projectName: iot-platform
 * @package: com.rayeye.iot.persist.vo
 * @className: ${TYPE_NAME}
 * @description: Describes the function of the class
 * @author: 李臣臣
 * @createDate: 2019-04-29
 * @updateUser: 李臣臣
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class Organ {
    /** 这是id (主健ID) (无默认值) */
    private Long id;

    /**  (无默认值) */
    private String name;

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
        this.name = name == null ? null : name.trim();
    }
}