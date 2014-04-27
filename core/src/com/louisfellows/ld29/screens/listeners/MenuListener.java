package com.louisfellows.ld29.screens.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.louisfellows.ld29.LD29;
import com.louisfellows.ld29.ScreenFactory;
import com.louisfellows.ld29.screens.MenuScreen;

public class MenuListener extends InputListener {

    final MenuScreen parentScreen;

    public MenuListener(MenuScreen screen) {
        parentScreen = screen;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (event.getTarget().getParent() instanceof TextButton || event.getTarget() instanceof TextButton) {
            return true;
        }

        return false;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (event.getTarget().getName() != null && event.getTarget().getName().equals("start")) {
            parentScreen.setNextScreen(ScreenFactory.createBattleScreen(parentScreen.getControlChoices()));
            LD29.screenComplete();
        } else if (event.getTarget().getParent().getName() != null && event.getTarget().getParent().getName().equals("start")) {
            parentScreen.setNextScreen(ScreenFactory.createBattleScreen(parentScreen.getControlChoices()));
            LD29.screenComplete();
        }
    }

}
