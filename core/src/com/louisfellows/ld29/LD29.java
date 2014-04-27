package com.louisfellows.ld29;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.louisfellows.ld29.screens.MenuScreen;

public class LD29 extends ApplicationAdapter {
    LD29Screen currentScreen;
    static boolean changeScreen = false;

    @Override
    public void create() {
        LD29Screen.loadAssets();
        LD29Sounds.loadAssets();

        currentScreen = new MenuScreen();
        currentScreen.show();

        Gdx.input.setInputProcessor(currentScreen);
    }

    @Override
    public void render() {
        if (changeScreen) {
            currentScreen.dispose();
            currentScreen = currentScreen.getNextScreen();
            changeScreen = false;
            currentScreen.show();
            Gdx.input.setInputProcessor(currentScreen);
        }
        currentScreen.render(Gdx.graphics.getDeltaTime());
    }

    public static void screenComplete() {
        changeScreen = true;
    }
}
