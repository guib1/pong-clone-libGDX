package com.guib.pongclone.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import com.guib.pongclone.preferences.GeneralPreferences;
import com.guib.pongclone.src.entities.Ball;
import com.guib.pongclone.src.entities.Paddle;
import com.guib.pongclone.src.MatchBase;
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;

public class StateSinglePlayerMatch extends State {
    private final StateManager gsm;
    private GeneralPreferences generalPreferences;
    private final MatchBase match = new MatchBase();

    private boolean render = false;

    public StateSinglePlayerMatch(StateManager gsm) {
        this.gsm = gsm;
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
        gsm.setRichPresence("LOCAL | SinglePlayer Match", getThisMatchScore(), "playing", true);
        if (render) {
            match.setBall();
            match.localSinglePlayerMovement();
            match.collision();
            match.ball.update(Gdx.graphics.getDeltaTime());
        }
        endMatchCondition();
    }

    public void endMatchCondition() {
        StateEndMatchCondition stateEndMatchCondition = new StateEndMatchCondition(gsm);
        if (match.player1.getScore() >= generalPreferences.getScore()) {
            stateEndMatchCondition.setWinnerSinglePlayer(true);
            gsm.push(stateEndMatchCondition);
        }
        if (match.bot.getScore() >= generalPreferences.getScore()) {
            stateEndMatchCondition.setWinnerSinglePlayer(false);
            gsm.push(stateEndMatchCondition);
        }
    }

    public String getThisMatchScore() {
        String player1Score = Integer.toString(match.player1.getScore());
        String botScore = Integer.toString(match.bot.getScore());
        return player1Score + " x " + botScore;
    }

    @Override
    public void dispose() {
        match.dispose();
    }
}
