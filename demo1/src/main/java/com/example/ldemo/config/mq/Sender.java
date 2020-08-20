package com.example.ldemo.config.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息发送者 - Producer。
 * @Component Producer类型的对象，必须交由Spring容器管理。
 * 使用SpringBoot提供的AMQP启动器，来访问rabbitmq的时候，都是通过AmqpTemplate来实现的。
 * 如果全局配置文件中，配置了rabbitmq相关内容，且工程依赖了starter-amqp，则spring容器自动创建AmqpTemplate对象。
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitAmqpTemplate;

/**
 *  @author: 李臣臣
 *  @Date: 2020/03/02 0002 11:10
 *  @Description: 发送消息的方法
 */
    public void send(String exchange,String queue,Object object){
        /**
         * convertAndSend - 转换并发送消息的template方法。
         * 是将传入的普通java对象，转换为rabbitmq中需要的message类型对象，并发送消息到rabbitmq中。
         * 参数一：交换器名称。 类型是String
         * 参数二：路由键。 类型是String
         * 参数三：消息，是要发送的消息内容对象。类型是Object
         */
        this.rabbitAmqpTemplate.convertAndSend(exchange, queue, object);
    }

}