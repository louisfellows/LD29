package com.louisfellows.ld29;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class LD29Screen extends Stage implements Screen {
    protected static AssetManager assetManager = new AssetManager();

    public static void loadAssets() {
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("level.tmx", TiledMap.class);

        assetManager.setLoader(Texture.class, new TextureLoader(new InternalFileHandleResolver()));
        assetManager.load("sub.png", Texture.class);
        assetManager.load("torpedo.png", Texture.class);
        assetManager.load("explosion.png", Texture.class);
        assetManager.load("health.png", Texture.class);
        assetManager.load("victory.png", Texture.class);

        assetManager.finishLoading();
    }
}
