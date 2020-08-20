package com.example.ldemo.utils.async;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;


/**
 *  @author: 李臣臣
 *  @Date: 2019/12/25 0025 13:49
 *  @Description: 异步异常处理类
 */
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        logger.info("Async method: {} has uncaught exception,params:{}", method.getName(), JSON.toJSONString(params));

        logger.error("=========================="+ex.getMessage()+"=======================", ex);
        logger.error("exception method:"+method.getName());


        if (ex instanceof AsyncException) {
            AsyncException asyncException = (AsyncException) ex;
            logger.info("asyncException:{}",asyncException.getErrorMessage());
        }

        logger.info("Exception :");
        ex.printStackTrace();
    }
}