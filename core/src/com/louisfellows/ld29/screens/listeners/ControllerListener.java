package com.louisfellows.ld29.screens.listeners;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ControllerListener implements ControlListener {
    private static final int MOVEMENT_INFLUENCE = 1;
    private final Array<SubActionListener> listeners = new Array<SubActionListener>();

    private final Controller controller;

    public ControllerListener() {
        controller = Controllers.getControllers().get(0);
    }

    public ControllerListener(int controllerIndex) {
        controller = Controllers.getControllers().get(controllerIndex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.louisfellows.ld29.screens.listeners.ControlListener#checkKeys()
     */
    @Override
    public void checkKeysAndUpdate(float delta) {

        float x = 0;
        float y = 0;

        float launchX = 0;
        float launchY = 0;

        x = controller.getAxis(1) * MOVEMENT_INFLUENCE;
        y = controller.getAxis(0) * MOVEMENT_INFLUENCE;

        if (controller.getAxis(3) > 0.7) {
            launchX = 1;
        } else if (controller.getAxis(3) < -0.7) {
            launchX = -1;
        }

        if (controller.getAxis(2) > 0.7) {
            launchY = 1;
        } else if (controller.getAxis(2) < -0.7) {
            launchY = -1;
        }

        updateListeners(x, -y, launchX, -launchY);
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
