package com.guib.pongclone.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guib.pongclone.src.Effects;

import java.util.Stack;

public class StateManager {
    private final Texture blackScreen;
    private final SpriteBatch batch = new SpriteBatch();
    private final Effects effect = new Effects();

    private final Stack<State> states;

    public StateManager() {
        states = new Stack<State>();
        blackScreen = new Texture(Gdx.files.internal("black_screen.png"));
    }

    public void push(State state) {
        /*batch.begin();
        batch.draw(blackScreen, 0, 0);
        batch.setColor(0.8f, 0.8f, 0.8f, 1);
        batch.end();*/
        states.push(state);
        states.peek().create();
    }

    public void pop() {
        if (!states.empty()) {
            states.pop().dispose();
            states.peek().create();
        }
    }

    public void set(State state) {
        if (!states.empty()) {
            states.pop().dispose();
        }
        states.push(state);
        states.peek().create();
    }

    public void render() {
        if (!states.empty()) {
            states.peek().render();
        }
    }

    public void create() {
        if (!states.empty()) {
            states.peek().create();
        }
    }

    public void dispose() {
        while (!states.isEmpty()) {
            states.pop().dispose();
        }
    }

    public boolean isEmpty() {
        return states.isEmpty();
    }
}
