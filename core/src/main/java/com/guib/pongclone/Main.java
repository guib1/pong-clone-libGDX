package com.guib.pongclone;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guib.pongclone.preferences.GeneralPreferences;
import com.guib.pongclone.src.Musics;
import com.guib.pongclone.src.match.MatchBase;
import com.guib.pongclone.src.match.MatchBaseConfig;
import com.guib.pongclone.states.StateManager;
import com.guib.pongclone.states.StateMenu;

/**
 * {@link ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private StateManager gsm;

    private Texture imageIntroduction;
    private Texture textIntroduction;

    private Musics music;

    private GeneralPreferences generalPreferences;

    @Override
    public void create() {
        generalPreferences = GeneralPreferences.getInstance();
        gsm = new StateManager();
        batch = new SpriteBatch();
        music = new Musics();

        gsm.push(new StateMenu(gsm));

        generalPreferences.load();
        gsm.create();

        imageIntroduction = new Texture("sanic.png");
        textIntroduction = new Texture("pong....png");

        music.playBoom();
        music.boom.setOnCompletionListener(musicc -> {
            music.playMainMusic();
        });
    }

    @Override
    public void render() {
        gsm.render();
        /*batch.begin();
        batch.draw(imageIntroduction, 0, 0);
        batch.setColor(effects.fading(), effects.fading(), effects.fading(), 1f);
        batch.draw(textIntroduction, 0, 0);
        batch.end();
        if (!boom.boom.isPlaying()) {
            inGame();
        }
         */
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gsm.pop();
        }
    }

    @Override
    public void dispose() {
        gsm.dispose();
        batch.dispose();
        imageIntroduction.dispose();
        textIntroduction.dispose();
    }
}
