package com.guib.pongclone.src;

import com.badlogic.gdx.Gdx;

public class MenuLayout {
    protected float numberOfObjects;
    private final float screenHeight = Gdx.graphics.getHeight();
    private final float screenWidth = Gdx.graphics.getWidth();

    public MenuLayout(float number ) {
        this.numberOfObjects = number;
    }

    public float setY(float height, int position) {
        float totalPosition = position * 3;
        float totalHeight = (height * totalPosition);
        return ((screenHeight + numberOfObjects * 50) - totalHeight) / 2f ;
    }

    public float setX(float width) {
        return (screenWidth / 2f) - (width / 2f);
    }
}
