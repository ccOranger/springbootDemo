package com.example.ldemo.entity.redisMessage;

import java.io.Serializable;

/**
 * @package: com.example.ldemo.entity.redisMessage
 * @className: ${TYPE_NAME}
 * @description: 消息模型
 * @author: 李臣臣
 * @createDate: 2019/8/9 10:29
 * @updateUser: 李臣臣
 * @updateDate: 2019/8/9 10:29
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */

public class RedisMessage implements Serializable {
    public String msgId;
    public long createStamp;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public long getCreateStamp() {
        return createStamp;
    }

    public void setCreateStamp(long createStamp) {
        this.createStamp = createStamp;
    }
}
