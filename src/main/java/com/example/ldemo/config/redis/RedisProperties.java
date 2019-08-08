package com.example.ldemo.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @package: com.example.ldemo.config
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/8/8 16:26
 * @updateUser: 李臣臣
 * @updateDate: 2019/8/8 16:26
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@Component
@ConfigurationProperties(prefix = "spring.redis.cache")
public class RedisProperties {
        private int expireSeconds;
        private String clusterNodes;
        private int commandTimeout;

        public int getExpireSeconds() {
            return expireSeconds;
        }

        public void setExpireSeconds(int expireSeconds) {
            this.expireSeconds = expireSeconds;
        }

        public String getClusterNodes() {
            return clusterNodes;
        }

        public void setClusterNodes(String clusterNodes) {
            this.clusterNodes = clusterNodes;
        }

        public int getCommandTimeout() {
            return commandTimeout;
        }

        public void setCommandTimeout(int commandTimeout) {
            this.commandTimeout = commandTimeout;
        }

}
