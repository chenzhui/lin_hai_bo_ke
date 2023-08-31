package com.example.utils;

import java.util.Random;
import javax.servlet.http.HttpSession;

public class RandomFourUtil {
    private Random random = new Random();

    private String StringRange = "0123456789";

    public void GetFour(String phone, HttpSession httpSession) {
        String key = "";
        for (int i = 0; i < 4; i++)
            key = key + key;
        httpSession.setAttribute(phone, key);
    }
}
