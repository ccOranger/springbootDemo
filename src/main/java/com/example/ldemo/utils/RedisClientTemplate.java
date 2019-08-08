package com.example.ldemo.utils;

import com.example.ldemo.config.redis.JedisClusterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @package: com.example.ldemo.config
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/8/8 16:38
 * @updateUser: 李臣臣
 * @updateDate: 2019/8/8 16:38
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@Service
public class RedisClientTemplate {
    private static final Logger log= LoggerFactory.getLogger(RedisClientTemplate.class);

    @Autowired
    private JedisClusterConfig jedisClusterConfig;

    public boolean setToRedis(String key,Object value){
        try {
            String str=jedisClusterConfig.getJedisCluster().set(key, String.valueOf(value));
            if("OK".equals(str)){

                return true;
            }
        }catch (Exception ex){
            log.error("setToRedis:{Key:"+key+",value"+value+"}",ex);
        }
        return false;
    }

    public Object getRedis(String key){
        String str=null;
        try {
            str=jedisClusterConfig.getJedisCluster().get(key);
        }catch (Exception ex){
            log.error("getRedis:{Key:"+key+"}",ex);
        }
        return str;
    }

}