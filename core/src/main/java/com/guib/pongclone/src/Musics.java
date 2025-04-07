package com.guib.pongclone.src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.guib.pongclone.preferences.GeneralPreferences;

public class Musics {
    private GeneralPreferences generalPreferences = GeneralPreferences.getInstance();
    public Music boom;
    public Music mainMusic;

    public Musics() {
        boom = Gdx.audio.newMusic(Gdx.files.internal("boom.mp3"));
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        mainMusic.setLooping(true);
    }

    public void musicVolume() {
        mainMusic.setVolume(generalPreferences.getMusicVolume());
        boom.setVolume(generalPreferences.getMusicVolume());
    }

    public void playMainMusic() {
        mainMusic.play();
    }

    public void playBoom() {
        boom.play();
    }

    public void dispose() {
        boom.dispose();
        mainMusic.dispose();
    }
}
