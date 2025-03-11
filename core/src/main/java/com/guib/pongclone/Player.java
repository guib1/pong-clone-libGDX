package com.guib.pongclone;

import com.badlogic.gdx.math.Rectangle;

public class Player {
    private float x;
    private float y;
    private float velocity;
    public Rectangle rect = new Rectangle();

    public void movement(float delta) {
        this.y += delta;
    }

    public int getY() {
        return (int) this.y;
    }

    public void update(float deltaTime) {
        this.y += this.velocityY * deltaTime;
        this.rect.setPosition(this.x, this.y);
    }

    public int score(int goal) {
        int score = 0;
        return score += goal;
    }
}
