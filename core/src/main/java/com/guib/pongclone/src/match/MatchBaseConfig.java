package com.guib.pongclone.src.match;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class MatchBaseConfig {
    private final MatchBase match;

    public boolean chooseSide = true;

    public MatchBaseConfig(MatchBase match) {
        this.match = match;
    }

    public void singlePlayerChooseSide(boolean side) {
        if (side) {
            match.player1.rect = new Rectangle(50, match.player1.getY() + match.pCenterY, 20, 70);
            match.shape.rect(match.player1.rect.x, match.player1.rect.y, match.player1.rect.width, match.player1.rect.height);
        } else {
            match.player2.rect = new Rectangle(Gdx.graphics.getWidth() - 70, match.player2.getY() + match.pCenterY, 20, 70);
            match.shape.rect(match.player2.rect.x, match.player2.rect.y, match.player2.rect.width, match.player2.rect.height);
        }
    }
}
