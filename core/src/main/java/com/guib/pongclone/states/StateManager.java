package com.guib.pongclone.states;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import de.jcm.discordgamesdk.activity.ActivityButton;
import de.jcm.discordgamesdk.activity.ActivityButtonsMode;

import java.time.Instant;
import java.util.Stack;

public class StateManager {
    private final Stack<State> states;
    private Core core;
    private Activity activity = new Activity();

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

    // Discord rich presence implementation
    public void setRichPresence(String details, String details2, String smallImage, Boolean haveImage) {

        activity.setDetails(details + " | " + details2);
        activity.assets().setLargeImage("logo-principal");
        activity.assets().setLargeText("PONG");
        if (haveImage) {
            activity.assets().setSmallImage(smallImage);
        } else {
            activity.assets().setSmallImage(null);
        }
        core.activityManager().updateActivity(activity);
    }

    public void startRichPresenceTimeStamp() {
        activity.timestamps().setStart(Instant.now());
    }

    public void render() {
        if (!states.empty()) {
            states.peek().render();
        }
        if (core != null) {
            core.runCallbacks();
        }
    }

    public void create() {
        if (!states.empty()) {
            states.peek().create();
        }

        // Loading Discord Rich Presence
        try (CreateParams params = new CreateParams()) {
            params.setClientID(1359676774857834728L); // Discord developers application clientID
            params.setFlags(CreateParams.getDefaultFlags());
            core = new Core(params);
        } catch (Exception e) {
            System.err.println("Erro ao iniciar Discord Rich Presence: " + e.getMessage());
        }
    }

    public void dispose() {
        while (!states.isEmpty()) {
            states.pop().dispose();
        }
        if (core != null) {
            core.close();
        }
    }

    public boolean isEmpty() {
        return states.isEmpty();
    }
}
