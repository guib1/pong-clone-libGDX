package com.guib.pongclone.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.guib.pongclone.src.DiscordRichPresence;
import com.guib.pongclone.src.Effects;
import com.guib.pongclone.src.MenuLayout;
import com.guib.pongclone.states.subMenus.StateLocalMenu;
import com.guib.pongclone.states.subMenus.StateOptionMenu;

public class StateMenu extends State {
    private final StateManager gsm;

    private Stage stage;
    private SpriteBatch batch;
    private Texture background;
    private Texture logo;
    private Effects effect = new Effects();

    public StateMenu(StateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void create() {
        gsm.setRichPresence("On menus", "Main Menu", "", false);
        batch = new SpriteBatch();
        background = new Texture("bg.jpg");
        logo = new Texture("logo.png");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));

        Image menuLogo = new Image(logo);
        TextButton localModeButton = new TextButton("PLAY", skin);
        TextButton optionsButton = new TextButton("OPTIONS", skin);
        TextButton exitButton = new TextButton("EXIT", skin);

        localModeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.push(new StateLocalMenu(gsm));
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.push(new StateOptionMenu(gsm));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Object[] buttons = new Object[]{localModeButton, optionsButton, exitButton};
        for (Object button : buttons) {
            stage.addActor((TextButton) button);
        }
        Table tableLogo = new Table();
        tableLogo.add(menuLogo).width(170).height(170);
        stage.addActor(tableLogo);

        float numberOfObjects = buttons.length;

        MenuLayout menuLayout = new MenuLayout(numberOfObjects + 1);

        tableLogo.setPosition(menuLayout.setX(tableLogo.getWidth()), menuLayout.setY(tableLogo.getHeight(), 1));
        localModeButton.setPosition(menuLayout.setX(localModeButton.getWidth()), menuLayout.setY(localModeButton.getHeight(), 3));
        optionsButton.setPosition(menuLayout.setX(optionsButton.getWidth()), menuLayout.setY(optionsButton.getHeight(), 4));
        exitButton.setPosition(menuLayout.setX(exitButton.getWidth()), menuLayout.setY(exitButton.getHeight(), 5));
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

        effect.setFadeInOrOut(1, 0.01f);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        background.dispose();
        logo.dispose();
    }
}
