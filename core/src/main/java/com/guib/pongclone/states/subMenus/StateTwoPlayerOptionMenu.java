package com.guib.pongclone.states.subMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.guib.pongclone.preferences.GeneralPreferences;
import com.guib.pongclone.src.MenuLayout;
import com.guib.pongclone.src.match.MatchBase;
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;
import com.guib.pongclone.states.game.StateTwoPlayerMatch;

public class StateTwoPlayerOptionMenu extends State {
    private final StateManager gsm;
    private GeneralPreferences generalPreferences = GeneralPreferences.getInstance();

    private Stage stage;
    private SpriteBatch batch;
    private Texture background;

    public StateTwoPlayerOptionMenu(StateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.jpg");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));

        TextButton playButton = new TextButton("PLAY", skin);
        TextButton backButton = new TextButton("BACK", skin);

        Label labelScore = new Label("SCORE TO WIN:", skin);
        CheckBox score3 = new CheckBox("3", skin);
        CheckBox score5 = new CheckBox("5", skin);
        CheckBox score7 = new CheckBox("7", skin);
        CheckBox score9 = new CheckBox("9", skin);
        CheckBox score11 = new CheckBox("11", skin);
        CheckBox score13 = new CheckBox("13", skin);
        CheckBox score15 = new CheckBox("15", skin);
        ButtonGroup<CheckBox> scores = new ButtonGroup<>(score3, score5, score9, score11, score13, score15);
        Table scoresTable = new Table();
        scoresTable.add(score3);
        scoresTable.add(score5);
        scoresTable.add(score7);
        scoresTable.add(score9);
        scoresTable.add(score11);
        scoresTable.add(score13);
        scoresTable.add(score15);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new StateTwoPlayerMatch());
            }
        });

        // function to save the visual state of selected option
        CheckBox[] scoreCheckboxes = {score3, score5, score7, score9, score11, score13, score15};
        float[] score = {3f, 5f, 7f, 9f, 11f, 13f, 15f};
        for (int i = 0; i < scoreCheckboxes.length; i++) {
            if (generalPreferences.getScore() == score[i]) {
                scoreCheckboxes[i].setChecked(true);
            }
        }

        ChangeListener sensitivityListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (int i = 0; i < scoreCheckboxes.length; i++) {
                    if (scoreCheckboxes[i].isChecked()) {
                        generalPreferences.setScore(score[i]);
                        break;
                    }
                }
            }
        };
        score3.addListener(sensitivityListener);
        score5.addListener(sensitivityListener);
        score7.addListener(sensitivityListener);
        score9.addListener(sensitivityListener);
        score11.addListener(sensitivityListener);
        score13.addListener(sensitivityListener);
        score15.addListener(sensitivityListener);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        scoresTable.pack();

        stage.addActor(playButton);
        stage.addActor(labelScore);
        stage.addActor(scoresTable);
        stage.addActor(backButton);

        MenuLayout menuLayout = new MenuLayout(4);

        playButton.setPosition(menuLayout.setX(playButton.getWidth()), menuLayout.setY(playButton.getHeight() - 3f, 1));
        labelScore.setPosition(menuLayout.setX(labelScore.getWidth()), menuLayout.setY(labelScore.getHeight(), 2));
        scoresTable.setPosition(menuLayout.setX(scoresTable.getWidth()), menuLayout.setY(scoresTable.getHeight(), 3));
        backButton.setPosition(menuLayout.setX(backButton.getWidth()), menuLayout.setY(backButton.getHeight(), 4));
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        background.dispose();
    }
}

