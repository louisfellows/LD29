package com.louisfellows.ld29.entities;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.louisfellows.ld29.screens.listeners.BattleScreenEventsListener;
import com.louisfellows.ld29.screens.listeners.SubActionListener;
import com.louisfellows.ld29.util.CollisionEdge;

public class Sub extends Entity implements SubActionListener {

    private static final float reloadTime = 1;
    private float loadTime = 0;
    private int maxSpeed = 60;
    private int health = 64;
    private final Sprite healthBar;
    private final Sprite reloadBar;
    private boolean defeated;

    public Sub(Texture tex, Texture healthBar) {
        super(tex);
        this.healthBar = new Sprite(healthBar);
        this.reloadBar = new Sprite(healthBar);
    }

    @Override
    public void collision(CollisionEdge edge) {

        if (!defeated) {
            switch (edge) {
            case LEFT:
            case RIGHT:
                direction.x = -(direction.x * 0.75f);
                direction.y = direction.y * 0.9f;
                break;
            case TOP:
            case BOTTOM:
                direction.x = direction.x * 0.9f;
                direction.y = -(direction.y * 0.75f);
                break;
            }
        }
    }

    @Override
    public void hit(CollisionEdge edge) {

        if (!defeated) {
            switch (edge) {
            case LEFT:
                direction.x = (maxSpeed * 0.75f);
                break;
            case RIGHT:
                direction.x = -(maxSpeed * 0.75f);
                break;
            case TOP:
                direction.y = -(maxSpeed * 0.75f);
                break;
            case BOTTOM:
                direction.y = (maxSpeed * 0.75f);
                break;
            }
            reduceHealth(10);
        }
    }

    private void reduceHealth(int reduction) {
        health -= reduction;
        if (health < 0) {
            health = 0;
            if (!defeated) {
                for (BattleScreenEventsListener l : listeners) {
                    l.outOfHealth(this);
                }
                rotate(45);
                direction.x = 0;
                direction.y = -30;
            }
            defeated = true;
        }
    }

    @Override
    public void draw(Batch batch) {
        healthBar.setColor(getColor());
        healthBar.setBounds(getX(), getY() + getHeight() + 5, health, healthBar.getHeight());
        healthBar.draw(batch);

        reloadBar.setColor(new Color(1, 1, 1, 1));
        reloadBar.setBounds(getX(), getY() + getHeight(), 64 - (loadTime * (64 / reloadTime)), 5);
        reloadBar.draw(batch);

        if (defeated) {
            if (getY() < -35) {
                direction.y = 0;
                setRemove(true);
            } else {
                generateExplosions();
            }
        }

        super.draw(batch);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        loadTime -= delta;
        if (loadTime < 0) {
            loadTime = 0;
        }
    }

    @Override
    public void alterXDirection(float influence) {
        direction.x += influence;
        if (direction.x > maxSpeed) {
            direction.x = maxSpeed;
        }
        if (direction.x < -maxSpeed) {
            direction.x = -maxSpeed;
        }

        if ((influence < 0 && isFlipX()) || (influence > 0 && !isFlipX())) {
            flip(true, false);
        }
    }

    @Override
    public void alterYDirection(float influence) {
        direction.y += influence;
        if (direction.y > maxSpeed) {
            direction.y = maxSpeed;
        }
        if (direction.y < -maxSpeed) {
            direction.y = -maxSpeed;
        }
    }

    @Override
    public void fire(Vector2 direction) {
        if (loadTime == 0) {
            float x = (float) (getX() + (0.5 * getWidth()));
            float y = (float) (getY() + (0.5 * getHeight()));

            Vector2 position = new Vector2(x, y);

            for (BattleScreenEventsListener l : listeners) {
                l.launchTorpedo(position, direction);
            }
            loadTime = reloadTime;
        }
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    private void generateExplosions() {
        for (BattleScreenEventsListener l : listeners) {

            Random random = new Random();
            float x = getX() + random.nextInt((int) getWidth());
            float y = getY() + random.nextInt((int) getHeight());

            l.drawExplosion(new Vector2(x, y));
        }
    }

    public boolean isDefeated() {
        return defeated;
    }

}
