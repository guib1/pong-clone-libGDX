package com.guib.pongclone.src;

import com.badlogic.gdx.utils.Timer;
import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;

public class DiscordRichPresence {
    public Core core;
    public Activity activity = new Activity();
    CreateParams params;

    public boolean running = false;
    private boolean switcher = false;

    public void setGameId() {
        params = new CreateParams();
        params.setClientID(1359676774857834728L); // Discord developers application clientID
        params.setFlags(CreateParams.getDefaultFlags());
    }

    public void initDiscordRichPresence() {
        // Loading Discord Rich Presence
        try {
            core = new Core(params);
            this.running = true;
        } catch (Exception e) {
            while (!switcher) {
                rpcExceptionDelay(e);
            }
        }
    }

    public void rpcExceptionDelay(Exception e) {
        switcher = true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                running = false;
                System.err.println("Error at starting Discord Rich Presence: " + e.getMessage());
                switcher = false;
            }
        }, 3);
    }
}
