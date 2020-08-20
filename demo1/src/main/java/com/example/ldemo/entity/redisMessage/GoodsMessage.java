package com.example.ldemo.entity.redisMessage;

/**
 * @package: com.example.ldemo.entity.redisMessage
 * @className: ${TYPE_NAME}
 * @description: 消息模型
 * @author: 李臣臣
 * @createDate: 2019/8/9 10:30
 * @updateUser: 李臣臣
 * @updateDate: 2019/8/9 10:30
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
public class GoodsMessage extends RedisMessage {
    private String goodsType;
    private String number;

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}