package com.guib.pongclone.states.gameModes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.guib.pongclone.modules.Ball;
import com.guib.pongclone.modules.Player;
import com.guib.pongclone.states.State;

public class TwoPlayerMode extends State {
    private MatchBase matchBase = new MatchBase();

    @Override
    public void create() {
        matchBase.batch = new SpriteBatch();
        matchBase.background = new Texture("bg.jpg");
        matchBase.shape = new ShapeRenderer();

        matchBase.player1 = new Player();
        matchBase.player2 = new Player();
        matchBase.ball = new Ball(0, 0, 350);

        matchBase.glyphLayout = new GlyphLayout();

        matchBase.font = new BitmapFont(Gdx.files.internal("font.fnt"));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        matchBase.batch.begin();
        matchBase.batch.setColor(0.5f, 0.5f, 0.5f, 1f);
        matchBase.batch.draw(matchBase.background, 0, 0);
        matchBase.batch.end();

        matchBase.match();
        matchBase.ui();

        matchBase.pMovement();
        matchBase.collision();

        matchBase.ball.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {

    }
}
