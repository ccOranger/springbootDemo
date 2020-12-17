package com.demo.valid.entity;

import com.demo.valid.service.First;
import com.demo.valid.service.Second;
import com.demo.common.validator.IsNumber;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author 李臣臣
 * @since 2020-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CorrectRecord对象", description="批改记录表")
public class OrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //有Frist.class 分组时 判断id不能为空
    @ApiModelProperty(value = "编号")
    @NotNull(groups = {First.class} ,message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "类型（0-批改 1-退保）")
    @NotEmpty(message = "类型不能为空")
    @IsNumber(message = "类型只能输入0或者1") //自定义校验
    @Min(value = 0,message = "类型只能输入0或者1")
    @Max(value = 1,message = "类型只能输入0或者1")
    private String type;

    @ApiModelProperty(value = "保单号")
    @NotEmpty(message = "保单号不能为空")
    @IsNumber(groups = {Second.class} ,message = "保单号只能是数字") //自定义校验
    private String policyNo;


    @ApiModelProperty(value = "金额")
    @Digits(integer = 10, fraction = 2,message = "金额必须是数字，并且只允许在10位整数和2位小数范围内")
    private String premium;

    @ApiModelProperty(value = "备注")
    private String remark;


    @ApiModelProperty(value = "附件列表")
    @Valid //嵌套验证必须加
    List<OrderAttachesVO> orderAttachesVOS;

}
