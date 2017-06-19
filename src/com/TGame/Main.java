package com.TGame;

import com.TGame.Game.Game;

import javax.swing.*;
import java.awt.*;

public class Main{


    public static int WIDTH = 540;
    public static int HEIGHT = 960;
    public static String NAME = "Last Gun alpha 0.0.1";


    public static void main(String[] args) {
        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JFrame frame = new JFrame(Main.NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //выход из приложения по нажатию клавиши ESC
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER); //добавляем холст на наш фрейм
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        game.start();
    }


}
