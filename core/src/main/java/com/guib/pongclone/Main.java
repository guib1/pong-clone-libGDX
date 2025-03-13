package com.guib.pongclone;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

    private Menu menu;
    private Effects effects;

    private Musics boom;
    private Musics music;

    private Player player1;
    private Player player2;
    private Ball ball;

    private BitmapFont font;
    private GlyphLayout glyphLayout;

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
        menu = new Menu();
        effects  = new Effects();
        player1 = new Player();
        player2 = new Player();
        glyphLayout = new GlyphLayout();
        ball = new Ball(0, 0, 250);

        imageIntroduction = new Texture("sanic.png");
        textIntroduction = new Texture("pong....png");
        background = new Texture("bg.jpeg");
        font = new BitmapFont(Gdx.files.internal("font.fnt"));

        boom = new Musics();
        boom.setBoom();
        boom.boom.setOnCompletionListener(musicc -> {
            music = new Musics();
            music.setMusic();
        });
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(imageIntroduction, 0, 0);
        batch.setColor(effects.fading(), effects.fading(), effects.fading(), 1f);
        batch.draw(textIntroduction, 0, 0);
        batch.end();
        if (!boom.boom.isPlaying()) {
            inGame();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    public void inGame() {
        ScreenUtils.clear(1, 0, 0, 1, true);
        batch.begin();
        batch.setColor(0.5f, 0.5f, 0.5f, 1f);
        batch.draw(background, 0, 0);
        batch.end();

        match();
        ui();

        pMovement();
        collision();

        ball.update(Gdx.graphics.getDeltaTime());
    }

    public void match() {
        float pCenterY = (Gdx.graphics.getHeight() - 70) / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;
        float centerX = Gdx.graphics.getWidth() / 2f;

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

        if (ball.circ.x > Gdx.graphics.getWidth()) {
            ball.resetPosition(0, 0, 250);
            player1.score(true);
        }
        if (ball.circ.x < 0) {
            ball.resetPosition(0, 0, 250);
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

    private final float PLAYER_SPEED = 500f;

    public void pMovement() {
        // players movement
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player1.movement(PLAYER_SPEED * player1.update());
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player1.movement(-PLAYER_SPEED * player1.update());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player2.movement(PLAYER_SPEED * player2.update());
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player2.movement(-PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }
    }

    public void collision() {
        // Vertical collision bars
        if (Intersector.overlaps(player1.rect, topBarRect)) {
            player1.movement(-PLAYER_SPEED * player1.update());
        }
        if (Intersector.overlaps(player1.rect, downBarRect)) {
            player1.movement(PLAYER_SPEED * player1.update());
        }
        if (Intersector.overlaps(player2.rect, topBarRect)) {
            player2.movement(-PLAYER_SPEED * player2.update());
        }
        if (Intersector.overlaps(player2.rect, downBarRect)) {
            player2.movement(PLAYER_SPEED * player2.update());
        }

        // Ball Collisions
        if (Intersector.overlaps(ball.circ, player1.rect)) {
            ball.collisionWalls(true, false);
            ball.addVelocityX(50);
            ball.addVelocityY(-20);
        }
        if (Intersector.overlaps(ball.circ, player2.rect)) {
            ball.collisionWalls(true, false);
            ball.addVelocityX(-50);
            ball.addVelocityY(20);
        }
        if (Intersector.overlaps(ball.circ, topBarRect)) {
            ball.collisionWalls(false, true);
            ball.addVelocityX(40);
        }
        if (Intersector.overlaps(ball.circ, downBarRect)) {
            ball.collisionWalls(false, true);
            ball.addVelocityX(40);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        shape.dispose();
        imageIntroduction.dispose();
        textIntroduction.dispose();
    }
}
