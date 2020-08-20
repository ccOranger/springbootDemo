package com.example.ldemo.entity.redisMessage;

/**
 * @package: com.example.ldemo.entity.redisMessage
 * @className: ${TYPE_NAME}
 * @description: 消息模型
 * @author: 李臣臣
 * @createDate: 2019/8/9 10:31
 * @updateUser: 李臣臣
 * @updateDate: 2019/8/9 10:31
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
public class UserMessage extends RedisMessage {
    private String userId;
    private String username;
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}