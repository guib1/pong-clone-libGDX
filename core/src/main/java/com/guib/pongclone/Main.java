package com.guib.pongclone;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * {@link ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;

    private Texture imageIntroduction;
    private Texture textIntroduction;
    private Texture background;

    private Menu menu;
    private Match match;

    private Musics boom;
    private Musics music;

    @Override
    public void create() {
        menu = new Menu();
        match = new Match();
        batch = new SpriteBatch();

        menu.create();

        imageIntroduction = new Texture("sanic.png");
        textIntroduction = new Texture("pong....png");
        background = new Texture("bg.jpg");

        boom = new Musics();
        boom.setBoom();
        boom.boom.setOnCompletionListener(musicc -> {
            music = new Musics();
            music.setMusic();
        });
    }

    @Override
    public void render() {
        menu.render();
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
        menu.dispose();
        match.dispose();
        batch.dispose();
        imageIntroduction.dispose();
        textIntroduction.dispose();
    }
}
