package com.guib.pongclone.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.guib.pongclone.src.Effects;
import com.guib.pongclone.src.MenuLayout;
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;
import com.guib.pongclone.states.StateMenu;

public class StateEndMatchCondition extends State {
    private StateManager gsm;
    private Stage stage;

    private Effects effect = new Effects();
    private String winner;

    public StateEndMatchCondition(StateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void create() {
        gsm.setRichPresence("Result", winner, "playing", true);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));

        Label winner = new Label(getWinner(), skin);
        TextButton restartButton = new TextButton("RESTART MATCH", skin);
        TextButton backButton = new TextButton("BACK MENU", skin);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new StateMenu(gsm));
            }
        });

        stage.addActor(winner);
        stage.addActor(restartButton);
        stage.addActor(backButton);

        float numberOfObjects = 3;

        MenuLayout menuLayout = new MenuLayout(numberOfObjects);

        winner.setPosition(menuLayout.setX(winner.getWidth()), menuLayout.setY(winner.getHeight() - 5, 1));
        restartButton.setPosition(menuLayout.setX(restartButton.getWidth()), menuLayout.setY(restartButton.getHeight(), 2));
        backButton.setPosition(menuLayout.setX(backButton.getWidth()), menuLayout.setY(backButton.getHeight(), 3));
    }

    public String getWinner() {
        return winner;
    }

    // true = Player 1 win | false = Player 2 win
    public void setWinnerMultiPlayer(boolean winner) {
        this.winner = winner ? "PLAYER 1 WIN" : "PLAYER 2 WIN";
    }

    // true = You Win | false = You lose
    public void setWinnerSinglePlayer(boolean winner) {
        this.winner = winner ? "WINNER" : "GAME OVER";
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        ShapeRenderer shapeRenderer = new ShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.rect(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f - 100, 400, 200);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(Gdx.graphics.getWidth() / 2f - 202.5f, Gdx.graphics.getHeight() / 2f - 102.5f, 405, 205);
        shapeRenderer.end();

        stage.act(delta);
        stage.draw();
        effect.setFadeInOrOut(1, 0.05f);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
