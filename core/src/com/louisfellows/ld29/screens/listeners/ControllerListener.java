package com.louisfellows.ld29.screens.listeners;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ControllerListener implements ControlListener {
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
    public void checkKeysAndUpdate() {

        float x = 0;
        float y = 0;

        float launchX = 0;
        float launchY = 0;

        x = controller.getAxis(1) * 3;
        y = controller.getAxis(0) * 3;

        if (controller.getAxis(3) > 0.7 || controller.getAxis(3) < -0.7)
            launchX = controller.getAxis(3) * 3;

        if (controller.getAxis(2) > 0.7 || controller.getAxis(2) < -0.7)
            launchY = controller.getAxis(2) * 3;

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
