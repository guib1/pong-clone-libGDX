package com.guib.pongclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

public class Ball {
    private float x, y;
    private float velocityX, velocityY;
    public Circle circ = new Circle();

    public float addVelocityX(float x) {
        return velocityX += x;
    }

    public float addVelocityY(float y) {
        return velocityY += y;
    }

    public int getX() {
        return (int) this.x;
    }

    public int getY() {
        return (int) this.y;
    }

    public Ball(float deltaX, float deltaY, float initialSpeed) {
        this.x = deltaX;
        this.y = deltaY;

        Random random = new Random();
        float angle = random.nextFloat() * 2 * (float) Math.PI;
        this.velocityX = (float) Math.cos(angle) * initialSpeed;
        this.velocityY = (float) Math.sin(angle) * initialSpeed;
    }

    public void resetPosition(float centerX, float centerY) {
        this.x = centerX;
        this.y = centerY;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public void update(float deltaTime) {
        this.x += this.velocityX * deltaTime;
        this.y += this.velocityY * deltaTime;
        this.circ.setPosition(this.x, this.y);
    }

    public void reflect(boolean x, boolean y) {
        if (x) {
            this.velocityX *= -1;
        }
        if (y) {
            this.velocityY *= -1;
        }
    }
}
