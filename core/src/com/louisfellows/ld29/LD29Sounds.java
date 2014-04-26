package com.louisfellows.ld29;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;

public class LD29Sounds {
    private static AssetManager soundManager = new AssetManager();

    public static void loadAssets() {
        getAssetManager().setLoader(Sound.class, new SoundLoader(new InternalFileHandleResolver()));
        getAssetManager().load("bang.wav", Sound.class);
        getAssetManager().load("torpedo.wav", Sound.class);

        getAssetManager().finishLoading();
    }

    public static AssetManager getAssetManager() {
        return soundManager;
    }
}
