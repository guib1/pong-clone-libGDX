package com.guib.pongclone.src.match;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class MatchBaseConfig {
    private final MatchBase match;

    // only for testing, it will be put at (StateLocalOptionMenu)
    public boolean chooseSide = true;

    public float PLAYER_SPEED;

    public MatchBaseConfig(MatchBase match) {
        this.match = match;
    }

    public void setPLAYER_SPEED(float playerSpeed){
        this.PLAYER_SPEED = playerSpeed;
    }

    public void singlePlayerChooseSide(boolean side) {
        if (side) {
            match.player1.rect = new Rectangle(50, match.player1.getY() + match.pCenterY, 20, 70);
            match.bot.rect = new Rectangle(Gdx.graphics.getWidth() - 70, match.bot.getY() + match.pCenterY, 20, 70);

            match.shape.rect(match.player1.rect.x, match.player1.rect.y, match.player1.rect.width, match.player1.rect.height);
            match.shape.rect(match.bot.rect.x, match.bot.rect.y, match.bot.rect.width, match.bot.rect.height);
        } else {
            match.player1.rect = new Rectangle(Gdx.graphics.getWidth() - 70, match.player1.getY() + match.pCenterY, 20, 70);
            match.bot.rect = new Rectangle(50, match.bot.getY() + match.pCenterY, 20, 70);

            match.shape.rect(match.player1.rect.x, match.player1.rect.y, match.player1.rect.width, match.player1.rect.height);
            match.shape.rect(match.bot.rect.x, match.bot.rect.y, match.bot.rect.width, match.bot.rect.height);
        }
    }

    /*
    public void chooseControl() {
        int up = Gdx.input.isButtonPressed(Input.Keys.);
    }

    public void chooseControl(int up, int down) {
        if (Gdx.input.isKeyPressed(Input.Keys.)) {
            match.player1.movement(match.PLAYER_SPEED * match.player1.update());
        } else if (Gdx.input.isKeyPressed(down)) {
            match.player1.movement(-match.PLAYER_SPEED * match.player1.update());
        }
    }
     */
}
