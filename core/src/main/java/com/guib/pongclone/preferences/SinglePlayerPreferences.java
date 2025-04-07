package com.guib.pongclone.preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SinglePlayerPreferences {
    private static SinglePlayerPreferences instance;

    private final Preferences singlePlayerPreferences = Gdx.app.getPreferences("SinglePlayerPreferences");
}
