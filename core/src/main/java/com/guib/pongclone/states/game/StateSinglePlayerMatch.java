package com.guib.pongclone.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Timer;
import com.guib.pongclone.preferences.GeneralPreferences;
import com.guib.pongclone.src.entities.Ball;
import com.guib.pongclone.src.entities.Paddle;
import com.guib.pongclone.src.match.MatchBase;
import com.guib.pongclone.src.match.MatchBaseConfig;
import com.guib.pongclone.states.State;

public class StateSinglePlayerMatch extends State {
    private final MatchBase match;
    private GeneralPreferences generalPreferences;

    private boolean render = false;

    public StateSinglePlayerMatch(MatchBase match) {
        this.match = match;
    }

    @Override
    public void create() {
        generalPreferences = GeneralPreferences.getInstance();
        match.batch = new SpriteBatch();
        match.background = new Texture("bg.jpg");
        match.shape = new ShapeRenderer();
        match.player1 = new Paddle();
        match.player2 = new Paddle();
        match.bot = new Paddle();
        match.ball = new Ball(0, 0, 350);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                render = true;
            }
        }, 1);

        match.glyphLayout = new GlyphLayout();

        match.font = new BitmapFont(Gdx.files.internal("font.fnt"));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        match.batch.begin();
        match.batch.setColor(0.5f, 0.5f, 0.5f, 1f);
        match.batch.draw(match.background, 0, 0);
        match.batch.end();
        match.staticBars();
        match.singlePlayerMode();
        match.ui();
        if (render) {
            match.setBall();
            localSinglePlayerMovement();
            collision();
            match.ball.update(Gdx.graphics.getDeltaTime());
        }
    }

    public void localSinglePlayerMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            match.player1.movement(generalPreferences.getPlayerSpeed() * Gdx.graphics.getDeltaTime());
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            match.player1.movement(-generalPreferences.getPlayerSpeed() * Gdx.graphics.getDeltaTime());
        }
        if (match.bot.getY() < match.ball.getY() - 30) {
            match.bot.movement(400 * Gdx.graphics.getDeltaTime());
        } else if (match.bot.getY() > match.ball.getY() + 30) {
            match.bot.movement(-400 * Gdx.graphics.getDeltaTime());
        }
    }

    public void collision() {
        // Vertical collision bars
        if (Intersector.overlaps(match.player1.rect, match.topBarRect)) {
            match.player1.movement(-generalPreferences.getPlayerSpeed() * Gdx.graphics.getDeltaTime());
        }
        if (Intersector.overlaps(match.player1.rect, match.downBarRect)) {
            match.player1.movement(generalPreferences.getPlayerSpeed() * Gdx.graphics.getDeltaTime());
        }
        if (Intersector.overlaps(match.bot.rect, match.topBarRect)) {
            match.bot.movement(-generalPreferences.getPlayerSpeed() * Gdx.graphics.getDeltaTime());
        }
        if (Intersector.overlaps(match.bot.rect, match.downBarRect)) {
            match.bot.movement(generalPreferences.getPlayerSpeed() * Gdx.graphics.getDeltaTime());
        }

        // Ball Collisions
        if (Intersector.overlaps(match.ball.circ, match.player1.rect)) {
            match.ball.paddleCollision(match.player1, 1);
            match.ball.circ.x = match.player1.rect.x + match.player1.rect.width + match.ball.circ.radius;
        }
        if (Intersector.overlaps(match.ball.circ, match.bot.rect)) {
            match.ball.paddleCollision(match.bot, -1);
            match.ball.circ.x = match.bot.rect.x - match.ball.circ.radius;
        }
        if (Intersector.overlaps(match.ball.circ, match.topBarRect)) {
            match.ball.simpleCollision(false, true);
            match.ball.circ.y = match.topBarRect.y - match.ball.circ.radius;
        }
        if (Intersector.overlaps(match.ball.circ, match.downBarRect)) {
            match.ball.simpleCollision(false, true);
            match.ball.circ.y = match.downBarRect.y + match.downBarRect.height + match.ball.circ.radius;
        }
    }

    public void endMatchCondition(){
        if (match.player1.getScore() < generalPreferences.getScore()){

        }
    }

    @Override
    public void dispose() {

    }
}
