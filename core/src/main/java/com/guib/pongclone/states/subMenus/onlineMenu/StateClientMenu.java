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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.guib.pongclone.src.MenuLayout;
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;

public class StateClientMenu extends State {
    private StateManager gsm;

    private Stage stage;
    private SpriteBatch batch;
    private Texture background;

    public StateClientMenu(StateManager gsm) {
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

        Label ipLabel = new Label("INSERT HOST IP:", skin);
        TextField ipField = new TextField("", skin);
        TextButton connectButton = new TextButton("CONNECT", skin);
        TextButton backButton = new TextButton("CANCEL", skin);

        TextField.TextFieldStyle style = new TextField.TextFieldStyle(skin.get(TextField.TextFieldStyle.class));
        style.font.getData().setScale(0.5f);
        ipField.setStyle(style);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        stage.addActor(ipLabel);
        stage.addActor(ipField);
        stage.addActor(connectButton);
        stage.addActor(backButton);

        float numberOfObjects = 5;

        MenuLayout menuLayout = new MenuLayout(numberOfObjects);

        ipField.setPosition(menuLayout.setX(ipField.getWidth()), menuLayout.setY(ipField.getHeight() - 5, 1));
        ipLabel.setPosition(menuLayout.setX(ipLabel.getWidth()), menuLayout.setY(ipLabel.getHeight(), 2));
        connectButton.setPosition(menuLayout.setX(connectButton.getWidth()), menuLayout.setY(connectButton.getHeight(), 4));
        backButton.setPosition(menuLayout.setX(backButton.getWidth()), menuLayout.setY(backButton.getHeight(), 5));
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

    }
}
