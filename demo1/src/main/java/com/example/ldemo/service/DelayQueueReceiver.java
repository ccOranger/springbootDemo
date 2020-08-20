package com.example.ldemo.service;


import com.example.ldemo.config.mq.DelayQueueConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class DelayQueueReceiver {


    @RabbitListener(queues = DelayQueueConfig.DELAY_PROCESS_QUEUE_NAME)
    @RabbitHandler
    public void process(String s){

        System.out.println(s);
    }
}
