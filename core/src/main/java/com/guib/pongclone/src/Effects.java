package com.guib.pongclone.src;

public class Effects {
    private float a = 0.0f;
    private float b = 1.0f;

    public float fadeOut() {
        this.b -= 0.1f;
        if (this.a <=  0.0f) {
            this.a = 0.0f;
        }
        return this.b;
    }

    public float fadeIn() {
        this.a += 0.1f;
        if (this.a >=  1.0f) {
            this.a = 1.0f;
        }
        return this.a;
    }
}
