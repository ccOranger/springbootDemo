package com.example.ldemo.utils.redis;

import com.example.ldemo.entity.redisMessage.RedisMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @package: com.example.ldemo.utils.redis
 * @className: ${TYPE_NAME}
 * @description: 消息发布者
 * @author: 李臣臣
 * @createDate: 2019/8/9 10:34
 * @updateUser: 李臣臣
 * @updateDate: 2019/8/9 10:34
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@Service
public class Publisher {
    private final RedisTemplate<String, Object> redisMessageTemplate;

    @Autowired
    public Publisher(RedisTemplate<String, Object> redisMessageTemplate) {
        this.redisMessageTemplate = redisMessageTemplate;
    }

    public void pushMessage(String topic, RedisMessage message) {
        redisMessageTemplate.convertAndSend(topic,message);
    }
    public void pushMessage(String topic, Map message) {
        redisMessageTemplate.convertAndSend(topic,message);
    }
}
