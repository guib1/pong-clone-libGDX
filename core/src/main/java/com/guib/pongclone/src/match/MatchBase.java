package com.guib.pongclone.src.match;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.guib.pongclone.src.entities.Ball;
import com.guib.pongclone.src.entities.Paddle;

public class MatchBase {
    public final MatchBaseConfig matchConfig;
    private final float centerY = Gdx.graphics.getHeight() / 2f;
    private final float centerX = Gdx.graphics.getWidth() / 2f;
    public SpriteBatch batch;
    public Texture background;
    public ShapeRenderer shape;
    public Paddle player1;
    public Paddle player2;
    public Paddle bot;
    public Ball ball;
    public BitmapFont font;
    public GlyphLayout glyphLayout;

    public final float PLAYER_SPEED = 700f;
    public Rectangle topBarRect;
    public Rectangle downBarRect;
    public float pCenterY = (Gdx.graphics.getHeight() - 70) / 2f;

    public MatchBase() {
        matchConfig = new MatchBaseConfig(this);
    }

    public void staticBars() {
        // those 2 have to be final because it'll be used in collisions
        downBarRect = new Rectangle(0, 0, Gdx.graphics.getWidth(), 20);
        topBarRect = new Rectangle(0, Gdx.graphics.getHeight() - 20, Gdx.graphics.getWidth(), 20);
        Rectangle middleBarRect = new Rectangle(centerX - 5, 0, 10, Gdx.graphics.getHeight());

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.WHITE);
        shape.rect(topBarRect.x, topBarRect.y, topBarRect.width, topBarRect.height);
        shape.rect(downBarRect.x, downBarRect.y, downBarRect.width, downBarRect.height);
        shape.rect(middleBarRect.x, middleBarRect.y, middleBarRect.width, middleBarRect.height);
        shape.end();
    }

    public void twoPlayerMode() {
        // those 2 have to be final because it'll be used in collisions
        player1.rect = new Rectangle(50, player1.getY() + pCenterY, 20, 70);
        player2.rect = new Rectangle(Gdx.graphics.getWidth() - 70, player2.getY() + pCenterY, 20, 70);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(player1.rect.x, player1.rect.y, player1.rect.width, player1.rect.height);
        shape.rect(player2.rect.x, player2.rect.y, player2.rect.width, player2.rect.height);
        shape.setColor(Color.WHITE);
        shape.end();
    }

    public void singlePlayerMode() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        matchConfig.singlePlayerChooseSide(matchConfig.chooseSide);
        shape.setColor(Color.WHITE);
        shape.end();
    }

    public void setBall() {
        ball.circ = new Circle(ball.getX() + centerX, ball.getY() + centerY, 10);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.WHITE);
        shape.circle(ball.circ.x, ball.circ.y, ball.circ.radius);
        shape.end();

        // ball respawning after someone scores
        if (ball.circ.x > Gdx.graphics.getWidth()) {
            ball.resetPosition(0, 0, 350);
            player1.score(true);
        }
        if (ball.circ.x < 0) {
            ball.resetPosition(0, 0, 350);
            player2.score(true);
        }
    }

    public void ui() {
        glyphLayout.setText(font, player1.score(false) + "         " + player2.score(false));
        float textWidth = glyphLayout.width;
        float textHeight = glyphLayout.height;
        float x = (Gdx.graphics.getWidth() - textWidth) / 2;
        float y = (Gdx.graphics.getHeight() + textHeight) / 2;

        batch.begin();
        font.draw(batch, glyphLayout, x, y + 180);
        batch.end();
    }

    public void localTwoPlayerMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player1.movement(PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player1.movement(-PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player2.movement(PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player2.movement(-PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }
    }

    public void localSinglePlayerMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player1.movement(PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player1.movement(-PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (bot.getY() < ball.getY() - 30) {
            bot.movement(400 * Gdx.graphics.getDeltaTime());
        } else if (bot.getY() > ball.getY() + 30) {
            bot.movement(-400 * Gdx.graphics.getDeltaTime());
        }
    }

    public void collision() {
        // Vertical collision bars
        if (Intersector.overlaps(player1.rect, topBarRect)) {
            player1.movement(-PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (Intersector.overlaps(player1.rect, downBarRect)) {
            player1.movement(PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (Intersector.overlaps(player2.rect, topBarRect)) {
            player2.movement(-PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (Intersector.overlaps(player2.rect, downBarRect)) {
            player2.movement(PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (Intersector.overlaps(bot.rect, topBarRect)) {
            bot.movement(-PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (Intersector.overlaps(bot.rect, downBarRect)) {
            bot.movement(PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }

        // Ball Collisions
        if (Intersector.overlaps(ball.circ, player1.rect)) {
            ball.paddleCollision(player1, 1);
            ball.circ.x = player1.rect.x + player1.rect.width + ball.circ.radius;
        }
        if (Intersector.overlaps(ball.circ, player2.rect)) {
            ball.paddleCollision(player2, -1);
            ball.circ.x = player2.rect.x - ball.circ.radius;
        }
        if (Intersector.overlaps(ball.circ, bot.rect)) {
            ball.paddleCollision(bot, -1);
            ball.circ.x = bot.rect.x - ball.circ.radius;
        }
        if (Intersector.overlaps(ball.circ, topBarRect)) {
            ball.simpleCollision(false, true);
            ball.circ.y = topBarRect.y - ball.circ.radius;
        }
        if (Intersector.overlaps(ball.circ, downBarRect)) {
            ball.simpleCollision(false, true);
            ball.circ.y = downBarRect.y + downBarRect.height + ball.circ.radius;
        }
    }

    public void dispose() {
        background.dispose();
        batch.dispose();
        shape.dispose();
        font.dispose();
    }
}
