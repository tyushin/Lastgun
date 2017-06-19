package com.TGame.Game.Actors;

import com.TGame.Game.Sprite;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Stack;

public abstract class Actor {

    protected double x, y, height, width;

    protected Boolean alive;

    protected double angle = 0;

    protected String id;

    protected Sprite sprite;

    public Actor() {
        id = GenerateId();
    }

    public void Init(double x, double y) {
        alive = true;
        this.x = x;
        this.y = y;
    }


    private String GenerateId() {
        return this.getName() + "_" + this.hashCode();
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public Point2D.Double getCentre() {
        return new Point2D.Double(x + width / 2, y + height / 2);
    }

    public String getId() {
        return id;
    }

    public boolean getAlive() { return alive; }

    public void Die() {
        this.alive = false;
    }

    public abstract void Update(long delta, HashMap<String, Actor> actors, Stack<Actor> actorsToAdd);

    public abstract void Render(Graphics gContext);

    public abstract String getName();

    public abstract Area getCollisionArea();

}
