package com.example.ldemo.config.redis;

import com.example.ldemo.utils.redis.UserReceiver;
import com.example.ldemo.utils.redis.GoodsReceiver;
import com.example.ldemo.utils.redis.MapReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @package: com.example.ldemo.config.redis
 * @className: ${TYPE_NAME}
 * @description: 订阅者配置
 * @author: 李臣臣
 * @createDate: 2019/8/9 10:24
 * @updateUser: 李臣臣
 * @updateDate: 2019/8/9 10:24
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@Configuration
public class ReceiverConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,MessageListenerAdapter mapListenerAdapter, MessageListenerAdapter userListenerAdapter, MessageListenerAdapter goodsListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(mapListenerAdapter, new PatternTopic("map"));
        container.addMessageListener(userListenerAdapter, new PatternTopic("user"));
        container.addMessageListener(goodsListenerAdapter, new PatternTopic("goods"));
        return container;
    }

    @Bean
    public MessageListenerAdapter mapListenerAdapter(MapReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
    @Bean
    public MessageListenerAdapter userListenerAdapter(UserReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    public MessageListenerAdapter goodsListenerAdapter(GoodsReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    public MapReceiver mapReceiver() {
        return new MapReceiver();
    }
    @Bean
    public UserReceiver userReceiver() {
        return new UserReceiver();
    }

    @Bean
    public GoodsReceiver goodsReceiver() {
        return new GoodsReceiver();
    }

}