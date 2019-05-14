package com.example.ldemo;

import com.example.ldemo.utils.AsyncTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * @package: com.example.ldemo
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/4/16 18:00
 * @updateUser: 李臣臣
 * @updateDate: 2019/4/16 18:00
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTests {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void AsyncTaskTest() throws InterruptedException, ExecutionException {

        for (int i = 0; i < 100; i++) {
            //asyncTask.doTask1(i);
            asyncTask.doTask2(i);
        }
        logger.info("All tasks finished.");
    }

    @Test
    public  void  testMap(){
        Map map = new ConcurrentHashMap<>();

    }
}
