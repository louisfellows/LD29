package com.louisfellows.ld29.screens;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.louisfellows.ld29.entities.Entity;
import com.louisfellows.ld29.entities.Explosion;
import com.louisfellows.ld29.entities.Projectile;
import com.louisfellows.ld29.entities.Sub;
import com.louisfellows.ld29.entities.Treasure;
import com.louisfellows.ld29.entities.bonuses.BonusFactory;
import com.louisfellows.ld29.screens.listeners.BattleScreenEventsListener;
import com.louisfellows.ld29.screens.listeners.BattleScreenRestartListener;
import com.louisfellows.ld29.screens.listeners.ControlListener;

public class BattleScreenEvents implements BattleScreenEventsListener {

    BattleScreen battleScreen;
    public static final int TORPEDO_SPEED = 150;

    public BattleScreenEvents(BattleScreen battleScreen) {
        this.battleScreen = battleScreen;
    }

    @Override
    public void launchTorpedo(Vector2 position, Vector2 direction) {
        launchTorpedo(position, direction, null);
    }

    @Override
    public void launchTorpedo(Vector2 position, Vector2 direction, Sub firedBy) {
        Entity torpedo = new Projectile((Texture) BattleScreen.getAssetManager().get("torpedo.png"), firedBy);

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

        direction.y *= TORPEDO_SPEED;
        direction.x *= TORPEDO_SPEED;

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

        Random random = new Random();
        explosion.rotate(random.nextInt(360));

        battleScreen.addCharacter(explosion);
    }

    @Override
    public void produceBonus() {
        Random r = new Random();
        int x = r.nextInt((int) (battleScreen.getWidth() - 64)) + 32;
        int y = r.nextInt((int) (battleScreen.getHeight() - 64)) + 32;

        Entity treasure = new Treasure((Texture) BattleScreen.getAssetManager().get("treasure.png"), BonusFactory.getRandomBonus());

        treasure.setX(x);
        treasure.setY(y);

        treasure.setDirection(new Vector2(0, -15));

        treasure.setRotation(15);

        battleScreen.addCharacter(treasure);
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
        BattleScreenRestartListener restartListener = new BattleScreenRestartListener(battleScreen);
        battleScreen.addListener(restartListener);
    }

    @Override
    public void bonusPickup(Treasure bonus) {
        Explosion explosion = new Explosion(bonus.getBonus().getPowerupImage());

        explosion.setX(bonus.getX() + (bonus.getWidth() / 2));
        explosion.setY(bonus.getY() + (bonus.getHeight() / 2));

        explosion.setDirection(new Vector2(0, 5f));

        explosion.setTtl(4f);

        battleScreen.addCharacter(explosion);
    }
}
