package com.guib.pongclone.src.entities;

import com.badlogic.gdx.math.Rectangle;

public class Paddle {
    private float y;
    private final float i = 0.1f;
    private int score = 0;
    public Rectangle rect = new Rectangle();

    public void movement(float delta) {
        this.y += delta;
    }

    public int getY() {
        return (int) this.y;
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
