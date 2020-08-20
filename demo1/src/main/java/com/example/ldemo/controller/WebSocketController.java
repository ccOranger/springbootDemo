package com.example.ldemo.controller;

import com.example.ldemo.entity.RequestMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.example.ldemo.entity.ResponseMessage;
import com.example.ldemo.entity.User;
import com.example.ldemo.service.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @package: cn.rayeye.micro.smoke.controller
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/6/6 16:30
 * @updateUser: 李臣臣
 * @updateDate: 2019/6/6 16:30
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private WebSocketServer webSocketServer;


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ResponseMessage say(RequestMessage message) {
        System.out.println(message.getName());
        return new ResponseMessage("welcome," + message.getName() + " !");
    }


    //广播推送消息
    @Scheduled(fixedRate = 10000)
    public void sendTopicMessage() {
     //   System.out.println("后台广播推送！");
        template.convertAndSend("/topic/greetings", "掌声");
        for (int i=1;i<4;i++){
            template.convertAndSend("/topic/greetings", "后台广播推送"+i);
        }
    }

    //一对一推送消息
    @Scheduled(fixedRate = 10000)
    public void sendQueueMessage() {
      //  System.out.println("后台一对一推送！");
        for (int i=1;i<4;i++){
            User user=new User();
            user.setId((long) i);
            user.setUserName("后台一对一推送");
            template.convertAndSendToUser(user.getId()+"","/queue/getResponse",user);
        }
    }




    @ApiOperation(value = "给指定用户发送信息")
    @GetMapping("test3")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "人员id", paramType = "String", required = true),
            @ApiImplicitParam(name = "param", value = "参数", paramType = "String", required = true),
    })
    public void test3(String userid,String param) {
        webSocketServer.sendInfo(userid,param);
    }

    @Scheduled(fixedRate = 10000)
    @ApiOperation(value = "给所有用户发送信息")
    @GetMapping("test4")
    public void test4() {
        webSocketServer.sendAllInfo("zhang");
    }

}
