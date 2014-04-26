package com.louisfellows.ld29.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.louisfellows.ld29.screens.listeners.BattleScreenListener;
import com.louisfellows.ld29.util.CollisionEdge;

public class Projectile extends Entity {

    public Projectile(Texture tex) {
        super(tex);
    }

    @Override
    public void collision(CollisionEdge edge) {
        setRemove(true);
        for (BattleScreenListener l : listeners) {
            l.drawExplosion(new Vector2(getX(), getY()));
        }
    }

    @Override
    public void hit(CollisionEdge edge) {
        collision(edge);
    }

}
