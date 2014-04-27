package com.louisfellows.ld29;

import com.louisfellows.ld29.screens.BattleScreen;
import com.louisfellows.ld29.screens.MenuScreen;
import com.louisfellows.ld29.util.ControllerSelection;

public class ScreenFactory {
    public static LD29Screen createBattleScreen(ControllerSelection[] controls) {
        BattleScreen battleScreen = new BattleScreen();
        battleScreen.setupPlayers(controls);
        return battleScreen;
    }

    public static LD29Screen createMenuScreen() {
        return new MenuScreen();
    }
}
