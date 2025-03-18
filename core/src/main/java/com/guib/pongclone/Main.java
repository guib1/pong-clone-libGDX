package com.guib.pongclone;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guib.pongclone.modules.Musics;
import com.guib.pongclone.states.StateManager;
import com.guib.pongclone.states.StateMenu;
import com.guib.pongclone.states.StateMatch;

/**
 * {@link ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private StateManager gsm;

    private Texture imageIntroduction;
    private Texture textIntroduction;

    private Musics boom;
    private Musics music;

    @Override
    public void create() {
        gsm = new StateManager();
        batch = new SpriteBatch();

        gsm.push(new StateMenu(gsm));

        gsm.create();

        imageIntroduction = new Texture("sanic.png");
        textIntroduction = new Texture("pong....png");

        boom = new Musics();
        boom.setBoom();
        boom.boom.setOnCompletionListener(musicc -> {
            music = new Musics();
            music.setMusic();
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
            Gdx.app.exit();
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
