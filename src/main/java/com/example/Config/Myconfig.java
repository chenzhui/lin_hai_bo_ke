package com.example.Config;

import com.example.Interceptor.MyInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class Myconfig implements WebMvcConfigurer {
    @Bean
    public MyInterceptor GetMyInterceptor() {
        return new MyInterceptor();
    }

    /*public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludes = new ArrayList<>();
        String[] a = {"/Login", "/SelectKinds", "/SelectKinds", "/GetAllBlogs", "/Register", "/Send", "/GetComment", "/GetBlog", "/GetTypeBlogs", "/error", "/InsertKind", "/DeleteKind", "/SearchBlogs", "/GetOwnerBlogs", "/GetNameById", "/GetAvatarById", "/static/**", "/avatar/**", "/css/**", "/js/**", "/picture/**", "/jsp/**", "/video/**", "/index.html", "/ReleaseBlog",  "/","UploadAvatar" };
        for (int i = 0; i < a.length; ) {
            excludes.add(a[i]);
            i++;
        }
        registry.addInterceptor((HandlerInterceptor)GetMyInterceptor()).addPathPatterns("/*").excludePathPatterns(excludes);
    }*/

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*" ).allowedMethods("GET", "POST" ).allowCredentials(true).maxAge(86400L).allowedHeaders("*");
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/audio/**").addResourceLocations("file:/www/server/tomcat9/webapps/audio/");
        registry.addResourceHandler("/video/**").addResourceLocations("file:/www/server/tomcat9/webapps/video/");
        registry.addResourceHandler("/picture/**").addResourceLocations("file:/www/server/tomcat9/webapps/picture/");
        registry.addResourceHandler("/avatar/**").addResourceLocations("file:/www/server/tomcat9/webapps/avatar/");
        registry.addResourceHandler("/css/**").addResourceLocations("file:/www/server/tomcat9/webapps/lin_hai_bo_ke-0.0.1-SNAPSHOT/WEB-INF/classes/static/assets/css/");
        registry.addResourceHandler("/js/**" ).addResourceLocations("file:/www/server/tomcat9/webapps/lin_hai_bo_ke-0.0.1-SNAPSHOT/WEB-INF/classes/static/assets/js/");

        /*registry.addResourceHandler("/audio/**").addResourceLocations("file:C:/website/audio/");
        registry.addResourceHandler("/video/**").addResourceLocations("file:C:/website/video/");
        registry.addResourceHandler("/picture/**").addResourceLocations("file:C:/website/picture/");
        registry.addResourceHandler("/avatar/**").addResourceLocations("file:C:/website/avatar/");*/


    }
}