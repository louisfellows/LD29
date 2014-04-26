package com.louisfellows.ld29;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.louisfellows.ld29.screens.BattleScreen;

public class LD29 extends ApplicationAdapter {
    LD29Screen currentScreen;
    static boolean changeScreen = false;

    @Override
    public void create() {
        LD29Screen.loadAssets();
        LD29Sounds.loadAssets();

        currentScreen = new BattleScreen();
        currentScreen.show();

        Gdx.input.setInputProcessor(currentScreen);
    }

    @Override
    public void render() {
        if (changeScreen) {
            currentScreen.dispose();
            // currentScreen = //New Screen;
            changeScreen = false;
        }
        currentScreen.render(Gdx.graphics.getDeltaTime());
    }

    public static void screenComplete() {
        changeScreen = true;
    }
}
