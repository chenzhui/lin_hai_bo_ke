package com.example.Config;

import com.example.Interceptor.MyInterceptor;
import java.util.ArrayList;
import java.util.List;

import com.example.Server.ChatRoomServerEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpoint;


@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class Myconfig implements WebMvcConfigurer {
    @Bean
    public MyInterceptor GetMyInterceptor() {
        return new MyInterceptor();
    }

    /*public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludes = new ArrayList<>();
        /*String[] a = {"/Login", "/SelectKinds", "/SelectKinds", "/GetAllBlogs", "/Register", "/Send", "/GetComment", "/GetBlog", "/GetTypeBlogs", "/error", "/InsertKind", "/DeleteKind", "/SearchBlogs", "/GetOwnerBlogs", "/GetNameById", "/GetAvatarById", "/static/**", "/avatar/**", "/css/**", "/js/**", "/picture/**", "/jsp/**", "/video/**", "/index.html", "/ReleaseBlog",  "/","UploadAvatar","/Index" };
        for (int i = 0; i < a.length; ) {
            excludes.add(a[i]);
            i++;
        }

        registry.addInterceptor((HandlerInterceptor)GetMyInterceptor()).addPathPatterns("/*").excludePathPatterns(excludes);
    }*/
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//设置允许跨域请求的路由
                .allowedOriginPatterns("*" )//设置允许跨域请求的域名
                .allowedMethods("GET", "POST" )//设置允许的方法
                .allowCredentials(true)//是否允许证书
                .maxAge(86400L)//跨域允许时间
                .allowedHeaders("*");
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/audio/**").addResourceLocations("file:/www/server/tomcat9/webapps/audio/");
        registry.addResourceHandler("/api/video/**").addResourceLocations("file:/www/server/tomcat9/webapps/video/");
        registry.addResourceHandler("/api/picture/**").addResourceLocations("file:/www/server/tomcat9/webapps/picture/");
        registry.addResourceHandler("/api/avatar/**").addResourceLocations("file:/www/server/tomcat9/webapps/avatar/");
    }
}