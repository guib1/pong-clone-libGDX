package com.guib.pongclone.src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.guib.pongclone.preferences.GeneralPreferences;

public class Musics {
    private GeneralPreferences generalPreferences = GeneralPreferences.getInstance();
    public Music mainMusic;

    public Musics() {
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        mainMusic.setLooping(true);
    }

    public void musicVolume() {
        mainMusic.setVolume(generalPreferences.getMusicVolume());
    }

    public void playMainMusic() {
        mainMusic.play();
    }

    public void dispose() {
        mainMusic.dispose();
    }
}
