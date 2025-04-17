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
import com.guib.pongclone.src.DiscordRichPresence;
import com.guib.pongclone.src.MenuLayout;
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;

public class StateOptionMenu extends State {
    private final StateManager gsm;
    private GeneralPreferences generalPreferences = GeneralPreferences.getInstance();

    private Stage stage;
    private SpriteBatch batch;
    private Texture background;

    public StateOptionMenu(StateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void create() {
        gsm.setRichPresence("On menus", "Main Options Menu", "", false);
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

        // function to save the visual state of selected option
        float[] musicVolume = {0.5f, 0f};
        audioCheckBox.setChecked(generalPreferences.getMusicVolume() == musicVolume[0]);

        audioCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                generalPreferences.setMusicVolume(musicVolume[audioCheckBox.isChecked() ? 0 : 1]);
            }
        });

        // function to save the visual state of selected option
        CheckBox[] sensitivityCheckboxes = {low, medium, high};
        float[] playerSpeed = {300f, 500f, 650f};
        for (int i = 0; i < sensitivityCheckboxes.length; i++) {
            if (generalPreferences.getPlayerSpeed() == playerSpeed[i]) {
                sensitivityCheckboxes[i].setChecked(true);
            }
        }

        ChangeListener sensitivityListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (int i = 0; i < sensitivityCheckboxes.length; i++) {
                    if (sensitivityCheckboxes[i].isChecked()){
                        generalPreferences.setPlayerSpeed(playerSpeed[i]);
                    }
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
