package com.licc.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.licc.rabbitmq.config.MqConstants;
import com.licc.rabbitmq.config.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqTests {
    @Autowired
    private Sender sender;


    @Test
     public void  test(){

        for (int i=0;i<10;i++){

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ssss","222"+i);
            //mq异步通知获取投保单
            sender.send(MqConstants.TOPIC_EXCHANGE, MqConstants.QUEUE_API_LOG,jsonObject);
        }
    }



    @Test
    public void  testA(){

        for (int i=0;i<10;i++){

            //mq异步通知获取投保单
            sender.send(MqConstants.TOPIC_EXCHANGE, MqConstants.QUEUE_API_,i);
        }
    }
}
