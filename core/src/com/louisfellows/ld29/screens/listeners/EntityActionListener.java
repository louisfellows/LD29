package com.louisfellows.ld29.screens.listeners;

import com.badlogic.gdx.math.Vector2;

public interface EntityActionListener {

    public void alterXDirection(float influence);

    public void alterYDirection(float influence);

    public void fire(Vector2 direction);
}
