package com.guib.pongclone.states;

import com.guib.pongclone.src.DiscordRichPresence;

import java.time.Instant;
import java.util.Stack;

public class StateManager {
    private final DiscordRichPresence discordRichPresence = new DiscordRichPresence();
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

    public void create() {
        if (!states.empty()) {
            states.peek().create();
        }
        discordRichPresence.setGameId();
    }

    public void render() {
        if (!states.empty()) {
            states.peek().render();
        }
        if (discordRichPresence.core != null) {
            discordRichPresence.core.runCallbacks();
        }
        if (!discordRichPresence.running) {
            discordRichPresence.initDiscordRichPresence();
        }
    }

    // Discord rich presence implementation
    public void setRichPresence(String details, String details2, String smallImage, Boolean haveImage) {
        if (!discordRichPresence.running || discordRichPresence.core == null) return;

        new Thread(() -> {
            try {
                discordRichPresence.activity.setDetails(details + " | " + details2);
                discordRichPresence.activity.assets().setLargeImage("logo-principal");
                discordRichPresence.activity.assets().setLargeText("PONG");
                if (haveImage) {
                    discordRichPresence.activity.assets().setSmallImage(smallImage);
                } else {
                    discordRichPresence.activity.assets().setSmallImage(null);
                }
                discordRichPresence.core.activityManager().updateActivity(discordRichPresence.activity);
            } catch (RuntimeException e) {
                System.err.println("Error: " + e.getMessage());
                discordRichPresence.running = false;
            }
        }, "RPC-Update-Thread").start();
    }

    public void startRichPresenceTimeStamp() {
        discordRichPresence.activity.timestamps().setStart(Instant.now());
    }

    public void dispose() {
        while (!states.isEmpty()) {
            states.pop().dispose();
        }
        if (discordRichPresence.core != null) {
            discordRichPresence.core.close();
        }
    }

    public boolean isEmpty() {
        return states.isEmpty();
    }
}
