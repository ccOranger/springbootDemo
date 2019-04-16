package com.example.ldemo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @package: com.example.ldemo.service
 * @className: ${TYPE_NAME}
 * @description: 创建线程任务
 * @author: 李臣臣
 * @createDate: 2019/4/16 17:44
 * @updateUser: 李臣臣
 * @updateDate: 2019/4/16 17:44
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@Component
public class AsyncTask {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Async("myTaskAsyncPool")  //myTaskAsynPool即配置线程池的方法名，此处如果不写自定义线程池的方法名，会使用默认的线程池
    public void doTask1(int i) throws InterruptedException{
        logger.info("Task"+i+" started.");
    }

    @Async
    public void doTask2(int i) throws InterruptedException{
        logger.info("Task2-Native"+i+" started.");
    }
}
