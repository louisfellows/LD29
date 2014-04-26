package com.louisfellows.ld29.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.louisfellows.ld29.LD29Screen;
import com.louisfellows.ld29.entities.Entity;
import com.louisfellows.ld29.screens.listeners.ControlListener;
import com.louisfellows.ld29.screens.listeners.KeyboardListener;

public class BattleScreen extends LD29Screen {

    TiledMap map = new TiledMap();
    MapRenderer mapRenderer;
    OrthographicCamera camera;
    Array<Entity> characters = new Array<Entity>();
    Array<ControlListener> listeners = new Array<ControlListener>();

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();

        for (ControlListener l : listeners) {
            l.checkKeysAndUpdate();
        }

        getSpriteBatch().begin();

        int objectLayerId = 2;
        MapLayer collisionObjectLayer = map.getLayers().get(objectLayerId);
        MapObjects objects = collisionObjectLayer.getObjects();

        for (Entity e : characters) {
            e.update(delta);

            for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

                Rectangle rectangle = rectangleObject.getRectangle();
                if (Intersector.overlaps(rectangle, e.getBoundingRectangle())) {
                    System.out.println("Collision");
                    e.collision();
                }
            }

            e.draw(getSpriteBatch());
        }

        getSpriteBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();

    }

    @Override
    public void show() {
        map = assetManager.get("level.tmx");
        System.out.println(map.getLayers().getCount());
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        Entity sub = new Entity((Texture) assetManager.get("sub.png"));
        sub.setColor(1, 0, 0, 1);
        sub.setX(40);
        sub.setY(40);

        characters.add(sub);

        KeyboardListener l = new KeyboardListener();
        l.addListener(sub);
        listeners.add(l);
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

}
