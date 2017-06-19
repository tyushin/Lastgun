package com.TGame.Game.Actors;

import com.TGame.Game.Helper;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class HeroBullet extends Bullet {

    protected int damage = 10;

    public HeroBullet(double angle) {
        super(angle);
    }

    @Override
    public void Render(Graphics gContext) {
        gContext.setColor(Color.BLUE);
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

        // collision with Enemies
        for (Map.Entry<String, Actor> entry : actors.entrySet()) {
            Actor actor = entry.getValue();
            if (actor instanceof IUnit) {
                if (Helper.doAreasCollide(actor.getCollisionArea(), this.getCollisionArea())) {
                    if (((IUnit) actor).isEnemy()) {
                        ((IUnit) actor).TakeDamage(damage);
                        this.Die();
                    }
                }
            }
        }

    }

}
