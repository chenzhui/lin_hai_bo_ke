package com.example.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanContentUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext0;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext0=applicationContext;
    }
    public static ApplicationContext getContext() {
        return applicationContext0 ;
    }
    public Object getBean(String beanName) {
        return applicationContext0.getBean(beanName);
    }
    public <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext0.getBean(beanName, clazz);
    }

    public <T> T getBean(Class<T> clazz) {
        return applicationContext0.getBean(clazz);
    }
}
