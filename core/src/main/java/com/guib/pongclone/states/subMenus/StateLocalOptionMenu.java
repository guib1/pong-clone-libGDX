package com.guib.pongclone.states.subMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.guib.pongclone.src.MenuLayout;
import com.guib.pongclone.src.Musics;
import com.guib.pongclone.src.match.MatchBase;
import com.guib.pongclone.src.match.MatchBaseConfig;
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
        TextButton backButton = new TextButton("BACK", skin);

        Label labelDifficulty = new Label("SET DIFFICULTY:", skin);
        CheckBox easy = new CheckBox("EASY", skin);
        CheckBox medium = new CheckBox("MEDIUM", skin);
        CheckBox hard = new CheckBox("HARD", skin);
        ButtonGroup<CheckBox> difficulties = new ButtonGroup<>(easy, medium, hard);
        Table difficultyTable = new Table();
        difficultyTable.add(easy);
        difficultyTable.add(medium);
        difficultyTable.add(hard);

        Label labelScore = new Label("SCORE TO WIN:", skin);
        CheckBox score3 = new CheckBox("3", skin);
        CheckBox score5 = new CheckBox("5", skin);
        CheckBox score7 = new CheckBox("7", skin);
        CheckBox score9 = new CheckBox("9", skin);
        CheckBox score11 = new CheckBox("11", skin);
        CheckBox score13 = new CheckBox("13", skin);
        CheckBox score15 = new CheckBox("15", skin);
        ButtonGroup<CheckBox> scores = new ButtonGroup<>(score3, score5, score9, score11, score13, score15);
        Table scoresTable = new Table();
        scoresTable.add(score3);
        scoresTable.add(score5);
        scoresTable.add(score7);
        scoresTable.add(score9);
        scoresTable.add(score11);
        scoresTable.add(score13);
        scoresTable.add(score15);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new StateSinglePlayerMatch(new MatchBase(), new MatchBaseConfig(new MatchBase())));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pop();
            }
        });

        difficultyTable.pack();
        scoresTable.pack();

        stage.addActor(playButton);
        stage.addActor(labelScore);
        stage.addActor(scoresTable);
        stage.addActor(labelDifficulty);
        stage.addActor(difficultyTable);
        stage.addActor(backButton);

        MenuLayout menuLayout = new MenuLayout(6);

        playButton.setPosition(menuLayout.setX(playButton.getWidth()), menuLayout.setY(playButton.getHeight() - 3f, 1));
        labelDifficulty.setPosition(menuLayout.setX(labelDifficulty.getWidth()), menuLayout.setY(labelDifficulty.getHeight(), 2));
        difficultyTable.setPosition(menuLayout.setX(difficultyTable.getWidth()), menuLayout.setY(difficultyTable.getHeight(), 3));
        labelScore.setPosition(menuLayout.setX(labelScore.getWidth()), menuLayout.setY(labelScore.getHeight(), 4));
        scoresTable.setPosition(menuLayout.setX(scoresTable.getWidth()), menuLayout.setY(scoresTable.getHeight(), 5));
        backButton.setPosition(menuLayout.setX(backButton.getWidth()), menuLayout.setY(backButton.getHeight(), 6));
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
