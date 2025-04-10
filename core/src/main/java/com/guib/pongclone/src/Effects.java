package com.guib.pongclone.src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Effects {
    float a = 0.0f;
    float b = 1.0f;

    // Recommend for "fadeTime" = 0.01f
    public void setFadeInOrOut(int fadeInOrOut, float fadeTime) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        a += fadeTime;
        if (a >= 1.0f) {
            a = 1.0f;
        }
        b -= fadeTime;
        if (b <= 0.0f) {
            b = 0.0f;
        }

        // a = fade in | b = fade out
        float[] effects = {a, b, 0};

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        switch (fadeInOrOut) {
            case 0:
                shapeRenderer.setColor(0, 0, 0, effects[0]);
                break;
            case 1:
                shapeRenderer.setColor(0, 0, 0, effects[1]);
                break;
            case 2:
                shapeRenderer.setColor(0, 0, 0, effects[2]);
                break;
        }
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
