package com.guib.pongclone.states.subMenus;

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
import com.guib.pongclone.src.match.MatchBaseConfig;
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;
import com.guib.pongclone.states.game.StateTwoPlayerMatch;

public class StateLocalMenu extends State {
    private Stage stage;
    private SpriteBatch batch;
    private Texture background;
    private final StateManager gsm;

    public StateLocalMenu(StateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.jpg");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));

        TextButton singlePlayerModeButton = new TextButton("SINGLE PLAYER", skin);
        TextButton twoPlayerModeButton = new TextButton("TWO PLAYERS", skin);
        TextButton backButton = new TextButton("BACK", skin);

        singlePlayerModeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.push(new StateLocalOptionMenu(gsm));
            }
        });

        twoPlayerModeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.push(new StateTwoPlayerOptionMenu(gsm));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               gsm.pop();
            }
        });

        stage.addActor(singlePlayerModeButton);
        stage.addActor(twoPlayerModeButton);
        stage.addActor(backButton);

        Object[] buttons = new Object[]{singlePlayerModeButton, twoPlayerModeButton, backButton};
        for (Object button : buttons) {
            stage.addActor((TextButton) button);
        }

        float numberOfObjects = buttons.length;

        MenuLayout menuLayout = new MenuLayout(numberOfObjects);

        singlePlayerModeButton.setPosition(menuLayout.setX(singlePlayerModeButton.getWidth()), menuLayout.setY(singlePlayerModeButton.getHeight(), 1));
        twoPlayerModeButton.setPosition(menuLayout.setX(twoPlayerModeButton.getWidth()), menuLayout.setY(twoPlayerModeButton.getHeight(), 2));
        backButton.setPosition(menuLayout.setX(backButton.getWidth()), menuLayout.setY(backButton.getHeight(), 3));
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
