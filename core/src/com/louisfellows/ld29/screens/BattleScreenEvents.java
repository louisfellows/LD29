package com.louisfellows.ld29.screens;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.louisfellows.ld29.entities.Entity;
import com.louisfellows.ld29.entities.Explosion;
import com.louisfellows.ld29.entities.Projectile;
import com.louisfellows.ld29.entities.Sub;
import com.louisfellows.ld29.screens.listeners.BattleScreenEventsListener;
import com.louisfellows.ld29.screens.listeners.ControlListener;

public class BattleScreenEvents implements BattleScreenEventsListener {

    BattleScreen battleScreen;

    public BattleScreenEvents(BattleScreen battleScreen) {
        this.battleScreen = battleScreen;
    }

    @Override
    public void launchTorpedo(Vector2 position, Vector2 direction) {
        Entity torpedo = new Projectile((Texture) BattleScreen.getAssetManager().get("torpedo.png"));

        torpedo.setX(position.x + direction.x);
        torpedo.setY(position.y + direction.y);

        if (direction.x < 0) {
            direction.x = -1;
        } else if (direction.x > 0) {
            direction.x = 1;
        }
        if (direction.y < 0) {
            direction.y = -1;
        } else if (direction.y > 0) {
            direction.y = 1;
        }

        direction.y *= 75;
        direction.x *= 75;

        torpedo.setDirection(direction);

        double angle = Math.atan2(-direction.x, direction.y) * 180 / Math.PI;

        torpedo.rotate((float) angle - 90f);

        battleScreen.addCharacter(torpedo);
    }

    @Override
    public void drawExplosion(Vector2 position) {
        Entity explosion = new Explosion((Texture) BattleScreen.getAssetManager().get("explosion.png"));

        explosion.setX(position.x);
        explosion.setY(position.y);

        explosion.setDirection(new Vector2());

        explosion.addListener(this);

        Random random = new Random();
        explosion.rotate(random.nextInt(360));

        battleScreen.addCharacter(explosion);
    }

    @Override
    public void outOfHealth(Sub entity) {
        battleScreen.matchComplete = true;
        for (ControlListener l : battleScreen.listeners) {
            l.removeListener(entity);
        }
        battleScreen.players.removeValue(entity, true);

        if (battleScreen.players.size == 1) {
            displayVictoryScreen(battleScreen.players.get(0));
        }
    }

    @Override
    public void displayVictoryScreen(Sub victor) {
        Sprite victorySprite = new Sprite((Texture) BattleScreen.getAssetManager().get("victory.png"));
        victorySprite.setColor(victor.getColor());
        victorySprite.setX((battleScreen.getWidth() - victorySprite.getWidth()) / 2);
        victorySprite.setY((battleScreen.getHeight() - victorySprite.getHeight()) / 2);
        battleScreen.nonGameSprites.add(victorySprite);
    }

}
