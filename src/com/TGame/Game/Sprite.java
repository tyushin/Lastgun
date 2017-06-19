package com.TGame.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Sprite {
    private final String SPRITES_ROOT = "com/TGame/asserts/sprites/";

    private Image image; //изображение

    public Sprite(Image image) {
        this.image = image;
    }

    public Sprite(String path) {
        BufferedImage sourceImage = null;

        try {
            URL url = this.getClass().getClassLoader().getResource(SPRITES_ROOT + path);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.image = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
    }

    public int getWidth() { //получаем ширину картинки
        return image.getWidth(null);
    }

    public int getHeight() { //получаем высоту картинки
        return image.getHeight(null);
    }

    public void draw(Graphics g,int x,int y) { //рисуем картинку
        g.drawImage(image,x,y,null);
    }
}
