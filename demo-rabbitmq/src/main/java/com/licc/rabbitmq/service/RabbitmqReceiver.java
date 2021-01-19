package com.licc.rabbitmq.service;

import com.licc.rabbitmq.config.MqConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RabbitListener(queues = MqConstants.QUEUE_API_LOG)
@Slf4j
public class RabbitmqReceiver {


    /**
     * 消费消息的方法。采用消息队列监听机制
     * @RabbitHandler - 代表当前方法是监听队列状态的方法，就是队列状态发生变化后，执行的消费消息的方法。
     * 方法参数。就是处理的消息的数据载体类型。
     */
    @RabbitHandler
    public void process(Object apiLog, Message message, Channel channel) {


        MessageProperties properties = message.getMessageProperties();
        long tag = properties.getDeliveryTag();

        try {

            Thread.sleep(2000L);
            log.info("apiLog===={}",apiLog);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                // 消费确认
                channel.basicAck(tag, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}