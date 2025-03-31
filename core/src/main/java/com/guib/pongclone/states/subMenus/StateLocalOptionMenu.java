package com.guib.pongclone.states.subMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.guib.pongclone.src.MenuLayout;
import com.guib.pongclone.src.Musics;
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;
import com.guib.pongclone.states.game.StateSinglePlayerMatch;

public class StateLocalOptionMenu extends State {
    private final StateManager gsm;
    private final Musics music;
    private Stage stage;
    private SpriteBatch batch;
    private Texture background;

    public StateLocalOptionMenu(StateManager gsm, Musics music) {
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

        TextButton playButton = new TextButton("PLAY", skin);
        Label chooseSideLabel = new Label("CHOOSE SIDE", skin);
        Label chooseKeysLabel = new Label("CHOOSE KEYS", skin);
        Label botDifficultyLabel = new Label("BOT DIFFICULTY", skin);
        TextButton backButton = new TextButton("BACK", skin);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new StateSinglePlayerMatch());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        stage.addActor(playButton);
        stage.addActor(chooseSideLabel);
        stage.addActor(chooseKeysLabel);
        stage.addActor(botDifficultyLabel);
        stage.addActor(backButton);

        Object[] buttons = new Object[]{playButton, backButton};
        for (Object button : buttons) {
            stage.addActor((TextButton) button);
        }

        float numberOfObjects = buttons.length;

        MenuLayout menuLayout = new MenuLayout(numberOfObjects);

        playButton.setPosition(menuLayout.setX(playButton.getWidth()), menuLayout.setY(playButton.getHeight(), 1));
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
