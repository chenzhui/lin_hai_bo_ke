package com.example.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

public class WebSocketUtil {
    public static final Map<String, Session> ONLINE_USER_SESSIONS = new ConcurrentHashMap<>();

    public static void sendMessage(Session session, String message) throws IOException {
        if (session == null)
            return;
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic == null)
            return;
        basic.sendText(message);
    }

    public static void sendAllMessage(String message) throws IOException {
        Collection<Session> sessions = ONLINE_USER_SESSIONS.values();
        for (Session session : sessions)
            sendMessage(session, message);
    }
}
