package com.example.ldemo.controller;

import com.example.ldemo.entity.RequestMessage;
import com.example.ldemo.entity.ResponseMessage;
import com.example.ldemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

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

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ResponseMessage say(RequestMessage message) {
        System.out.println(message.getName());
        return new ResponseMessage("welcome," + message.getName() + " !");
    }


    //广播推送消息
    @Scheduled(fixedRate = 10000)
    public void sendTopicMessage() {
        System.out.println("后台广播推送！");
        template.convertAndSend("/topic/greetings", "掌声");
        for (int i=1;i<4;i++){
            template.convertAndSend("/topic/greetings", "后台广播推送"+i);
        }
    }

    //一对一推送消息
    @Scheduled(fixedRate = 10000)
    public void sendQueueMessage() {
        System.out.println("后台一对一推送！");
        for (int i=1;i<4;i++){
            User user=new User();
            user.setId((long) i);
            user.setUserName("后台一对一推送");
            template.convertAndSendToUser(user.getId()+"","/queue/getResponse",user);
        }
    }

}
