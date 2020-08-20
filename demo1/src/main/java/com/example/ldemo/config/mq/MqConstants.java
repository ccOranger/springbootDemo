package com.example.ldemo.config.mq;

/**
 *  @author: 李臣臣
 *  @Date: 2020/03/02 0002 10:55
 *  @Description: rebbitmq配置
 */
public interface  MqConstants {


    /**
     * 发送短信
     */
    String  QUEUE_SEND_SMS = "queue.direct.send.sms";
    /**
     * 发送邮件
     */
    String  QUEUE_SEND_EMAIL = "queue.direct.send.email";
    /**
     * 请求日志
     */
    String  QUEUE_API_LOG = "queue.direct.api.log";
    /**
     * 投保单队列
     */
    String  QUEUE_APPLY_POLICY = "queue.direct.apply.policy";

    /**
     * 订单日志
     */
    String  QUEUE_ORDER_LOG = "queue.direct.order.log";
    /**
     * 运维日志
     */
    String  QUEUE_DEVOPS_LOG = "queue.direct.devops.log";
    /**
     * 批改
     */
    String  QUEUE_CORRECT_POLICY = "queue.direct.correct.log";
    /**
     * 用户更新成单
     */
    String  QUEUE_USER_BUY = "queue.direct.user.buy";




    /**
     * 交换机
     */
    String  TOPIC_EXCHANGE = "topicexchange";

}
