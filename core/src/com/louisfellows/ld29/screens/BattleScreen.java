package com.louisfellows.ld29.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.louisfellows.ld29.entities.Explosion;
import com.louisfellows.ld29.entities.Sub;
import com.louisfellows.ld29.screens.listeners.BattleScreenEventsListener;
import com.louisfellows.ld29.screens.listeners.ControlListener;
import com.louisfellows.ld29.screens.listeners.ControllerListener;
import com.louisfellows.ld29.screens.listeners.KeyboardListener;
import com.louisfellows.ld29.sounds.BattleSound;
import com.louisfellows.ld29.util.CollisionEdge;

public class BattleScreen extends LD29Screen {

    TiledMap map = new TiledMap();
    MapRenderer mapRenderer;
    OrthographicCamera camera;
    Array<Entity> characters = new Array<Entity>();
    Array<Sub> players = new Array<Sub>();
    Array<ControlListener> listeners = new Array<ControlListener>();
    Array<Sprite> nonGameSprites = new Array<Sprite>();
    boolean matchComplete = false;
    BattleScreenEventsListener screenSounds = new BattleSound();
    BattleScreenEventsListener screenEvents = new BattleScreenEvents(this);

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

        actorUpdate(delta);

        for (Sprite s : nonGameSprites) {
            s.draw(getSpriteBatch());
        }

        getSpriteBatch().end();
    }

    private void actorUpdate(float delta) {
        int objectLayerId = 2;
        MapLayer collisionObjectLayer = map.getLayers().get(objectLayerId);
        MapObjects objects = collisionObjectLayer.getObjects();

        for (Entity e : characters) {

            e.update(delta);

            collisionDetection(objects, e);

            if (e.isRemove()) {
                characters.removeValue(e, true);
            }

            e.draw(getSpriteBatch());
        }
    }

    private void collisionDetection(MapObjects objects, Entity e) {
        if (!(e instanceof Explosion)) {

            for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

                Rectangle entityRectangle = e.getBoundingRectangle();
                Rectangle rectangle = rectangleObject.getRectangle();
                if (Intersector.overlaps(rectangle, entityRectangle)) {
                    Rectangle intersection = new Rectangle();
                    Intersector.intersectRectangles(entityRectangle, rectangle, intersection);

                    CollisionEdge edge = CollisionEdge.TOP;

                    if (intersection.x > entityRectangle.x) {
                        edge = CollisionEdge.RIGHT;
                    } else if (intersection.y > entityRectangle.y) {
                        edge = CollisionEdge.TOP;
                    } else if (intersection.x + intersection.width < entityRectangle.x + entityRectangle.width) {
                        edge = CollisionEdge.LEFT;
                    } else if (intersection.y + intersection.height < entityRectangle.y + entityRectangle.height) {
                        edge = CollisionEdge.BOTTOM;
                    }
                    e.collision(edge);
                }
            }

            for (int i = 0; i < characters.size; i++) {
                Entity e2 = characters.get(i);
                if (!(e2 instanceof Explosion)) {
                    if (!e.equals(e2)) {
                        if (Intersector.overlaps(e.getBoundingRectangle(), e2.getBoundingRectangle())) {

                            Rectangle intersection = new Rectangle();
                            Intersector.intersectRectangles(e.getBoundingRectangle(), e2.getBoundingRectangle(), intersection);

                            CollisionEdge edgeE = CollisionEdge.TOP;
                            ;
                            CollisionEdge edgeE2 = CollisionEdge.TOP;
                            ;

                            if (intersection.x > e.getBoundingRectangle().x) {
                                edgeE = CollisionEdge.RIGHT;
                                edgeE2 = CollisionEdge.LEFT;
                            } else if (intersection.y > e.getBoundingRectangle().y) {
                                edgeE = CollisionEdge.TOP;
                                edgeE2 = CollisionEdge.BOTTOM;
                            } else if (intersection.x + intersection.width < e.getBoundingRectangle().x + e.getBoundingRectangle().width) {
                                edgeE = CollisionEdge.LEFT;
                                edgeE2 = CollisionEdge.RIGHT;
                            } else if (intersection.y + intersection.height < e.getBoundingRectangle().y + e.getBoundingRectangle().height) {
                                edgeE = CollisionEdge.BOTTOM;
                                edgeE2 = CollisionEdge.TOP;
                            }

                            e.hit(edgeE);
                            e2.hit(edgeE2);
                        }
                    }
                }
            }

        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();

    }

    @Override
    public void show() {
        Random r = new Random();
        int level = r.nextInt(3) + 1;
        map = getAssetManager().get("level" + level + ".tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        Sub sub = makeSub(new Color(1, 0, 0, 1), 40, 40);

        ControllerListener l = new ControllerListener();
        l.addListener(sub);
        listeners.add(l);

        sub = makeSub(new Color(0, 1, 0, 1), 1170, 40);

        KeyboardListener kl = new KeyboardListener();
        kl.addListener(sub);
        listeners.add(kl);

        sub = makeSub(new Color(0, 0, 1, 1), 1170, 600);
        sub = makeSub(new Color(1, 0, 1, 1), 40, 600);

    }

    private Sub makeSub(Color colour, int x, int y) {
        Sub sub = new Sub((Texture) getAssetManager().get("sub.png"), (Texture) getAssetManager().get("health.png"));
        sub.setColor(colour);
        sub.setX(x);
        sub.setY(y);
        addListeners(sub);

        characters.add(sub);
        players.add(sub);

        return sub;
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

    protected void addListeners(Entity entity) {
        entity.addListener(screenEvents);
        entity.addListener(screenSounds);
    }

    protected void addCharacter(Entity entity) {
        addListeners(entity);
        characters.add(entity);
    }

}
