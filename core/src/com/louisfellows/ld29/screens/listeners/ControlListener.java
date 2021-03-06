package com.louisfellows.ld29.screens.listeners;

public interface ControlListener {

    public abstract void checkKeysAndUpdate(float delta);

    public abstract void addListener(SubActionListener listener);

    public abstract void removeListener(SubActionListener listener);

    public abstract void removeAllListeners();

}