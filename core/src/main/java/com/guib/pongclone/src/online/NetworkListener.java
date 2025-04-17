package com.guib.pongclone.src.online;

import com.esotericsoftware.kryo.Kryo;
import com.guib.pongclone.src.MatchBase;

public class NetworkListener {
    public static final int PORT = 54555;

    public static void register(Kryo kryo) {
        kryo.register(PaddleUpdate.class);
        kryo.register(BallUpdate.class);
        kryo.register(PlayerConnected.class);
        kryo.register(Score.class);
        kryo.register(GameStart.class);
    }

    // udp
    public static class PaddleUpdate {
        public int playerId;
        public float y;
        // Construtor vazio obrigatório
        public PaddleUpdate() {}
    }

    public static class BallUpdate {
        public float x, y, velX, velY;
        public BallUpdate() {}
    }

    // tcp
    public static class PlayerConnected {
        public int playerId; // 1 ou 2
        public PlayerConnected() {}
    }

    public static class GameStart {
        public boolean start;
        public GameStart() {}
    }

    public static class Score {
        public MatchBase match;
        public Score() {}
    }
}
