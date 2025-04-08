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

    private GeneralPreferences generalPreferences;

    @Override
    public void create() {
        generalPreferences = GeneralPreferences.getInstance();
        gsm = new StateManager();
        batch = new SpriteBatch();
        music = new Musics();

        gsm.create();
        gsm.push(new VideoIntro(gsm));

        music.boom.play();
        music.mainMusic.play();

        generalPreferences.load();
    }

    @Override
    public void render() {
        gsm.render();
        music.musicVolume();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gsm.pop();
        }
    }

    @Override
    public void dispose() {
        gsm.dispose();
        batch.dispose();
        music.dispose();
    }
}
