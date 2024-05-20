package com.example.utils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Protect_Login_Util {
    public static Map<Integer, Date> map=new HashMap<Integer,Date>();
    public void AddLoginRecord(Integer integer){
        map.put(integer,new Date());
    }
    public void UpdateLoginRecord(Integer integer){
        map.replace(integer,new Date());
    }
    public void DeleteLoginRecord(Integer integer){
        map.remove(integer);
    }

    public boolean CheckLoginRecord(Integer integer){
        if(this.map.containsKey(integer)&&(new Date().getTime()-this.map.get(integer).getTime())<259200000){
            return false;
        }
        return true;
    }
}
