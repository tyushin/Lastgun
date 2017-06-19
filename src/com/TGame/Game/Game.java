package com.TGame.Game;

import com.TGame.Game.Actors.Actor;
import com.TGame.Game.Actors.Barracks;
import com.TGame.Game.Actors.Hero;
import com.TGame.Game.Actors.Solider;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private Barracks barracks;

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
            update(delta);
            render();
        }
    }

    public void start () {
        running = true;
        new Thread(this).start();
    }

    private void init() {
        addKeyListener(keyInputHandler);

        /*Actor actor = new Solider();
        actor.Init(50, 20);
        actors.put(actor.getId(), actor);

        actor = new Solider();
        actor.Init(150, 20);
        actors.put(actor.getId(), actor);

        actor = new Solider();
        actor.Init(250, 20);
        actors.put(actor.getId(), actor);

        actor = new Solider();
        actor.Init(350, 20);
        actors.put(actor.getId(), actor);

        actor = new Solider();
        actor.Init(450, 20);
        actors.put(actor.getId(), actor);*/

        barracks = new Barracks(10000);
        barracks.Init(0,0);
        actors.put("Barracks", barracks);

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

        for(Iterator<Map.Entry<String, Actor>> it = actors.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Actor> entry = it.next();
            if(!entry.getValue().getAlive()) {
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

}
