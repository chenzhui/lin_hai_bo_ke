package com.example.Interceptor;

import com.example.utils.TokenUtil;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MyInterceptor implements HandlerInterceptor {
    private int yangToken = 10000000;

    private int oldToken = 86400000;

    TokenUtil tokenUtil = new TokenUtil();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        System.out.println(request.getRequestURL());
        if (object.toString().equals("ParameterizableViewController [view=\"forward:index.html\"]")){return true;}
        String token = request.getHeader("token");
        if (token == null) {
            response.setStatus(401);
            return false;
        }
        Map<String, String> map = this.tokenUtil.parseToken(token);
        if (!((String)map.get("id")).equals(request.getParameter("id"))) {
            response.setStatus(401);
            return false;
        }
        long timeOfUse = System.currentTimeMillis() - Long.parseLong(map.get("time"));
        if (timeOfUse >= this.yangToken && timeOfUse < this.oldToken) {
            Cookie cookie = new Cookie("token", this.tokenUtil.GetToken(Integer.parseInt(map.get("id")), map.get("name"), map.get("avatar")));
            cookie.setPath("/");
            cookie.setMaxAge(259200);
            Cookie cookie1 = new Cookie("id", map.get("id"));
            cookie1.setPath("/");
            cookie1.setMaxAge(259200);
            Cookie cookie2 = new Cookie("name", map.get("name"));
            cookie2.setPath("/");
            cookie2.setMaxAge(259200);
            Cookie cookie3 = new Cookie("avatar", map.get("avatar"));
            cookie3.setPath("/");
            cookie3.setMaxAge(259200);
            response.addCookie(cookie);
            response.addCookie(cookie1);
            response.addCookie(cookie2);
            response.addCookie(cookie3);
        }
        return true;
    }
}