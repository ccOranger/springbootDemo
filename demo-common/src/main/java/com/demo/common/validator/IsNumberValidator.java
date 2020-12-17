package com.demo.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IsNumberValidator implements ConstraintValidator<IsNumber,Object> {

    private static Pattern NUMBER_PATTERN = Pattern.compile("-?[0-9]+.?[0-9]+");

    @Override
    public void initialize(IsNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return isNumeric(value);
    }


    /**
     *  @author: 李臣臣
     *  @Date: 2020/11/20 0020 13:58
     *  @Description: 判断是否是数字
     */
    public boolean isNumeric(Object value) {
        if (value == null){
             return  false;
        }
        String str = value.toString();
        Matcher isNum = NUMBER_PATTERN.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

}
