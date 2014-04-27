package com.louisfellows.ld29.entities.bonuses;

import com.badlogic.gdx.graphics.Texture;

public class Bonus {

    protected float expireTime = 5f;
    protected boolean expired = false;
    protected boolean positive = true;
    protected final Effect pickup;
    protected final Effect expire;
    protected final Texture tex;

    public Bonus(float expireTime, boolean positive, Effect pickup, Effect expire, Texture tex) {
        super();
        this.expireTime = expireTime;
        this.positive = positive;
        this.pickup = pickup;
        this.expire = expire;
        this.tex = tex;
    }

    public Effect onPickup() {
        return pickup;
    }

    public Effect onExpire() {
        return expire;
    }

    public void update(float delta) {
        expireTime -= delta;
        if (expireTime < 0) {
            expired = true;
        }
    }

    public boolean isExpired() {
        return expired;
    }

    public float getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(float expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public Texture getPowerupImage() {
        return tex;
    }
}
