package com.demo.common.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 *  @author: 李臣臣
 *  @Date: 2020/11/20 0020 13:57
 *  @Description: 自定义注解 - 校验数字
 */
@Documented
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsNumberValidator.class )
public @interface IsNumber {
    String message() default  "必须是数字";
    Class<?>[] groups() default  {};
    Class<? extends Payload>[] payload() default  {};
}
