package com.guib.pongclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private float y;
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
