package com.guib.pongclone;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guib.pongclone.preferences.GeneralPreferences;
import com.guib.pongclone.src.Musics;
import com.guib.pongclone.states.VideoIntro;
import com.guib.pongclone.states.StateManager;

/**
 * {@link ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private StateManager gsm;
    private Musics music;

    @Override
    public void create() {
        GeneralPreferences generalPreferences = GeneralPreferences.getInstance();
        gsm = new StateManager();
        batch = new SpriteBatch();
        music = new Musics();

        gsm.create();
        gsm.startRichPresenceTimeStamp();
        gsm.push(new VideoIntro(gsm));

        music.boom.play();
        music.mainMusic.play();

        generalPreferences.load();
    }

    @Override
    public void render() {
        gsm.render();
        music.musicVolume();
    }

    @Override
    public void dispose() {
        gsm.dispose();
        batch.dispose();
        music.dispose();
    }
}
