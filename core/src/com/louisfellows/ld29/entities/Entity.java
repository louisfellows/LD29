package com.louisfellows.ld29.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.louisfellows.ld29.screens.listeners.BattleScreenListener;
import com.louisfellows.ld29.util.CollisionEdge;

public abstract class Entity extends Sprite {
    Vector2 direction = new Vector2();
    protected boolean remove = false;
    protected final Array<BattleScreenListener> listeners = new Array<BattleScreenListener>();

    public Entity(Texture tex) {
        super(tex);
        direction.set(0, 0);
    }

    public abstract void collision(CollisionEdge edge);

    public abstract void hit(CollisionEdge edgeE);

    public void update(float delta) {
        setX(getX() + (direction.x * delta));
        setY(getY() + (direction.y * delta));
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void addListener(BattleScreenListener listener) {
        listeners.add(listener);
    }

    public void removeListener(BattleScreenListener listener) {
        listeners.removeValue(listener, true);
    }

}
