package com.demo.valid.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author 李臣臣
 * @since 2020-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OrderAttachesVO")
public class OrderAttachesVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "文件大小")
    private String size;

    @ApiModelProperty(value = "文件别名")
    private String downName;

    @ApiModelProperty(value = "文件地址")
    @NotEmpty(message = "文件地址不能为空")
    private String path;



}
