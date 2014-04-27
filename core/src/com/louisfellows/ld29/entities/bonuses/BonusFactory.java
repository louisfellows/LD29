package com.louisfellows.ld29.entities.bonuses;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.louisfellows.ld29.LD29Screen;

public class BonusFactory {

    private static EffectTable[] effects = { new EffectTable(Stats.HEALTH, Stats.HEALTH, +10, +0, true, 1f, "healthup.png"),
            new EffectTable(Stats.HEALTH, Stats.HEALTH, -5, +0, false, 1f, "healthdown.png"),
            new EffectTable(Stats.HEALTH, Stats.HEALTH, +15, +0, true, 1f, "healthup.png"),
            new EffectTable(Stats.ACCELERATION, Stats.ACCELERATION, +10, -10, true, 8f, "accup.png"),
            new EffectTable(Stats.ACCELERATION, Stats.ACCELERATION, -10, +10, false, 8f, "accdown.png"),
            new EffectTable(Stats.MAX_SPEED, Stats.MAX_SPEED, +100, -100, true, 8f, "speedup.png"),
            new EffectTable(Stats.MAX_SPEED, Stats.MAX_SPEED, -20, +20, false, 8f, "speeddown.png"),
            new EffectTable(Stats.FIRING_SPEED, Stats.FIRING_SPEED, -0.2f, +0.2f, true, 8f, "reloadup.png"),
            new EffectTable(Stats.FIRING_SPEED, Stats.FIRING_SPEED, +1, -1, false, 8f, "reloaddown.png") };

    public static Bonus getRandomBonus() {
        Random random = new Random();
        int i = random.nextInt(effects.length);

        Effect pickup = new Effect(effects[i].pickupStat, effects[i].pickupAmount);
        Effect expire = new Effect(effects[i].expireStat, effects[i].expireAmount);

        Texture t = LD29Screen.getAssetManager().get(effects[i].image);

        Bonus b = new Bonus(effects[i].time, effects[i].positive, pickup, expire, t);

        return b;
    }

    private static class EffectTable {
        public final Stats pickupStat;
        public final Stats expireStat;
        public final float pickupAmount;
        public final float expireAmount;
        public final boolean positive;
        public final float time;
        public final String image;

        public EffectTable(Stats pickupStat, Stats expireStat, float pickupAmount, float expireAmount, boolean positive, float time, String image) {
            super();
            this.pickupStat = pickupStat;
            this.expireStat = expireStat;
            this.pickupAmount = pickupAmount;
            this.expireAmount = expireAmount;
            this.positive = positive;
            this.time = time;
            this.image = image;
        }
    }
}
