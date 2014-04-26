package com.louisfellows.ld29.screens.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;

public class KeyboardListener extends InputListener implements ControlListener {
    private static final int MOVEMENT_INFLUENCE = 5;
    private final Array<SubActionListener> listeners = new Array<SubActionListener>();

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
            x = -MOVEMENT_INFLUENCE;
        } else if (!left && right) {
            x = MOVEMENT_INFLUENCE;
        }

        if (up && !down) {
            y = MOVEMENT_INFLUENCE;
        } else if (!up && down) {
            y = -MOVEMENT_INFLUENCE;
        }

        left = Gdx.input.isKeyPressed(Input.Keys.A);
        right = Gdx.input.isKeyPressed(Input.Keys.D);
        up = Gdx.input.isKeyPressed(Input.Keys.W);
        down = Gdx.input.isKeyPressed(Input.Keys.S);

        float launchX = 0;
        float launchY = 0;

        if (left && !right) {
            launchX = -1;
        } else if (!left && right) {
            launchX = 1;
        }

        if (up && !down) {
            launchY = 1;
        } else if (!up && down) {
            launchY = -1;
        }

        updateListeners(x, y, launchX, launchY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.louisfellows.ld29.screens.listeners.ControlListener#addListener(com
     * .louisfellows.ld29.screens.listeners.EntityActionListener)
     */
    @Override
    public void addListener(SubActionListener listener) {
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
    public void removeListener(SubActionListener listener) {
        listeners.removeValue(listener, true);
    }

    private void updateListeners(float x, float y, float launchX, float launchY) {
        for (SubActionListener l : listeners) {
            l.alterXDirection(x);
            l.alterYDirection(y);
            if (launchX != 0 || launchY != 0) {
                l.fire(new Vector2(launchX, launchY));
            }
        }
    }

    @Override
    public void removeAllListeners() {
        listeners.clear();
    }
}
