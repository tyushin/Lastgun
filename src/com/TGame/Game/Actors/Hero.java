package com.TGame.Game.Actors;

import com.TGame.Game.KeyInputHandler;
import com.TGame.Game.Sprite;
import com.TGame.Game.Helper;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Stack;

import static com.TGame.Main.HEIGHT;
import static com.TGame.Main.WIDTH;

public class Hero extends Actor implements IUnit{

    protected static String name = "Hero";

    protected static String spriteName = "Solider.png";

    private KeyInputHandler keyInputHandler;
    private int health = 100;
    private boolean isShooting = false;
    private double shootAngle = 0;


    public Hero(KeyInputHandler keyInputHandler, String id) {
        super();
        this.id = id;
        this.height = 32;
        this.width = 32;
        this.sprite = new Sprite(spriteName);
        this.keyInputHandler = keyInputHandler;
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
        return new Area( new Rectangle2D.Double(x, y, width, height));
    }

    @Override
    public void Update(long delta, HashMap<String, Actor> actors, Stack<Actor> actorsToAdd) {
        if (keyInputHandler.isUpPressed() && this.y > 0) {
            this.y --;
        }
        if (keyInputHandler.isDownPressed() && this.y + this.height < HEIGHT) {
            this.y ++;
        }
        if (keyInputHandler.isLeftPressed() && this.x > 0) {
            this.x --;
        }
        if (keyInputHandler.isRightPressed() && this.x + this.width < WIDTH) {
            this.x ++;
        }

        if(isShooting) Shoot(actorsToAdd);

        if (this.health <= 0) {
            this.Die();
        }
    }

    @Override
    public boolean isEnemy() {
        return false;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void Die () {
        // heroes never dies!!!
    }

    @Override
    public void TakeDamage(int damage) {
        this.health -= damage;
    }

    private void Shoot(Stack<Actor> actorsToAdd) {
        Bullet bullet = new HeroBullet(shootAngle);
        Point2D centre = getCentre();
        bullet.Init(centre.getX(), centre.getY());
        actorsToAdd.push(bullet);
        isShooting = false;
    }

    public void ShootToPoint(Point2D point) {
        isShooting = true;
        shootAngle = Helper.CalculateAngle(point, this.getCentre());
    }
}
