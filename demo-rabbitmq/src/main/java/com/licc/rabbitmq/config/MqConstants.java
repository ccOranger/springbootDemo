package com.licc.rabbitmq.config;

/**
 *  @author: 李臣臣
 *  @Date: 2020/03/02 0002 10:55
 *  @Description: rebbitmq配置
 */
public interface  MqConstants {


    /**
     * 请求日志
     */
    String  QUEUE_API_LOG = "queue.direct.api.log";


    /**
     * 解耦，一对多
     */
    String  QUEUE_API_A = "queue.api.a";

    /**
     * 解耦，一对多
     */
    String  QUEUE_API_B = "queue.api.b";


    String  QUEUE_API_ = "queue.api.#";

    /**
     * 交换机
     */
    String  TOPIC_EXCHANGE = "topicexchange";

}
