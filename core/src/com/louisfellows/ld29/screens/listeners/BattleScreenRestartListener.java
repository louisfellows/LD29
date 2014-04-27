package com.louisfellows.ld29.screens.listeners;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.louisfellows.ld29.LD29;
import com.louisfellows.ld29.ScreenFactory;
import com.louisfellows.ld29.screens.BattleScreen;

public class BattleScreenRestartListener extends InputListener {

    BattleScreen battleScreen;

    public BattleScreenRestartListener(BattleScreen battleScreen) {
        super();
        this.battleScreen = battleScreen;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if (keycode == Keys.ENTER || keycode == Keys.BACKSPACE || keycode == Keys.BUTTON_A || keycode == Keys.BUTTON_B) {
            return true;
        }
        return super.keyDown(event, keycode);
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {

        if (keycode == Keys.ENTER || keycode == Keys.BUTTON_A) {
            battleScreen.setNextScreen(ScreenFactory.createBattleScreen(battleScreen.getControllers()));
            LD29.screenComplete();
        } else if (keycode == Keys.BACKSPACE || keycode == Keys.BUTTON_B) {
            battleScreen.setNextScreen(ScreenFactory.createMenuScreen());
            LD29.screenComplete();
        }

        return super.keyUp(event, keycode);
    }

}
