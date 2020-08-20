package com.example.ldemo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体名称 organ_info
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
@ApiModel(value = "OrganInfo",description = "organ_info")
public class OrganInfo {
    /** 这是id (主健ID) (无默认值) */
    @ApiModelProperty(value =  "这是id", name = "id", example ="null")
    private Long id;

    /** 团队姓名(必填项) (无默认值) */
    @ApiModelProperty(value =  "团队姓名", name = "name", example ="null")
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