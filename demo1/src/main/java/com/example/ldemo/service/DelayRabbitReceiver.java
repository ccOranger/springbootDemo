package com.example.ldemo.service;

import com.rabbitmq.client.Channel;
import com.example.ldemo.config.mq.DelayRabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;


/**
 * @author 李臣臣
 */
@Component
@Slf4j
public class DelayRabbitReceiver {

    @RabbitListener(queues = DelayRabbitConfig.DEAD_LETTER_QUEUEA_NAME)
    public void receiveA(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},死信队列A收到消息：{}", new Date().toString(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = DelayRabbitConfig.DEAD_LETTER_QUEUEB_NAME)
    public void receiveB(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},死信队列B收到消息：{}", new Date().toString(), msg);
        //maintenanceFeignClient.finishExecute(executeId);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
    /**
     * 消费消息的方法。采用消息队列监听机制
     * @RabbitHandler - 代表当前方法是监听队列状态的方法，就是队列状态发生变化后，执行的消费消息的方法。
     * 方法参数。就是处理的消息的数据载体类型。
     */


}
