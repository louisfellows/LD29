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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.louisfellows.ld29.LD29Screen;
import com.louisfellows.ld29.entities.Entity;
import com.louisfellows.ld29.entities.Explosion;
import com.louisfellows.ld29.entities.Projectile;
import com.louisfellows.ld29.entities.Sub;
import com.louisfellows.ld29.entities.Treasure;
import com.louisfellows.ld29.screens.listeners.AIListener;
import com.louisfellows.ld29.screens.listeners.BattleScreenEventsListener;
import com.louisfellows.ld29.screens.listeners.ControlListener;
import com.louisfellows.ld29.screens.listeners.ControllerListener;
import com.louisfellows.ld29.screens.listeners.KeyboardListenerA;
import com.louisfellows.ld29.screens.listeners.KeyboardListenerB;
import com.louisfellows.ld29.screens.listeners.KeyboardListenerC;
import com.louisfellows.ld29.sounds.BattleSound;
import com.louisfellows.ld29.util.CollisionEdge;
import com.louisfellows.ld29.util.ControllerSelection;

public class BattleScreen extends LD29Screen {

    private static final int OBJECT_LAYER_ID = 2;
    TiledMap map = new TiledMap();
    MapRenderer mapRenderer;
    OrthographicCamera camera;
    Array<Entity> characters = new Array<Entity>();
    Array<Sub> players = new Array<Sub>();
    Array<ControlListener> listeners = new Array<ControlListener>();
    Array<BattleScreenEventsListener> eventListeners = new Array<BattleScreenEventsListener>();
    Array<Sprite> nonGameSprites = new Array<Sprite>();
    boolean matchComplete = false;
    BattleScreenEventsListener screenSounds = new BattleSound();
    BattleScreenEventsListener screenEvents = new BattleScreenEvents(this);
    Color[] subColours = { new Color(1, 0, 0, 1), new Color(0, 1, 0, 1), new Color(1, 1, 0, 1), new Color(1, 0, 1, 1) };
    Vector2[] subStarts = { new Vector2(40, 40), new Vector2(1170, 40), new Vector2(40, 600), new Vector2(1170, 600) };
    float timeTillBonus = 15f;
    ControllerSelection[] controllers;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.7f, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();

        timeTillBonus -= delta;
        if (timeTillBonus < 0) {
            timeTillBonus = 15f;

            for (BattleScreenEventsListener b : eventListeners) {
                b.produceBonus();
            }
        }

        for (ControlListener l : listeners) {
            l.checkKeysAndUpdate(delta);
        }

        getSpriteBatch().begin();

        actorUpdate(delta);

        for (Sprite s : nonGameSprites) {
            s.draw(getSpriteBatch());
        }

        getSpriteBatch().end();
    }

    private void actorUpdate(float delta) {
        MapLayer collisionObjectLayer = map.getLayers().get(OBJECT_LAYER_ID);
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
                        boolean checkCollision = true;
                        if (e instanceof Projectile && e2 instanceof Sub) {
                            if (((Projectile) e).wasFiredBy((Sub) e2)) {
                                checkCollision = false;
                            }
                        }
                        if (e2 instanceof Projectile && e instanceof Sub) {
                            if (((Projectile) e2).wasFiredBy((Sub) e)) {
                                checkCollision = false;
                            }
                        }

                        if (checkCollision) {
                            if (Intersector.overlaps(e.getBoundingRectangle(), e2.getBoundingRectangle())) {

                                if (e instanceof Treasure && e2 instanceof Sub) {
                                    ((Sub) e2).applyBonus(((Treasure) e).getBonus());
                                    ((Treasure) e).hit(CollisionEdge.TOP);
                                } else if (e2 instanceof Treasure && e instanceof Sub) {
                                    ((Sub) e).applyBonus(((Treasure) e2).getBonus());
                                    ((Treasure) e2).hit(CollisionEdge.TOP);
                                } else {
                                    Rectangle intersection = new Rectangle();
                                    Intersector.intersectRectangles(e.getBoundingRectangle(), e2.getBoundingRectangle(), intersection);

                                    CollisionEdge edgeE = CollisionEdge.TOP;
                                    CollisionEdge edgeE2 = CollisionEdge.TOP;

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
        eventListeners.add(screenEvents);
        eventListeners.add(screenSounds);
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

    public void setupPlayers(ControllerSelection[] controllers) {

        this.controllers = controllers;

        for (int i = 0; i < 4; i++) {
            if (controllers[i] != ControllerSelection.NONE) {
                Sub sub = makeSub(subColours[i], (int) subStarts[i].x, (int) subStarts[i].y);
                switch (controllers[i]) {
                case KEYBOARD_A:
                    KeyboardListenerA kl = new KeyboardListenerA();
                    kl.addListener(sub);
                    listeners.add(kl);
                    break;
                case KEYBOARD_B:
                    KeyboardListenerB kl_b = new KeyboardListenerB();
                    kl_b.addListener(sub);
                    listeners.add(kl_b);
                    break;
                case KEYBOARD_C:
                    KeyboardListenerC kl_c = new KeyboardListenerC();
                    kl_c.addListener(sub);
                    listeners.add(kl_c);
                    break;
                case CONTROLLER_1:
                    ControllerListener l1 = new ControllerListener(0);
                    l1.addListener(sub);
                    listeners.add(l1);
                    break;
                case CONTROLLER_2:
                    ControllerListener l2 = new ControllerListener(1);
                    l2.addListener(sub);
                    listeners.add(l2);
                    break;
                case CONTROLLER_3:
                    ControllerListener l3 = new ControllerListener(2);
                    l3.addListener(sub);
                    listeners.add(l3);
                    break;
                case CONTROLLER_4:
                    ControllerListener l4 = new ControllerListener(3);
                    l4.addListener(sub);
                    listeners.add(l4);
                    break;
                case AI:
                    AIListener ai = new AIListener(this);
                    ai.addListener(sub);
                    listeners.add(ai);
                    break;
                default:
                    break;
                }
            }
        }
    }

    public Array<Vector2> getPlayerLocations() {
        Array<Vector2> vs = new Array<Vector2>();
        for (Sub s : players) {
            vs.add(new Vector2(s.getX(), s.getY()));
        }
        return vs;
    }

    public MapLayer getMapObjectLayer() {
        return map.getLayers().get(OBJECT_LAYER_ID);
    }

    public ControllerSelection[] getControllers() {
        return controllers;
    }
}
