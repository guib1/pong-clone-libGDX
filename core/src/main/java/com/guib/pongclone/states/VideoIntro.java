package com.guib.pongclone.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;

import java.io.FileNotFoundException;

public class VideoIntro{
    private SpriteBatch batch;
    private VideoPlayer videoPlayer;

    public void create() {
        batch = new SpriteBatch();

        videoPlayer = VideoPlayerCreator.createVideoPlayer();

        try {
            videoPlayer.play(Gdx.files.local("intro.mp4"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        videoPlayer.update();

        Texture video = videoPlayer.getTexture();
        if (video != null) {
            batch.begin();
            batch.draw(video, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
        }
    }

    public void dispose() {
        batch.dispose();
    }
}
