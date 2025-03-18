package com.guib.pongclone.states.subMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;
import com.guib.pongclone.states.StateMatch;

public class StateOptionMenu extends State {
    private Stage stage;
    private SpriteBatch batch;
    private Texture background;
    private final StateManager gsm;

    public StateOptionMenu(StateManager gsm) {
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
        CheckBox audioCheckBox = new CheckBox("AUDIO", skin);
        TextButton optionsButton = new TextButton("OPTIONS", skin);
        TextButton backButton = new TextButton("BACK", skin);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new StateMatch(gsm));
            }
        });

        audioCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        stage.addActor(playButton);
        stage.addActor(audioCheckBox);
        stage.addActor(backButton);

        float buttonHeight = playButton.getHeight();
        float middleButtonY = Gdx.graphics.getHeight() / 2f - buttonHeight / 2f;
        float topButtonY = middleButtonY + buttonHeight * 2f;
        float bottomButtonY = middleButtonY - buttonHeight * 2f;

        playButton.setPosition(Gdx.graphics.getWidth() / 2f - playButton.getWidth() / 2f, topButtonY);
        audioCheckBox.setPosition(Gdx.graphics.getWidth() / 2f - audioCheckBox.getWidth() / 2f, middleButtonY);
        backButton.setPosition(Gdx.graphics.getWidth() / 2f - backButton.getWidth() / 2f, bottomButtonY);
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
