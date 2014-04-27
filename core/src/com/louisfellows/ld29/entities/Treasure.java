package com.louisfellows.ld29.entities;

import com.badlogic.gdx.graphics.Texture;
import com.louisfellows.ld29.entities.bonuses.Bonus;
import com.louisfellows.ld29.screens.listeners.BattleScreenEventsListener;
import com.louisfellows.ld29.util.CollisionEdge;

public class Treasure extends Entity {

    private final Bonus bonus;

    public Treasure(Texture tex, Bonus bonus) {
        super(tex);
        this.bonus = bonus;
    }

    @Override
    public void collision(CollisionEdge edge) {
        direction.x = 0;
        direction.y = 0;
        setRotation(0);
    }

    @Override
    public void hit(CollisionEdge edge) {
        setRemove(true);
        for (BattleScreenEventsListener l : listeners) {
            l.bonusPickup(this);
        }
    }

    public Bonus getBonus() {
        return bonus;
    }

}
