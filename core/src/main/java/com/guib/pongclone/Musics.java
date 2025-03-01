package com.guib.pongclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Musics {
    public Music boom;
    public Music music;

    public void setBoom() {
        boom = Gdx.audio.newMusic(Gdx.files.internal("boom.mp3"));
        boom.play();
    }

    public void setMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.play();
        music.setVolume(0.5f);
        music.isLooping();
    }
}
