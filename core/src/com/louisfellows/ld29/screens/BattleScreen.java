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
import com.louisfellows.ld29.screens.listeners.BattleScreenListener;
import com.louisfellows.ld29.screens.listeners.ControlListener;
import com.louisfellows.ld29.screens.listeners.ControllerListener;
import com.louisfellows.ld29.screens.listeners.KeyboardListener;
import com.louisfellows.ld29.util.CollisionEdge;

public class BattleScreen extends LD29Screen implements BattleScreenListener {

    TiledMap map = new TiledMap();
    MapRenderer mapRenderer;
    OrthographicCamera camera;
    Array<Entity> characters = new Array<Entity>();
    Array<Sub> players = new Array<Sub>();
    Array<ControlListener> listeners = new Array<ControlListener>();
    Array<Sprite> nonGameSprites = new Array<Sprite>();
    boolean matchComplete = false;

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
        map = assetManager.get("level.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        Sub sub = makeSub(new Color(1, 0, 0, 1), 40, 40);

        ControllerListener l = new ControllerListener();
        l.addListener(sub);
        listeners.add(l);

        sub = makeSub(new Color(0, 1, 0, 1), 1000, 40);

        KeyboardListener kl = new KeyboardListener();
        kl.addListener(sub);
        listeners.add(kl);

        sub = makeSub(new Color(0, 0, 1, 1), 1000, 200);
        sub = makeSub(new Color(1, 0, 1, 1), 40, 200);

    }

    private Sub makeSub(Color colour, int x, int y) {
        Sub sub = new Sub((Texture) assetManager.get("sub.png"), (Texture) assetManager.get("health.png"));
        sub.setColor(colour);
        sub.setX(x);
        sub.setY(y);
        sub.addListener(this);

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

    @Override
    public void launchTorpedo(Vector2 position, Vector2 direction) {
        Entity torpedo = new Projectile((Texture) assetManager.get("torpedo.png"));

        torpedo.setX(position.x + direction.x);
        torpedo.setY(position.y + direction.y);

        if (direction.x < 0) {
            direction.x = -1;
        } else if (direction.x > 0) {
            direction.x = 1;
        }
        if (direction.y < 0) {
            direction.y = -1;
        } else if (direction.y > 0) {
            direction.y = 1;
        }

        direction.y *= 75;
        direction.x *= 75;

        torpedo.setDirection(direction);

        double angle = Math.atan2(-direction.x, direction.y) * 180 / Math.PI;

        torpedo.rotate((float) angle - 90f);

        torpedo.addListener(this);

        characters.add(torpedo);
    }

    @Override
    public void drawExplosion(Vector2 position) {
        Entity explosion = new Explosion((Texture) assetManager.get("explosion.png"));

        explosion.setX(position.x);
        explosion.setY(position.y);

        explosion.setDirection(new Vector2());

        explosion.addListener(this);

        Random random = new Random();
        explosion.rotate(random.nextInt(360));

        characters.add(explosion);
    }

    @Override
    public void outOfHealth(Sub entity) {
        matchComplete = true;
        for (ControlListener l : listeners) {
            l.removeListener(entity);
        }
        players.removeValue(entity, true);

        if (players.size == 1) {
            displayVictoryScreen();
        }
    }

    private void displayVictoryScreen() {
        Sprite victorySprite = new Sprite((Texture) assetManager.get("victory.png"));
        victorySprite.setColor(players.get(0).getColor());
        victorySprite.setX((getWidth() - victorySprite.getWidth()) / 2);
        victorySprite.setY((getHeight() - victorySprite.getHeight()) / 2);
        nonGameSprites.add(victorySprite);
    }
}
