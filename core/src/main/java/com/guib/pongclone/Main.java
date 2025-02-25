package com.guib.pongclone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.audio.Music;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture imageIntroduction;
    private Texture textIntroduction;
    private Texture background;
    private Musics boom;
    private Musics music;

//    private String text(String textField) {
//        style.font = new BitmapFont();
//        style.fontColor = Color.CHARTREUSE;
//
//        TextField field = new TextField("", style);
//        field.setText("Test");
//        field.setWidth(150);
//    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        imageIntroduction = new Texture("sanic.png");
        textIntroduction = new Texture("pong....png");
        background = new Texture("bg.jpeg");

        boom = new Musics();
        boom.setBoom();
        boom.boom.setOnCompletionListener(new Music.OnCompletionListener(){
            @Override
            public void onCompletion(Music musicc) {
                music = new Musics();
                music.setMusic();
            }
        });
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(imageIntroduction, 0, 0);
        batch.draw(textIntroduction, 0, 0);
        batch.end();
        if (!boom.boom.isPlaying()) {
            ScreenUtils.clear(1, 0, 0, 1, true);
            batch.begin();
            batch.draw(background, 0, 0);

            batch.end();
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        imageIntroduction.dispose();
        textIntroduction.dispose();
    }
}
