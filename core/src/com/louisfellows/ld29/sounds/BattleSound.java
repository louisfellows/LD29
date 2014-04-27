package com.louisfellows.ld29.sounds;

import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.louisfellows.ld29.LD29Sounds;
import com.louisfellows.ld29.entities.Sub;
import com.louisfellows.ld29.entities.Treasure;
import com.louisfellows.ld29.screens.listeners.BattleScreenEventsListener;

public class BattleSound extends LD29Sounds implements BattleScreenEventsListener {

    Sound bang = getAssetManager().get("bang.wav");
    Sound fire = getAssetManager().get("torpedo.wav");
    Sound powerup = getAssetManager().get("powerup.wav");
    Sound powerdown = getAssetManager().get("powerdown.wav");
    Sound victory = getAssetManager().get("victory.wav");

    @Override
    public void launchTorpedo(Vector2 position, Vector2 direction) {
        launchTorpedo(position, direction, null);
    }

    @Override
    public void launchTorpedo(Vector2 position, Vector2 direction, Sub firedBy) {
        Random random = new Random();
        long id = fire.play();
        fire.setPitch(id, (float) ((random.nextInt(1) / 10) + 0.5));
    }

    @Override
    public void drawExplosion(Vector2 position) {
        bang.play();
    }

    @Override
    public void outOfHealth(Sub entity) {

    }

    @Override
    public void displayVictoryScreen(Sub victor) {
        long id = victory.play();
        victory.setPriority(id, 100);
    }

    @Override
    public void bonusPickup(Treasure bonus) {
        if (bonus.getBonus().isPositive()) {
            powerup.play();
        } else {
            powerdown.play();
        }
    }

    @Override
    public void produceBonus() {
        // TODO Auto-generated method stub

    }

}
