package com.guib.pongclone.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.guib.pongclone.src.MenuLayout;
import com.guib.pongclone.src.Musics;
import com.guib.pongclone.states.subMenus.StateLocalMenu;
import com.guib.pongclone.states.subMenus.StateOnlineMenu;
import com.guib.pongclone.states.subMenus.StateOptionMenu;

import java.awt.*;

public class StateMenu extends State {
    private Stage stage;
    private SpriteBatch batch;
    private Texture background;
    private final StateManager gsm;
    private final Musics music;

    public StateMenu(StateManager gsm, Musics music) {
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

        TextButton localModeButton = new TextButton("LOCAL", skin);
        TextButton onlineModeButton = new TextButton("ONLINE", skin);
        TextButton optionsButton = new TextButton("OPTIONS", skin);
        TextButton exitButton = new TextButton("EXIT", skin);

        localModeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.push(new StateLocalMenu(gsm, music));
            }
        });

        onlineModeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.push(new StateOnlineMenu(gsm, music));
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.push(new StateOptionMenu(gsm, music));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Object[] buttons = new Object[]{localModeButton, onlineModeButton, optionsButton, exitButton};
        for (Object button : buttons) {
            stage.addActor((TextButton) button);
        }

        float numberOfObjects = buttons.length;

        MenuLayout menuLayout = new MenuLayout(numberOfObjects);

        localModeButton.setPosition(menuLayout.setX(localModeButton.getWidth()), menuLayout.setY(localModeButton.getHeight(), 1));
        onlineModeButton.setPosition(menuLayout.setX(onlineModeButton.getWidth()), menuLayout.setY(onlineModeButton.getHeight(), 2));
        optionsButton.setPosition(menuLayout.setX(optionsButton.getWidth()), menuLayout.setY(optionsButton.getHeight(), 3));
        exitButton.setPosition(menuLayout.setX(exitButton.getWidth()), menuLayout.setY(exitButton.getHeight(), 4));
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
