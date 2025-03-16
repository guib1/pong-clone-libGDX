package com.guib.pongclone.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private float y, i = 0.1f;
    private int score = 0;
    public Rectangle rect = new Rectangle();

    public void movement(float delta) {
        this.y += delta;
    }

    public int getY() {
        return (int) this.y;
    }

    public float update() {
        return Gdx.graphics.getDeltaTime();
    }

    public int score(boolean goal) {
        if (goal) {
            this.score++;
        } else {
            return this.score;
        }
        return this.score;
    }
}
