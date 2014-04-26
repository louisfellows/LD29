package com.louisfellows.ld29.screens.listeners;

public interface ControlListener {

    public abstract void checkKeysAndUpdate();

    public abstract void addListener(EntityActionListener listener);

    public abstract void removeListener(EntityActionListener listener);

}