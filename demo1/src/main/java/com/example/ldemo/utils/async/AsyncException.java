package com.example.ldemo.utils.async;


/**
 *  @author: 李臣臣
 *  @Date: 2019/12/25 0025 13:48
 *  @Description: 自定义异步处理异常类
 */
public class AsyncException extends  Exception {
    private int code;
    private String errorMessage;



    public AsyncException(int code,String errorMessage){
        this.code = code;
        this.errorMessage = errorMessage;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
