package com.guib.pongclone;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * {@link ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shape;

    private Texture imageIntroduction;
    private Texture textIntroduction;
    private Texture background;

    private Musics boom;
    private Musics music;

    private Player player1;
    private Player player2;
    private Ball ball;

    private Rectangle topBarRect;
    private Rectangle downBarRect;

//    private String text(String textField) {
//        style.font = new BitmapFont();
//        style.fontColor = Color.CHARTREUSE;
//
//        TextField field = new TextField("", style);
//        field.setText("Test");
//        field.setWidth(150);
//    }

    @Override
    public void create() {
        shape = new ShapeRenderer();
        batch = new SpriteBatch();
        player1 = new Player();
        player2 = new Player();
        ball = new Ball();

        imageIntroduction = new Texture("sanic.png");
        textIntroduction = new Texture("pong....png");
        background = new Texture("bg.jpeg");

        boom = new Musics();
        boom.setBoom();
        boom.boom.setOnCompletionListener(musicc -> {
            music = new Musics();
            music.setMusic();
        });
    }

    @Override
    public void render() {
        float pCenterY = (Gdx.graphics.getHeight() - 70) / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;
        float centerX = Gdx.graphics.getWidth() / 2f;

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(imageIntroduction, 0, 0);
        batch.draw(textIntroduction, 0, 0);
        batch.end();
        if (!boom.boom.isPlaying()) {
            ScreenUtils.clear(1, 0, 0, 1, true);
            batch.begin();
            batch.setColor(0.5f, 0.5f, 0.5f, 1f);
            batch.draw(background, 0, 0);
            batch.end();

            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(Color.WHITE);
            downBarRect = new Rectangle(0, 0, Gdx.graphics.getWidth(), 20);
            topBarRect = new Rectangle(0, Gdx.graphics.getHeight() - 20, Gdx.graphics.getWidth(), 20);

            shape.rect(topBarRect.x, topBarRect.y, topBarRect.width, topBarRect.height);
            shape.rect(downBarRect.x, downBarRect.y, downBarRect.width, downBarRect.height);
            shape.end();

            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(Color.WHITE);
            // start of the 2 players
            player1.rect = new Rectangle(50, player1.getY() + pCenterY, 20, 70);
            player2.rect = new Rectangle(Gdx.graphics.getWidth() - 70, player2.getY() + pCenterY, 20, 70);
            shape.rect(player1.rect.x, player1.rect.y, player1.rect.width, player1.rect.height);
            shape.rect(player2.rect.x, player2.rect.y, player2.rect.width, player2.rect.height);
            shape.end();

            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(Color.WHITE);
            ball.circ = new Circle(ball.getX() + centerX, ball.getY() + centerY, 10);
            shape.circle(ball.circ.x, ball.circ.y, ball.circ.radius);
            shape.end();

            ball.randMovement(ball.getXRand() * Gdx.graphics.getDeltaTime(), ball.getYRand() * Gdx.graphics.getDeltaTime());

            pMovement();
            collision();
        }
    }

    private final float PLAYER_SPEED = 500f;

    public void pMovement() {
        // players movement
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

        // Ball Collision
        if (Intersector.overlaps(ball.circ, player1.rect)) {
            ball.randMovement(-ball.getXRand() * Gdx.graphics.getDeltaTime(), ball.getYRand() * Gdx.graphics.getDeltaTime());
        }
        if (Intersector.overlaps(ball.circ, player2.rect)) {
            ball.randMovement(-ball.getXRand() * Gdx.graphics.getDeltaTime(), ball.getYRand() * Gdx.graphics.getDeltaTime());
        }
        if (Intersector.overlaps(ball.circ, topBarRect)) {
            ball.randMovement(ball.getXRand() * Gdx.graphics.getDeltaTime(), -ball.getYRand() * Gdx.graphics.getDeltaTime());
        }
        if (Intersector.overlaps(ball.circ, downBarRect)) {
            ball.randMovement(ball.getXRand() * Gdx.graphics.getDeltaTime(), -ball.getYRand() * Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        imageIntroduction.dispose();
        textIntroduction.dispose();
    }
}
