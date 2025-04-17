package com.guib.pongclone.states.subMenus.onlineMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.guib.pongclone.src.MenuLayout;
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;

public class StateServerMenu extends State {
    private StateManager gsm;

    private Stage stage;
    private SpriteBatch batch;
    private Texture background;

    public StateServerMenu(StateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void create() {
        gsm.setRichPresence("On menus", "Creating a online game server", "", false);
        batch = new SpriteBatch();
        background = new Texture("bg.jpg");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));

        Label serverStartedLabel = new Label("SERVER STARTED!", skin);
        Label hostIpLabel = new Label("", skin);
        Label waitingForPlayersLabel = new Label("WAITING FOR PLAYERS", skin);
        TextButton backButton = new TextButton("CANCEL", skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        stage.addActor(serverStartedLabel);
        stage.addActor(hostIpLabel);
        stage.addActor(waitingForPlayersLabel);
        stage.addActor(backButton);

        float numberOfObjects = 4;

        MenuLayout menuLayout = new MenuLayout(numberOfObjects);

        serverStartedLabel.setPosition(menuLayout.setX(serverStartedLabel.getWidth()), menuLayout.setY(serverStartedLabel.getHeight(), 1));
        hostIpLabel.setPosition(menuLayout.setX(hostIpLabel.getWidth()), menuLayout.setY(hostIpLabel.getHeight(), 2));
        waitingForPlayersLabel.setPosition(menuLayout.setX(waitingForPlayersLabel.getWidth()), menuLayout.setY(waitingForPlayersLabel.getHeight(), 3));
        backButton.setPosition(menuLayout.setX(backButton.getWidth()), menuLayout.setY(backButton.getHeight(), 4));
    }

    public Timer.Task scheduleUpdate(int delay) {
        Label waitingForPlayersLabel = new Label("", new Skin(Gdx.files.internal("skin/vhs-ui.json")));
        Timer.Task print = new Timer.Task() {
            @Override
            public void run() {
                waitingForPlayersLabel.setText("WAITING FOR PLAYERS.");
                waitingForPlayersLabel.setText("WAITING FOR PLAYERS..");
                waitingForPlayersLabel.setText("WAITING FOR PLAYERS...");
            }
        };
        Timer.instance().scheduleTask(print, delay);
        return print;
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
