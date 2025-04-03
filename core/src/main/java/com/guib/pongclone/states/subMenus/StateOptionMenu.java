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
import com.guib.pongclone.src.MenuLayout;
import com.guib.pongclone.src.Musics;
import com.guib.pongclone.src.match.MatchBaseConfig;
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;

public class StateOptionMenu extends State {
    private Stage stage;
    private SpriteBatch batch;
    private Texture background;
    private final StateManager gsm;
    private final Musics music;
    private final MatchBaseConfig matchBaseConfig;

    public StateOptionMenu(StateManager gsm, Musics music, MatchBaseConfig matchBaseConfig) {
        this.gsm = gsm;
        this.music = music;
        this.matchBaseConfig = matchBaseConfig;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.jpg");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));

        Label labelSensitivity = new Label("SENSITIVITY:", skin);
        CheckBox low = new CheckBox("LOW", skin);
        CheckBox medium = new CheckBox("MEDIUM", skin);
        CheckBox high = new CheckBox("HIGH", skin);
        ButtonGroup<TextButton> sensitivity = new ButtonGroup<>(low, medium, high);
        sensitivity.setMinCheckCount(1);
        sensitivity.setMaxCheckCount(1);
        Table sensitivityTable = new Table();
        sensitivityTable.add(low);
        sensitivityTable.add(medium);
        sensitivityTable.add(high);

        CheckBox audioCheckBox = new CheckBox("AUDIO", skin);

        TextButton backButton = new TextButton("BACK", skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        audioCheckBox.setChecked(true);
        audioCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float musicVolume = (audioCheckBox.isChecked() ? 0.5f : 0f);
                music.SetMusicVolume(musicVolume);
            }
        });

        ChangeListener sensitivityListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (low.isChecked()) {
                    matchBaseConfig.setPLAYER_SPEED(300f);
                } else if (medium.isChecked()) {
                    matchBaseConfig.setPLAYER_SPEED(500f);
                } else if (high.isChecked()) {
                    matchBaseConfig.setPLAYER_SPEED(700f);
                }
            }
        };
        low.addListener(sensitivityListener);
        medium.addListener(sensitivityListener);
        high.addListener(sensitivityListener);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        sensitivityTable.pack();

        stage.addActor(labelSensitivity);
        stage.addActor(sensitivityTable);
        stage.addActor(audioCheckBox);
        stage.addActor(backButton);

        MenuLayout menuLayout = new MenuLayout(4);

        labelSensitivity.setPosition(menuLayout.setX(labelSensitivity.getWidth()), menuLayout.setY(labelSensitivity.getHeight(), 1));
        sensitivityTable.setPosition(menuLayout.setX(sensitivityTable.getWidth()), menuLayout.setY(sensitivityTable.getHeight(), 2));
        audioCheckBox.setPosition(menuLayout.setX(audioCheckBox.getWidth()), menuLayout.setY(audioCheckBox.getHeight(), 3));
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
