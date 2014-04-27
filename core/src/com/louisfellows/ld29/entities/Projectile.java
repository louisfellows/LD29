package com.louisfellows.ld29.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.louisfellows.ld29.screens.listeners.BattleScreenEventsListener;
import com.louisfellows.ld29.util.CollisionEdge;

public class Projectile extends Entity {

    private Sub firedBy;

    public Projectile(Texture tex) {
        super(tex);
    }

    public Projectile(Texture tex, Sub firedBy) {
        this(tex);
        this.firedBy = firedBy;
    }

    @Override
    public void collision(CollisionEdge edge) {
        setRemove(true);
        for (BattleScreenEventsListener l : listeners) {
            l.drawExplosion(new Vector2(getX(), getY()));
        }
    }

    @Override
    public void hit(CollisionEdge edge) {
        collision(edge);
    }

    public void setFiredBy(Sub firedBy) {
        this.firedBy = firedBy;
    }

    public boolean wasFiredBy(Sub firedBy) {
        return firedBy.equals(this.firedBy);
    }

}
