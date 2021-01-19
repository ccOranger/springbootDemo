package com.licc.rabbitmq.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());

        // 消息是否成功发送到Exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息成功发送到Exchange");
            //    String msgId = correlationData.getId();
            } else {
                log.info("消息发送到Exchange失败, {}, cause: {}", correlationData, cause);
            }
        });

        // 触发setReturnCallback回调必须设置mandatory=true, 否则Exchange没有找到Queue就会丢弃掉消息, 而不会触发回调
        rabbitTemplate.setMandatory(true);
        // 消息是否从Exchange路由到Queue, 注意: 这是一个失败回调, 只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息从Exchange路由到Queue失败: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}", exchange, routingKey, replyCode, replyText, message);
        });

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue apiLog() {
        return new Queue(MqConstants.QUEUE_API_LOG);
    }


    @Bean
    public Queue a() {
        return new Queue(MqConstants.QUEUE_API_A);
    }

    @Bean
    public Queue b() {
        return new Queue(MqConstants.QUEUE_API_B);
    }

    //Direct交换机 起名：exchange
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(MqConstants.TOPIC_EXCHANGE);
    }


    @Bean
    Binding bindingExchangeMessage3() {
        return BindingBuilder.bind(apiLog()).to(topicExchange()).with(MqConstants.QUEUE_API_LOG);
    }


    /**
     * 解耦，一对多
     */
    @Bean
    Binding bindingExchangeMessage4() {
        return BindingBuilder.bind(a()).to(topicExchange()).with(MqConstants.QUEUE_API_);
    }
    /**
     * 解耦，一对多
     */
    @Bean
    Binding bindingExchangeMessage5() {
        return BindingBuilder.bind(b()).to(topicExchange()).with(MqConstants.QUEUE_API_);
    }

//路由键必须是一串字符，用句号（.） 隔开，比如说 agreements.us，或者 agreements.eu.stockholm 等。
//路由模式必须包含一个 星号（*），主要用于匹配路由键指定位置的一个单词，
// 比如说，一个路由模式是这样子：agreements..b.*，那么就只能匹配路由键是这样子的：第一个单词是 agreements，第四个单词是 b。 井号（#）就表示相当于一个或者多个单词，例如一个匹配模式是agreements.eu.berlin.#，那么，以agreements.eu.berlin开头的路由键都是可以的。
}