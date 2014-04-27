package com.louisfellows.ld29.screens.listeners;

import com.badlogic.gdx.math.Vector2;
import com.louisfellows.ld29.entities.Sub;
import com.louisfellows.ld29.entities.Treasure;

public interface BattleScreenEventsListener {
    void launchTorpedo(Vector2 position, Vector2 direction, Sub firedBy);

    void launchTorpedo(Vector2 position, Vector2 direction);

    void drawExplosion(Vector2 position);

    void outOfHealth(Sub entity);

    void displayVictoryScreen(Sub victor);

    void bonusPickup(Treasure bonus);

    void produceBonus();

}
