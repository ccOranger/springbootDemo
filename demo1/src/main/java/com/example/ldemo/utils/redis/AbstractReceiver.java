package com.example.ldemo.utils.redis;

/**
 * @package: com.example.ldemo.utils.redis
 * @className: ${TYPE_NAME}
 * @description: 消息订阅者
 * @author: 李臣臣
 * @createDate: 2019/8/9 10:35
 * @updateUser: 李臣臣
 * @updateDate: 2019/8/9 10:35
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
public abstract class AbstractReceiver {
    public abstract void receiveMessage(Object message);
}