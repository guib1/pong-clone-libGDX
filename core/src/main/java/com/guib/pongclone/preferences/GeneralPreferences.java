package com.guib.pongclone.preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GeneralPreferences {
    private static GeneralPreferences instance;

    private final Preferences generalPreferences = Gdx.app.getPreferences("GeneralSettings");

    private float musicVolume;
    private float playerSpeed;
    private float score;

    private GeneralPreferences(){
        load();
    }

    public static GeneralPreferences getInstance() {
        if (instance == null) {
            instance = new GeneralPreferences();
        }
        return instance;
    }

    public void load(){
        this.musicVolume = generalPreferences.getFloat("musicVolume", 0.5f); // default volume
        this.playerSpeed = generalPreferences.getFloat("playerSpeed", 500f); // medium speed
        this.score = generalPreferences.getFloat("score", 11); // default score is the same of table tennis scoreToWin
    }

    public void save(){
        generalPreferences.putFloat("musicVolume", musicVolume);
        generalPreferences.flush();
    }

    public float getMusicVolume(){
        return this.musicVolume;
    }

    public void setMusicVolume(float musicVolume){
        this.musicVolume = musicVolume;
        save();
    }

    public float getPlayerSpeed(){
        return this.playerSpeed;
    }

    public void setPlayerSpeed(float playerSpeed){
        this.playerSpeed = playerSpeed;
        save();
    }

    public float getScore(){
        return this.score;
    }

    public void setScore(float score){
        this.score = score;
        save();
    }
}
