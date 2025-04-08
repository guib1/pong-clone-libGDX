package com.guib.pongclone.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;

import java.io.FileNotFoundException;

public class VideoIntro extends State {
    private final StateManager gsm;

    private SpriteBatch batch;
    private VideoPlayer videoPlayer;

    public VideoIntro(StateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        videoPlayer = VideoPlayerCreator.createVideoPlayer();

        try {
            videoPlayer.play(Gdx.files.internal("intro.webm"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        videoPlayer.update();
        Texture video = videoPlayer.getTexture();

        batch.begin();
        batch.draw(video, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        if (!videoPlayer.isPlaying()) {
            gsm.push(new StateMenu(gsm));
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        videoPlayer.dispose();
    }
}
