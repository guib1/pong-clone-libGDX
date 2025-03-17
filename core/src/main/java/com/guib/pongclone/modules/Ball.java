package com.guib.pongclone.modules;

import com.badlogic.gdx.math.Circle;

import java.util.Random;

public class Ball {
    private float x, y;
    private float velocityX, velocityY;
    public Circle circ = new Circle();

    public int getX() {
        return (int) this.x;
    }

    public int getY() {
        return (int) this.y;
    }

    public void ballSpawn(float initialSpeed) {
        Random random = new Random();
        float angle = (float) (random.nextFloat() * Math.PI / 2 - Math.PI / 4);
        this.velocityX = (float) Math.cos(angle) * initialSpeed;
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

    public void playersCollision(Player player, int alternator) {
        float impactPosition = (this.circ.y - (player.rect.y + player.rect.height / 2)) / (player.rect.height / 2);

        impactPosition = Math.max(-1, Math.min(1, impactPosition));

        float baseSpeed = 550;

        this.velocityX = baseSpeed * alternator;
        this.velocityY = impactPosition * baseSpeed;
    }

    public void update(float deltaTime) {
        this.x += this.velocityX * deltaTime;
        this.y += this.velocityY * deltaTime;
        this.circ.setPosition(this.x, this.y);
    }
}
