package com.example.ldemo.service;

import com.example.ldemo.dao.HelloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @package: com.example.ldemo.service
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/4/11 15:39
 * @updateUser: 李臣臣
 * @updateDate: 2019/4/11 15:39
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@Service
public class HelloService {
    @Value("${hello.name}")
    private String name;

    private static Logger logger = LoggerFactory.getLogger(HelloService.class);
    @Autowired
    HelloMapper helloMapper;

    public String getHello(){
        logger.debug("hahahh");

      Map map =  helloMapper.findByUserName(11);

      Map map1 =  helloMapper.findByUserId();

        return  map.get("user_name").toString()+">>>>>"+map1.get("user_name").toString();

    }
}
