package com.louisfellows.ld29.entities.bonuses;

public class Effect {
    private final Stats stat;
    private final float amount;

    public Stats getStat() {
        return stat;
    }

    public float getAmount() {
        return amount;
    }

    public Effect(Stats stat, float amount) {
        super();
        this.stat = stat;
        this.amount = amount;
    }

}
