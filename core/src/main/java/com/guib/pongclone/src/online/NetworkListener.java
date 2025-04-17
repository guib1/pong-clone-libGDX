package com.guib.pongclone.src.online;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class NetworkListener {
    public static final int PORT = 54555;

    public static void registerClasses(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(PaddleMove.class);
        kryo.register(BallUpdate.class);
        kryo.register(GameState.class);
        kryo.register(Player.class);
    }

    // Mensagens de rede
    public static class PaddleMove {
        public float y;
        public int playerId;
    }

    public static class BallUpdate {
        public float x, y;
        public float velocityX, velocityY;
    }

    public static class GameState {
        public Player player1;
        public Player player2;
        public BallUpdate ball;
    }

    public static class Player {
        public int id;
        public float paddleY;
        public int score;
    }
}
