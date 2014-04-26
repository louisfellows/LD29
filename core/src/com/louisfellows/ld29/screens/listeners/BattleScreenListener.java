package com.louisfellows.ld29.screens.listeners;

import com.badlogic.gdx.math.Vector2;
import com.louisfellows.ld29.entities.Sub;

public interface BattleScreenListener {
    void launchTorpedo(Vector2 position, Vector2 direction);

    void drawExplosion(Vector2 position);

    void outOfHealth(Sub entity);
}
