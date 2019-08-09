package com.example.ldemo.utils.redis;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @package: com.example.ldemo.utils.redis
 * @className: ${TYPE_NAME}
 * @description: 消息订阅者
 * @author: 李臣臣
 * @createDate: 2019/8/9 10:36
 * @updateUser: 李臣臣
 * @updateDate: 2019/8/9 10:36
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
public class MapReceiver {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    public void receiveMessage(Object message) {
        logger.info("接收到集合消息：{}", JSON.toJSONString(message));
    }
}