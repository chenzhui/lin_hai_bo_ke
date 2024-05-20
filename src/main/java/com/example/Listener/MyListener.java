package com.example.Listener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener implements HttpSessionListener {
    public Map<Integer, Date> map = new HashMap<>();

    public void sessionCreabhted(HttpSessionEvent se){
    //    this.map.put(se.getSession().getId(),)
    };

    /*public void sessionDestroyed(HttpSessionEvent se) {
        this.map.remove(se.getSession().getId());
    }*/
}
