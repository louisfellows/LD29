package com.louisfellows.ld29.entities;

import com.badlogic.gdx.graphics.Texture;
import com.louisfellows.ld29.util.CollisionEdge;

public class Explosion extends Entity {

    private float age = 0;
    private float ttl = 0.1f;

    public Explosion(Texture tex) {
        super(tex);
    }

    @Override
    public void collision(CollisionEdge edge) {
        // Do Nothing
    }

    @Override
    public void hit(CollisionEdge edge) {
        // Do Nothing
    }

    @Override
    public void update(float delta) {
        age += delta;
        if (age > ttl) {
            setRemove(true);
        }
        super.update(delta);
    }

    public void setTtl(float ttl) {
        this.ttl = ttl;
    }
}
