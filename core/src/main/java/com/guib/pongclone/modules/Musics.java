package com.guib.pongclone.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Musics {
    public Music boom;
    public Music mainMusic;

    public Musics() {
        boom = Gdx.audio.newMusic(Gdx.files.internal("boom.mp3"));
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        mainMusic.setLooping(true);

        SetMusicVolume(0.5f);
    }

    public void SetMusicVolume(float volume) {
            mainMusic.setVolume(volume);
            boom.setVolume(volume);
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
