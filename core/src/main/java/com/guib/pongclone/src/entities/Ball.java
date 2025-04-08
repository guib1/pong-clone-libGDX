package com.guib.pongclone.src.entities;

import com.badlogic.gdx.math.Circle;

import java.util.Random;

public class Ball {
    private float x, y;
    private float velocityX, velocityY;
    private final float BASE_SPEED = 550f;
    public Circle circ = new Circle();

    public int getX() {
        return (int) this.x;
    }

    public int getY() {
        return (int) this.y;
    }

    public void ballSpawn(float initialSpeed) {
        Random random = new Random();

        float angle = (float) Math.toRadians(random.nextFloat() * 120 - 60);

        int direction = random.nextBoolean() ? 1 : -1;
        this.velocityX = (float) (Math.cos(angle) * initialSpeed) * direction;
        this.velocityY = (float) Math.sin(angle) * initialSpeed;
    }

    public Ball(float deltaX, float deltaY, float initialSpeed) {
        this.x = deltaX;
        this.y = deltaY;
        ballSpawn(initialSpeed);
    }

    public void resetPosition(float centerX, float centerY, float initialSpeed) {
        this.x = centerX;
        this.y = centerY;
        ballSpawn(initialSpeed);
    }

    public void simpleCollision(boolean x, boolean y) {
        if (x) {
            this.velocityX *= -1;
        }
        if (y) {
            this.velocityY *= -1;
        }
    }

    public void paddleCollision(Paddle paddle, int alternator) {
        float impactPosition = (this.circ.y - (paddle.rect.y + paddle.rect.height / 2)) / (paddle.rect.height / 2);

        impactPosition = Math.max(-1, Math.min(1, impactPosition));

        this.velocityX = BASE_SPEED * alternator;
        this.velocityY = impactPosition * BASE_SPEED;
    }

    public void update(float deltaTime) {
        this.x += this.velocityX * deltaTime;
        this.y += this.velocityY * deltaTime;
        this.circ.setPosition(this.x, this.y);
    }
}
