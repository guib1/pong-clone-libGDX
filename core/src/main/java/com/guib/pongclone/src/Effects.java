package com.guib.pongclone.src;

public class Effects {
    private float a = 0.0f;

    public void fadeOut() {
    }

    public float fading() {
        this.a += 0.001f;
        if (this.a >=  1.0f) {
            this.a = 1.0f;
        }
        return this.a;
    }
}
