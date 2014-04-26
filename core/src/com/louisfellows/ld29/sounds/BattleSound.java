package com.louisfellows.ld29.sounds;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.louisfellows.ld29.LD29Sounds;
import com.louisfellows.ld29.entities.Sub;
import com.louisfellows.ld29.screens.listeners.BattleScreenListener;

public class BattleSound extends LD29Sounds implements BattleScreenListener {

    Sound bang = getAssetManager().get("bang.wav");
    Sound fire = getAssetManager().get("torpedo.wav");

    @Override
    public void launchTorpedo(Vector2 position, Vector2 direction) {
        fire.play();
    }

    @Override
    public void drawExplosion(Vector2 position) {
        bang.play();
    }

    @Override
    public void outOfHealth(Sub entity) {
        // TODO Auto-generated method stub

    }

}
