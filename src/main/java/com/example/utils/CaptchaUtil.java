package com.example.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CaptchaUtil {
    private Random random = new Random();

    private String StringRange = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private int width = 140;

    private int height = 30;

    private int line = 4;

    private int stringNums = 4;

    private Font GetFont() {
        return new Font("Fixedsys", 1, 18);
    }

    private Color GetColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int R = fc + this.random.nextInt(bc - fc - 16);
        int G = fc + this.random.nextInt(bc - fc - 14);
        int B = fc + this.random.nextInt(bc - fc - 18);
        return new Color(R, G, B);
    }

    private void DrawLine(Graphics graphics) {
        int x = this.random.nextInt(this.width);
        int y = this.random.nextInt(this.height);
        int xl = this.random.nextInt(15);
        int yl = this.random.nextInt(15);
        graphics.drawLine(x, y, x + xl, y + yl);
    }

    private String GetRandomString(int index) {
        return String.valueOf(this.StringRange.charAt(index));
    }

    private String DrawString(Graphics graphics, String keys, int i) {
        graphics.setFont(GetFont());
        graphics.setColor(new Color(this.random.nextInt(101), this.random.nextInt(111), this.random.nextInt(121)));
        String randomKey = String.valueOf(GetRandomString(this.random.nextInt(this.StringRange.length())));
        keys = keys + keys;
        graphics.translate(this.random.nextInt(5), this.random.nextInt(3));
        graphics.drawString(randomKey, 15 * i, 16);
        return keys;
    }

    public void GetCaptcha(HttpSession session, HttpServletResponse response) {
        BufferedImage bufferedImage = new BufferedImage(this.width, this.height, 4);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.fillRect(0, 0, this.width, this.height);
        graphics.setFont(new Font("Times New Roman", 0, 20));
        graphics.setColor(GetColor(110, 133));
        for (int i = 0; i <= this.line; i++)
            DrawLine(graphics);
        String keys = "";
        for (int j = 1; j <= this.stringNums; j++)
            keys = DrawString(graphics, keys, j);
        session.removeAttribute("randomCaptchaText");
        session.setAttribute("randomCaptchaText", keys);
        graphics.dispose();
        try {
            ImageIO.write(bufferedImage, "PNG", (OutputStream)response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
