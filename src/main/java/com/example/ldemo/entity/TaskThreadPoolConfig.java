package com.example.ldemo.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

/**
 * @package: com.example.ldemo.entity
 * @className: ${TYPE_NAME}
 * @description: 线程池配置属性类
 * @author: 李臣臣
 * @createDate: 2019/4/16 17:29
 * @updateUser: 李臣臣
 * @updateDate: 2019/4/16 17:29
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@Configuration
public class TaskThreadPoolConfig {

    @Value("${task.pool.corePoolSize}")
    private int corePoolSize;
    @Value("${task.pool.maxPoolSize}")
    private int maxPoolSize;
    @Value("${task.pool.keepAliveSeconds}")
    private int keepAliveSeconds;
    @Value("${task.pool.queueCapacity}")
    private int queueCapacity;

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }
}
