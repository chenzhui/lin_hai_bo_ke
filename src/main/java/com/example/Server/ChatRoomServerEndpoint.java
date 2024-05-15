package com.example.Server;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import com.example.Service.UserService;
import com.example.utils.SpringBeanContentUtil;
import com.example.utils.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@ServerEndpoint("/Chat/{userId}")
@RestController

public class ChatRoomServerEndpoint {
    private static UserService userService;
    @Autowired
    public void SetUserService(UserService userService){
        ChatRoomServerEndpoint.userService=userService;
    }

    @OnOpen
    public void openSession(@PathParam("userId")int userId, Session session) throws IOException {
        String userName=this.userService.GetName(userId);
        WebSocketUtil.ONLINE_USER_SESSIONS.put(userId, session);
        String str = "{\"name\":\"wzh\",\"message\":\"123\"}";
        WebSocketUtil.sendAllMessage(str);
    }

    @OnMessage
    public void onMessage(@PathParam("userId") int userId , String message) throws IOException {
        String userName=this.userService.GetName(userId);
        WebSocketUtil.sendAllMessage("{\"userId\":\""+userId+"\",\"name\":\""+userName+"\",\"message\":\""+message+"\"}");
        System.out.println("onmessage");
    }

    @OnClose
    public void onClose(@PathParam("userId") int userId , Session session) throws IOException {
        WebSocketUtil.ONLINE_USER_SESSIONS.remove(userId);
        try {session.close();} catch (IOException e) {e.printStackTrace();}
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        try {session.close();} catch (IOException e) {e.printStackTrace();}
    }
}

