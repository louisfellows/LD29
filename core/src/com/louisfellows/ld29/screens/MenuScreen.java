package com.louisfellows.ld29.screens;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.louisfellows.ld29.LD29Screen;
import com.louisfellows.ld29.screens.listeners.MenuListener;
import com.louisfellows.ld29.util.ControllerSelection;

public class MenuScreen extends LD29Screen {

    Skin menuSkin = getAssetManager().get("skin.json");
    List<ControllerSelection> p1SelectList;
    List<ControllerSelection> p2SelectList;
    List<ControllerSelection> p3SelectList;
    List<ControllerSelection> p4SelectList;

    @Override
    public void render(float delta) {
        act();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void show() {

        addListener(new MenuListener(this));

        addActor(new Image((Texture) getAssetManager().get("titlebg.png")));

        Table mainTable = new Table();
        mainTable.setBounds(0, 0, getWidth(), getHeight() * 0.9f);

        mainTable.add(new Label("Player 1", menuSkin, "red"));
        mainTable.add(new Label("Player 2", menuSkin, "green"));
        mainTable.add(new Label("Player 3", menuSkin, "yellow"));
        mainTable.add(new Label("Player 4", menuSkin, "pink"));

        mainTable.row();

        p1SelectList = new List<ControllerSelection>(menuSkin);
        p1SelectList.setItems(getListEntries());
        mainTable.add(p1SelectList);

        p2SelectList = new List<ControllerSelection>(menuSkin);
        p2SelectList.setItems(getListEntries());
        mainTable.add(p2SelectList);

        p3SelectList = new List<ControllerSelection>(menuSkin);
        p3SelectList.setItems(getListEntries());
        mainTable.add(p3SelectList);

        p4SelectList = new List<ControllerSelection>(menuSkin);
        p4SelectList.setItems(getListEntries());
        mainTable.add(p4SelectList);

        mainTable.row().padTop(40f);

        TextButton startButton = new TextButton("Start", menuSkin);
        startButton.padLeft(30f);
        startButton.padRight(30f);
        startButton.setName("start");
        mainTable.add(startButton).colspan(4);

        addActor(mainTable);
    }

    private Array<ControllerSelection> getListEntries() {
        Array<ControllerSelection> listEntries = new Array<ControllerSelection>() {
            {
                add(ControllerSelection.NONE);
                add(ControllerSelection.KEYBOARD_A);
                add(ControllerSelection.KEYBOARD_B);
                add(ControllerSelection.KEYBOARD_C);
                add(ControllerSelection.AI);
            }
        };

        listEntries.addAll(getControllers());

        return listEntries;
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    public ControllerSelection[] getControlChoices() {
        ControllerSelection[] choices = { p1SelectList.getSelected(), p2SelectList.getSelected(), p3SelectList.getSelected(), p4SelectList.getSelected() };
        return choices;
    }

    private Array<ControllerSelection> getControllers() {
        int numberControllers = Controllers.getControllers().size;
        if (numberControllers > 4) {
            numberControllers = 4;
        }

        Array<ControllerSelection> controllers = new Array<ControllerSelection>();

        switch (numberControllers) {
        case 4:
            controllers.add(ControllerSelection.CONTROLLER_4);
            controllers.add(ControllerSelection.CONTROLLER_3);
            controllers.add(ControllerSelection.CONTROLLER_2);
            controllers.add(ControllerSelection.CONTROLLER_1);
            break;
        case 3:
            controllers.add(ControllerSelection.CONTROLLER_3);
            controllers.add(ControllerSelection.CONTROLLER_2);
            controllers.add(ControllerSelection.CONTROLLER_1);
            break;
        case 2:
            controllers.add(ControllerSelection.CONTROLLER_2);
            controllers.add(ControllerSelection.CONTROLLER_1);
            break;
        case 1:
            controllers.add(ControllerSelection.CONTROLLER_1);
            break;
        }

        return controllers;
    }

}
