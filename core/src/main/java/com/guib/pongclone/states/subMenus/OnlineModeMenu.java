package com.guib.pongclone.states.subMenus;

import com.guib.pongclone.modules.Musics;
import com.guib.pongclone.states.State;
import com.guib.pongclone.states.StateManager;

public class OnlineModeMenu extends State {
    private final StateManager gsm;
    private final Musics music;

    public OnlineModeMenu(StateManager gsm, Musics music) {
        this.gsm = gsm;
        this.music = music;
    }

    @Override
    public void create() {
        
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }
}
