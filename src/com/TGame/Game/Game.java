package com.TGame.Game;

import com.TGame.Game.Actors.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class Game extends Canvas implements Runnable {

    private Boolean running;
    private KeyInputHandler keyInputHandler;
    private HashMap<String, Actor> actors;
    private Stack<Actor> actorsToAdd;
    private  int killCounter = 0;

    public Game () {
        actorsToAdd = new Stack<Actor>();
        actors = new HashMap<String, Actor>();
    }

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        long delta;
        keyInputHandler = new KeyInputHandler();

        init();

        while(running) {
            delta = (System.currentTimeMillis() - lastTime);
            lastTime = System.currentTimeMillis();
            render();
            update(delta);
        }
    }

    public void start () {
        running = true;
        new Thread(this).start();
    }

    private void init() {
        addKeyListener(keyInputHandler);
        Actor actor = new Solider();
        actor.Init(50, 20);
        actors.put(actor.getId(), actor);
        killCounter = 0;

        actorsToAdd.clear();
        actors.clear();

        Barracks barracks = new Barracks(10000);
        barracks.Init(0,0);
        actors.put(barracks.getId(), barracks);

        Hero hero = new Hero(keyInputHandler, "Player");
        hero.Init(300, 700);
        actors.put(hero.getId(), hero);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (running) {
                    hero.ShootToPoint(e.getPoint());
                }
            }
        });
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2); //создаем BufferStrategy для нашего холста
            requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics(); //получаем Graphics из созданной нами BufferStrategy
        g.setColor(Color.black); //выбрать цвет
        g.fillRect(0, 0, getWidth(), getHeight()); //заполнить прямоугольник

        for(Map.Entry<String, Actor> actorEntry : actors.entrySet()) {
            actorEntry.getValue().Render(g);
        }

        g.dispose();
        bs.show();
    }

    private void update(long delta) {
        if( ((IUnit)actors.get("Player")).getHealth() > 80) {
            for (Iterator<Map.Entry<String, Actor>> it = actors.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Actor> entry = it.next();
                if (!entry.getValue().getAlive()) {
                    if (entry.getValue() instanceof Solider) {
                        killCounter ++;
                    }
                    it.remove();
                    break;
                }
                entry.getValue().Update(delta, actors, actorsToAdd);
            }

            while (!actorsToAdd.isEmpty()) {
                Actor actor = actorsToAdd.pop();
                actors.put(actor.getId(), actor);
            }
        }
        else {
            GameOver();
        }
    }

    private void GameOver() {
        running = false;
        DrawScore();
    }

    private void DrawScore() {

        Graphics g = getGraphics(); //получаем Graphics из созданной нами BufferStrategy
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("Arial", Font.BOLD, 72));
        g2.drawString("Game Over",70,350);
        g2.setFont(new Font("Arial", Font.BOLD, 72));
        g2.setFont(new Font("Arial", Font.PLAIN, 45));
        g2.drawString("You killed " + killCounter + " soliders",70,450);

    }

}
