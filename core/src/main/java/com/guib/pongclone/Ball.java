package com.guib.pongclone;

import com.badlogic.gdx.math.Circle;

public class Ball {
    private float x,y;
    public Circle circ = new Circle();

    public int getX() {
        return (int) this.x;
    }

    public int getY() {
        return (int) this.y;
    }
}
