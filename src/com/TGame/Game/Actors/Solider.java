package com.TGame.Game.Actors;

import com.TGame.Game.Sprite;
import com.TGame.Game.Helper;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Solider extends Actor implements IUnit {

    private final String spriteName = "Enemy.png";

    private Actor target;
    private double speed = 0.1 + Math.random() * 0.1;

    private long fireTimer = 0;
    private long TIME_TO_FIRE = 500;
    protected int health = 20;


    protected static String name = "Enemy";



    public Solider() {
        super();
        sprite = new Sprite(spriteName);
        height = 32;
        width = 32;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void Render(Graphics gContext) {
        sprite.draw(gContext, (int) Math.round(x), (int) Math.round(y));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Area getCollisionArea() {
        return new Area(new Ellipse2D.Double(x, y, width, height));
    }

    @Override
    public void Update(long delta, HashMap<String, Actor> actors, Stack<Actor> actorsToAdd) {
        if (FindTarget(actors)) {
            Move(delta);
            if (IsTimeToShoot(delta)) {
                Shoot(actorsToAdd);
            }
        }
        if (health < 0) Die();
    }

    private void Shoot(Stack<Actor> actorsToAdd) {
        Bullet bullet = new Bullet(CalcAngle(target));
        Point2D centre = getCentre();
        bullet.Init(centre.getX(), centre.getY());
        actorsToAdd.push(bullet);
    }

    // todo refactor to boolean checkTimerMethod(timer, time, delta) to Commons
    private boolean IsTimeToShoot(long delta) {
        if (fireTimer > TIME_TO_FIRE) {
            fireTimer = 0;
            return true;
        }

        fireTimer += delta;
        return false;
    }

    private void Move(long delta) {

        PerformStep(CalcAngle(target), delta * speed);
    }

    private void PerformStep(double angle, double stepSize) {
        y += Math.sin(angle) * stepSize;
        x += Math.cos(angle) * stepSize;
    }

    private double CalcAngle(Actor target) {
        return Helper.CalculateAngle(target.getCentre(), this.getCentre());
    }

    private boolean FindTarget(HashMap<String, Actor> actors) {
        if (target == null){
            for (Map.Entry<String, Actor> actorEntry : actors.entrySet()) {
                if (actorEntry.getValue().getName() == "Hero") {
                    target = actorEntry.getValue();
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isEnemy() {
        return true;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void TakeDamage(int damage) {
        health -= damage;
    }
}
