package com.example.ldemo.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @package: com.example.ldemo.config
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/8/8 16:30
 * @updateUser: 李臣臣
 * @updateDate: 2019/8/8 16:30
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@Configuration
public class JedisClusterConfig {
    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public JedisCluster getJedisCluster(){
        String [] serverArray=redisProperties.getClusterNodes().split(",");
        Set<HostAndPort> nodes=new HashSet<>();

        for (String ipPort:serverArray){
            String [] ipPortPair=ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(),Integer.valueOf(ipPortPair[1].trim())));

        }
        return  new JedisCluster(nodes,redisProperties.getCommandTimeout());
    }

}