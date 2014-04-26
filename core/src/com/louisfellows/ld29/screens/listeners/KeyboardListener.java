package com.louisfellows.ld29.screens.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;

public class KeyboardListener extends InputListener implements ControlListener {
    private final Array<EntityActionListener> listeners = new Array<EntityActionListener>();

    /*
     * (non-Javadoc)
     * 
     * @see com.louisfellows.ld29.screens.listeners.ControlListener#checkKeys()
     */
    @Override
    public void checkKeysAndUpdate() {
        boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        float x = 0;
        float y = 0;

        if (left && !right) {
            x = -1;
        } else if (!left && right) {
            x = 1;
        }

        if (up && !down) {
            y = 1;
        } else if (!up && down) {
            y = -1;
        }

        updateListeners(x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.louisfellows.ld29.screens.listeners.ControlListener#addListener(com
     * .louisfellows.ld29.screens.listeners.EntityActionListener)
     */
    @Override
    public void addListener(EntityActionListener listener) {
        listeners.add(listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.louisfellows.ld29.screens.listeners.ControlListener#removeListener
     * (com.louisfellows.ld29.screens.listeners.EntityActionListener)
     */
    @Override
    public void removeListener(EntityActionListener listener) {
        listeners.removeValue(listener, true);
    }

    private void updateListeners(float x, float y) {
        for (EntityActionListener l : listeners) {
            l.alterXDirection(x);
            l.alterYDirection(y);
        }
    }

}
