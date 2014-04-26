package com.louisfellows.ld29;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class LD29Screen extends Stage implements Screen {
    private static AssetManager assetManager = new AssetManager();

    public static void loadAssets() {
        getAssetManager().setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        getAssetManager().load("level1.tmx", TiledMap.class);
        getAssetManager().load("level2.tmx", TiledMap.class);
        getAssetManager().load("level3.tmx", TiledMap.class);

        getAssetManager().setLoader(Texture.class, new TextureLoader(new InternalFileHandleResolver()));
        getAssetManager().load("sub.png", Texture.class);
        getAssetManager().load("torpedo.png", Texture.class);
        getAssetManager().load("explosion.png", Texture.class);
        getAssetManager().load("health.png", Texture.class);
        getAssetManager().load("victory.png", Texture.class);

        getAssetManager().setLoader(Sound.class, new SoundLoader(new InternalFileHandleResolver()));
        getAssetManager().load("bang.wav", Sound.class);

        getAssetManager().finishLoading();
    }

    public static AssetManager getAssetManager() {
        return assetManager;
    }

}
