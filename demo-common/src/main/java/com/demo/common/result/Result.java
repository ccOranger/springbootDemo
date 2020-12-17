package com.demo.common.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(
        value = "Result对象",
        description = "结果对象"
)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 8051203182511793143L;
    @ApiModelProperty(
            value = "状态码",
            name = "code",
            example = "200"
    )
    private int code = 200;
    @ApiModelProperty(
            value = "接口执行状态消息",
            name = "message",
            example = "成功！"
    )
    private String message = "操作成功!";

    @ApiModelProperty(
            value = "请求返回消息对象",
            name = "data",
            example = "User.class"
    )
    private T data = null;

    public Result(int code, String message,  T data){
        this.code = code;
        this.message = message ;
        this.data = data;
    }

    public Result(){

    }
    public static Result build() {
        return new Result();
    }

    public static Result error() {
        return new Result().setCode(ResultCode.HTTP_500).setMessage("操作失败");
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
