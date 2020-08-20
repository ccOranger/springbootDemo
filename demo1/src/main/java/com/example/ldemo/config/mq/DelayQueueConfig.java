package com.example.ldemo.config.mq;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *  @author: 李臣臣
 *  @Date: 2020/05/20 0020 13:55
 *  @Description: rabbitmq 实现延时队列
 */
@Configuration
public class DelayQueueConfig {
    public final static String DELAY_QUEUE_PER_QUEUE_TTL_NAME = "delay_queue_per_queue_ttl";

    public final static String DELAY_PROCESS_QUEUE_NAME = "delay_process_queue";

    public final static String DELAY_EXCHANGE_NAME = "delay_exchange";

    public final static int QUEUE_EXPIRATION = 4000;


    /**
     * 通过x-dead-letter-exchange设置队列的死信路由，那么出现dead letter之后将dead letter重新发送到指定exchange；
     *
     * 通过x-dead-letter-routing-key设置路由键：出现dead letter之后将dead letter重新按照指定的routing-key发送；
     *
     * 通过x-message-ttl设置队列的过期时间；
     *
     * 后面设置了处理队列，并且将路由和队列绑定起来；
     * ————————————————
     * 版权声明：本文为CSDN博主「xiaoguangtouqiang」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/xiaoguangtouqiang/java/article/details/90734440
     * @return
     */
    @Bean
    Queue delayQueuePerQueueTTL() {
        return QueueBuilder.durable(DELAY_QUEUE_PER_QUEUE_TTL_NAME)
                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE_NAME) // DLX
                .withArgument("x-dead-letter-routing-key", DELAY_PROCESS_QUEUE_NAME) // dead letter携带的routing key
                .withArgument("x-message-ttl", QUEUE_EXPIRATION) // 设置队列的过期时间
                .build();
    }

    @Bean
    Queue delayProcessQueue() {
        return QueueBuilder.durable(DELAY_PROCESS_QUEUE_NAME)
                .build();
    }

    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE_NAME);
    }

    @Bean
    Binding dlxBinding(Queue delayProcessQueue, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayProcessQueue)
                .to(delayExchange)
                .with(DELAY_PROCESS_QUEUE_NAME);
    }

}
