package com.TGame.Game.Actors;

import com.TGame.Game.Sprite;

import java.awt.*;
import java.awt.geom.Area;
import java.util.HashMap;
import java.util.Stack;

public class Barracks extends Actor{


    private long spawnDelay = 0;
    private long timeToSpawn = 3000;



    public Barracks(long spawnDelay) {
        this.spawnDelay = spawnDelay;
        this.sprite = new Sprite("Enemy.png");
        height = 64;
        width = 540;
    }

    @Override
    public void Update(long delta, HashMap<String, Actor> actors, Stack<Actor> actorsToAdd) {

        spawnDelay += delta;
        if (spawnDelay  > timeToSpawn) {
            SpawnSolider(actorsToAdd);
            spawnDelay = 0;
        }
    }

    private void SpawnSolider(Stack<Actor> actorsToAdd) {
        Solider newbee = new Solider();
        newbee.Init(getExitX(), height);
        actorsToAdd.push(newbee);
    }

    @Override
    public void Render(Graphics gContext) {

    }

    @Override
    public String getName() {
        return "Barracks";
    }

    @Override
    public Area getCollisionArea() {
        return null;
    }

    public double getExitX() {
        return Math.random() * width;
    }

    public void setTimeToSpawn(long timeToSpawn) {
        if (timeToSpawn > 500) {
            this.timeToSpawn = timeToSpawn;
        }
    }

    public double getTimeToSpawn() {
        return this.timeToSpawn;
    }
}
