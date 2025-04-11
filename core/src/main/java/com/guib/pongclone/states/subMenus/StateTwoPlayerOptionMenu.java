package com.guib.pongclone.states.subMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;
import com.guib.pongclone.states.game.StateTwoPlayerMatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StateTwoPlayerOptionMenu extends State {
    private final StateManager gsm;
    private GeneralPreferences generalPreferences;

    private Stage stage;
    private SpriteBatch batch;
    private Texture background;

    public StateTwoPlayerOptionMenu(StateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void create() {
        gsm.setRichPresence("On menus", "TwoPlayer Mode Menu", "", false);
        generalPreferences = GeneralPreferences.getInstance();

        batch = new SpriteBatch();
        background = new Texture("bg.jpg");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));

        TextButton playButton = new TextButton("PLAY", skin);
        TextButton backButton = new TextButton("BACK", skin);

        Label labelScore = new Label("SCORE TO WIN:", skin);
        ArrayList<CheckBox> scores = new ArrayList<>();
        for (int i = 3; i <= 15; i += 2) {
            scores.add(new CheckBox(Integer.toString(i), skin));
        }
        ButtonGroup<CheckBox> score = new ButtonGroup<>(scores.get(0), scores.get(1), scores.get(2), scores.get(3), scores.get(4), scores.get(5), scores.get(6));
        Table scoresTable = new Table();

        Collections.sort(scores, new Comparator<CheckBox>() {
            @Override
            public int compare(CheckBox o1, CheckBox o2) {
                return 0;
            }
        });

        for (CheckBox box : scores) {
            scoresTable.add(box);
        }

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new StateTwoPlayerMatch(gsm));
            }
        });

        // function to save the visual state of selected option
        CheckBox[] scoreCheckboxes = {scores.get(0), scores.get(1), scores.get(2), scores.get(3), scores.get(4), scores.get(5), scores.get(6)};
        float[] score2 = {3f, 5f, 7f, 9f, 11f, 13f, 15f};
        for (int i = 0; i < scoreCheckboxes.length; i++) {
            if (generalPreferences.getScore() == score2[i]) {
                scoreCheckboxes[i].setChecked(true);
            }
        }

        ChangeListener sensitivityListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (int i = 0; i < scoreCheckboxes.length; i++) {
                    if (scoreCheckboxes[i].isChecked()) {
                        generalPreferences.setScore(score2[i]);
                        break;
                    }
                }
            }
        };

        for (CheckBox box : scores) {
            box.addListener(sensitivityListener);
        }

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

