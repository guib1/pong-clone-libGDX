package com.guib.pongclone;

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
import com.badlogic.gdx.utils.ScreenUtils;

public class Match extends Menu {
    private SpriteBatch batch = new SpriteBatch();;
    private Texture background;
    private ShapeRenderer shape;

    private Player player1;
    private Player player2;
    private Ball ball;

    private BitmapFont font;
    private GlyphLayout glyphLayout;

    private Rectangle topBarRect;
    private Rectangle downBarRect;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.jpg");
        shape = new ShapeRenderer();

        player1 = new Player();
        player2 = new Player();
        ball = new Ball(0, 0, 350);

        glyphLayout = new GlyphLayout();

        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        background = new Texture("bg.jpg");
    }

    @Override
    public void render() {
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

    private final float PLAYER_SPEED = 700f;

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
            ball.playersCollision(player1, 1);
        }
        if (Intersector.overlaps(ball.circ, player2.rect)) {
            ball.playersCollision(player2, -1);
        }
        if (Intersector.overlaps(ball.circ, topBarRect)) {
            ball.simpleCollision(false, true);
        }
        if (Intersector.overlaps(ball.circ, downBarRect)) {
            ball.simpleCollision(false, true);
        }
    }
}
