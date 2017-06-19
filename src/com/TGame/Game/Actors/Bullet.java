package com.TGame.Game.Actors;

import com.TGame.Game.Helper;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Stack;

public class Bullet extends Actor {

    protected static String name = "Bullet";

    protected double speed = 1.5;
    protected double LIVE_TIME = 5000;
    protected long dieTimer = 0;
    protected int damage = 5;

    public Bullet(double angle) {
        this.angle = angle;
        this.height = 8;
        this.width= 8;
    }

    @Override
    public void Render(Graphics gContext) {
        gContext.setColor(Color.RED);
        gContext.fillOval(getX(), getY(), (int)this.height, (int)this.width);
    }

    @Override
    public void Update(long delta, HashMap<String, Actor> actors, Stack<Actor> actorsToAdd) {
        dieTimer += delta;

        if (dieTimer > LIVE_TIME) {
            Die();
            return;
        }

        Move(delta);

        // collision  with player
        Hero hero = (Hero) actors.get("Player");
        if (Helper.doAreasCollide(hero.getCollisionArea(), this.getCollisionArea())) {
            hero.TakeDamage(this.damage);
            this.Die();
        }
    }

    @Override
    public void Die() {
        this.alive = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Area getCollisionArea() {
        return new Area(new Ellipse2D.Double(x, y, width, height));
    }

    protected void Move(long delta) {
        PerformStep(angle, speed * delta);
    }

    private void PerformStep(double angle, double stepSize) {
        y += Math.sin(angle) * stepSize;
        x += Math.cos(angle) * stepSize;
    }
}
