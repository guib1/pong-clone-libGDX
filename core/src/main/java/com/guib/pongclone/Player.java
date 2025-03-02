package com.guib.pongclone;

import com.badlogic.gdx.math.Rectangle;

public class Player {
    private float y;
    public Rectangle rect = new Rectangle();

    public void movement(float delta) {
        this.y += delta;
    }

    public int getY() {
        return (int) this.y;
    }

    public float editY(float y) {
        return this.y = y;
    }
}
