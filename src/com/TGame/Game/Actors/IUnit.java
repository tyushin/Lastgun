package com.TGame.Game.Actors;


public interface IUnit {

    public boolean isEnemy();

    public int getHealth();

    public void TakeDamage(int damage);
}
