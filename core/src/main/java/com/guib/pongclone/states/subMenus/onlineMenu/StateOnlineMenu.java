package com.guib.pongclone.states.subMenus.onlineMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.guib.pongclone.src.DiscordRichPresence;
import com.guib.pongclone.src.MenuLayout;
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;
import com.guib.pongclone.states.subMenus.StateSinglePlayerOptionMenu;
import com.guib.pongclone.states.subMenus.StateTwoPlayerOptionMenu;

public class StateOnlineMenu extends State {
    private final StateManager gsm;

    private Stage stage;
    private SpriteBatch batch;
    private Texture background;

    public StateOnlineMenu(StateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void create() {
        gsm.setRichPresence("On menus", "Online Mode Menu", "", false);
        batch = new SpriteBatch();
        background = new Texture("bg.jpg");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));

        TextField chooseNameField = new TextField("", skin);
        TextButton setNameButton = new TextButton("SET NAME", skin);
        TextButton createServerButton = new TextButton("CREATE SERVER", skin);
        TextButton joinServerButton = new TextButton("JOIN SERVER", skin);
        TextButton backButton = new TextButton("BACK", skin);

        setNameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(chooseNameField.getText());
            }
        });

        createServerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.push(new StateServerMenu(gsm));
            }
        });

        joinServerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.push(new StateClientMenu(gsm));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        stage.addActor(setNameButton);
        stage.addActor(chooseNameField);
        stage.addActor(createServerButton);
        stage.addActor(joinServerButton);
        stage.addActor(backButton);

        float numberOfObjects = 7;

        MenuLayout menuLayout = new MenuLayout(numberOfObjects);

        chooseNameField.setPosition(menuLayout.setX(chooseNameField.getWidth()), menuLayout.setY(chooseNameField.getHeight() - 5, 2));
        setNameButton.setPosition(menuLayout.setX(setNameButton.getWidth()), menuLayout.setY(setNameButton.getHeight(), 3));
        createServerButton.setPosition(menuLayout.setX(createServerButton.getWidth()), menuLayout.setY(createServerButton.getHeight(), 5));
        joinServerButton.setPosition(menuLayout.setX(joinServerButton.getWidth()), menuLayout.setY(joinServerButton.getHeight(), 6));
        backButton.setPosition(menuLayout.setX(backButton.getWidth()), menuLayout.setY(backButton.getHeight(), 7));
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
