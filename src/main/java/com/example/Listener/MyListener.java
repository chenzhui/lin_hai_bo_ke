package com.example.Listener;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener implements HttpSessionListener {
    public Map<String, String> map = new HashMap<>();

    public void sessionCreated(HttpSessionEvent se) {}

    public void sessionDestroyed(HttpSessionEvent se) {
        this.map.remove(se.getSession().getId());
    }
}
