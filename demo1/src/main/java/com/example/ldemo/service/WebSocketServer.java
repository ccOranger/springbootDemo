package com.example.ldemo.service;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *  @author: 李臣臣
 *  @Date: 2020/08/19 0019 17:59
 *  @Description: websocket 服务
 */
@ServerEndpoint("/webSocket/{sid}")
@Component
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    //发送消息
    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
//                System.out.println("发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        }
    }
    //给指定用户发送信息
    public void sendInfo(String userName, String message){
        Session session = sessionPools.get(userName);
        try {
            sendMessage(session, message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //给所有用户发送信息
    public void sendAllInfo( String message){

        for (Map.Entry<String, Session> sessionEntry : sessionPools.entrySet()) {
            Session toSession = sessionEntry.getValue();
            try {
                sendMessage(toSession, message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String userName){
        sessionPools.put(userName, session);
        addOnlineCount();
        System.out.println(userName + "加入webSocket！当前人数为" + onlineNum);
        try {
            sendMessage(session, "欢迎" + userName + "加入连接！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "sid") String userName){
        sessionPools.remove(userName);
        subOnlineCount();
        System.out.println(userName + "断开webSocket连接！当前人数为" + onlineNum);
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String message) throws IOException{
        message = "客户端：" + message + ",已收到";
        System.out.println(message);
        for (Session session: sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }


    /**
     *  nginx 配置
     * 		location /yg/webSocket/ {
     * 		proxy_pass http://127.0.0.1:8208/webSocket/;
     * 		proxy_set_header Upgrade $http_upgrade;
     * 		proxy_set_header Connection "upgrade";
     * 		proxy_set_header X-real-ip $remote_addr;
     * 		#proxy_set_header X-Forwarded-For $remote_addr;
     * 		#proxy_connect_timeout 60;
     * 		#proxy_read_timeout 90;
     * 		proxy_ignore_headers X-Accel-Expires Expires Cache-Control;
     * 		proxy_ignore_headers Set-Cookie;
     * 		proxy_hide_header Set-Cookie;
     * 		proxy_hide_header X-powered-by;
     * 		proxy_set_header X-Real-IP $remote_addr;
     * 		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
     * 		proxy_set_header X-Forwarded-Proto https;
     * 		proxy_set_header Host $http_host;
     * 		#配置websocket 需要加下面这个
     * 		proxy_http_version 1.1;
     * 		proxy_connect_timeout 60;
     *         proxy_read_timeout 3600;
     *         proxy_send_timeout 60;
     *        }
     *
     */
}
