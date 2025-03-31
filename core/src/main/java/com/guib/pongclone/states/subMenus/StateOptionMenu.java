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
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;

public class StateOptionMenu extends State {
    private Stage stage;
    private SpriteBatch batch;
    private Texture background;
    private final StateManager gsm;
    private final Musics music;

    public StateOptionMenu(StateManager gsm, Musics music) {
        this.gsm = gsm;
        this.music = music;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.jpg");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));

        CheckBox audioCheckBox = new CheckBox("AUDIO", skin);

        TextButton backButton = new TextButton("BACK", skin);

        audioCheckBox.setChecked(true);

        audioCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float musicVolume = (audioCheckBox.isChecked() ? 0.5f : 0f);
                music.SetMusicVolume(musicVolume);
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        Object[] buttons = new Object[]{audioCheckBox, backButton};
        for (Object button : buttons) {
            stage.addActor((TextButton) button);
        }

        float numberOfObjects = buttons.length;

        MenuLayout menuLayout = new MenuLayout(numberOfObjects);

        audioCheckBox.setPosition(menuLayout.setX(audioCheckBox.getWidth()), menuLayout.setY(audioCheckBox.getHeight(), 1));
        backButton.setPosition(menuLayout.setX(backButton.getWidth()), menuLayout.setY(backButton.getHeight(), 2));
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
