package com.guib.pongclone.states;

import java.util.Stack;

public class StateManager {

    private final Stack<State> states;

    public StateManager() {
        states = new Stack<State>();
    }

    public void push(State state) {
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
