package com.example.ldemo.controller;

import com.example.ldemo.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.example.ldemo.entity.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package:        com.example.ldemo.controller
 * @className:      RegLoginController
 * @description:    类作用描述
 * @author:         李臣臣
 * @createDate:     2019/8/20 15:48
 * @updateUser:     李臣臣
 * @updateDate:     2019/8/20 15:48
 * @updateRemark:   The modified content
 * @version:        1.0
 * <p>copyright: Copyright (c) 2019/8/20</p>
 *
 */
@Api(value="登陆",description = "登陆")
@RestController
public class RegLoginController {
    @RequestMapping("/login_p")
    public RespBean loginP() {
        return RespBean.error("尚未登录，请登录!");
    }

    @ApiOperation(value = "登陆", notes = "登陆")
    @RequestMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName",value = "名字",paramType = "string"),
            @ApiImplicitParam(name = "passWord",value = "名字",paramType = "string")
    })
    public RespBean login() {
        return RespBean.error("尚未登录，请登录!");
    }


    @GetMapping("/employee/advanced/hello")
    public RespBean hello() {
        return RespBean.ok("hello", UserUtils.getCurrentHr());
    }
    @GetMapping("/employee/basic/hello")
    public String basicHello() {
        return "basicHello";
    }
}
