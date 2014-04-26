package com.louisfellows.ld29.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.louisfellows.ld29.screens.listeners.EntityActionListener;

public class Entity extends Sprite implements EntityActionListener {
    Vector2 direction = new Vector2();

    public Entity(Texture tex) {
        super(tex);
        direction.set(10, 10);
    }

    public void update(float delta) {
        setX(getX() + (direction.x * delta));
        setY(getY() + (direction.y * delta));
    }

    public void collision() {
        direction.x = -(direction.x * 0.75f);
        direction.y = -(direction.y * 0.75f);
    }

    public void collision(RectangleMapObject rectangleObject) {
        getBoundingRectangle().
    }

    @Override
    public void alterXDirection(float influence) {
        direction.x += influence;
        if (direction.x > 50) {
            direction.x = 50;
        }
        if (direction.x < -50) {
            direction.x = -50;
        }

        if ((influence < 0 && isFlipX()) || (influence > 0 && !isFlipX())) {
            flip(true, false);
        }
    }

    @Override
    public void alterYDirection(float influence) {
        direction.y += influence;
        if (direction.y > 50) {
            direction.y = 50;
        }
        if (direction.y < -50) {
            direction.y = -50;
        }
    }

    @Override
    public void fire(Vector2 direction) {

    }

}
