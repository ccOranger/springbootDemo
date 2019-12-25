package com.example.ldemo.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.ldemo.utils.async.AsyncException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import javax.xml.soap.SAAJResult;
import java.util.concurrent.Future;

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

    @Async("myTaskAsyncPool")  //myTaskAsynPool即配置线程池的方法名，此处如果不写自定义线程池的方法名，会使用默认的线程池,可通过线程名称看出
    public void doTask1(int i) {
        logger.info("Task"+i+" started.");
        logger.info("Thread {} deal No Return Task start", Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Thread {} deal No Return Task end at {}", Thread.currentThread().getName(), System.currentTimeMillis());
    }


    /**
    * @Description 使用默认的线程池,可通过线程名称看出
    * @Author  李臣臣
    * @Date   2019/12/25 0025 13:33
    * @Param  
    * @Return      
    * @Exception   
    * 
    */
    @Async
    public void doTask2(int i)  throws AsyncException{
        logger.info("Task2-Native"+i+" started.");
        logger.info("Thread {} deal No Return Task start", Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //异常处理测试
        try{
            String s = null;
            s.equals("111");
        }catch (Exception e){
                throw  new AsyncException(300,"异常");
        }


        logger.info("Thread {} deal No Return Task end at {}", Thread.currentThread().getName(), System.currentTimeMillis());
    }


    /**
    * @Description 异步调用返回数据，Future表示在未来某个点获取执行结果，返回数据类型可以自定义
    * @Author  李臣臣
    * @Date   2019/12/25 0025 13:33
    * @Param  
    * @Return      
    * @Exception   
    * 
    */
    @Async
    public Future<String> dealHaveReturnTask() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("thread", Thread.currentThread().getName());
        jsonObject.put("time", System.currentTimeMillis());
        return new AsyncResult<String>(jsonObject.toJSONString());
    }

}
