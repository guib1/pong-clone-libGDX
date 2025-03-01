package com.guib.pongclone;

public class Player {
    private float y;

    public void movement(float delta){
        this.y += delta;
    }

    public int getY () {
        return (int) this.y;
    }
}
