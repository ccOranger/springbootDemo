package com.example.ldemo.controller;

import com.example.ldemo.service.HelloService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package: com.example.ldemo.controller
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
@RestController
@RequestMapping(value = "/hello")
@Api(value="hello",description = "测试专用")
public class HelloController {
    @Value("${hello.name}")
    private String name;

    @Autowired
    private HelloService helloService;

    @ApiOperation(value = "测试专用", notes = "测试专用")
    @GetMapping(value = "/get")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "名字",paramType = "string")
    })
    public String getHello(String name){
        return  helloService.getHello(name);
    }
    @ApiOperation(value = "aop测试", notes = "aop测试")
    @GetMapping(value = "/getAop")
    public String getAop(){
        return  "aop测试";
    }
}
