package com.guib.pongclone;

import com.badlogic.gdx.math.Circle;

import java.util.Random;

public class Ball {
    private float x, y;
    public Circle circ = new Circle();

    private final Random random = new Random();
    private final boolean randomAddX = random.nextBoolean();
    private final boolean randomAddY = random.nextBoolean();
    private final float randomX = random.nextFloat(1, 250);
    private final float randomY = random.nextFloat(1, 250);

    public int getX() {
        return (int) this.x;
    }

    public int getY() {
        return (int) this.y;
    }

    public float getXRand() {
        return randomX;
    }

    public float getYRand() {
        return randomY;
    }

    public void randMovement(float deltaX, float deltaY) {
        if (randomAddX) {
            this.x += deltaX;
        } else {
            this.x -= deltaX;
        }
        if (randomAddY) {
            this.y += deltaY;
        } else {
            this.y -= deltaY;
        }
    }
}
