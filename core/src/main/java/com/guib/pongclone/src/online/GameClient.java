package com.guib.pongclone.src.online;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class GameClient {
    private Client client;
    private int myPlayerId;

    public GameClient() {
        client = new Client(16384, 8192); // Buffer maior para UDP
        NetworkListener.register(client.getKryo());

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof NetworkListener.PlayerConnected) {
                    myPlayerId = ((NetworkListener.PlayerConnected) object).playerId;
                }
                else if (object instanceof NetworkListener.PaddleUpdate) {
                    NetworkListener.PaddleUpdate update = (NetworkListener.PaddleUpdate) object;
                    // Atualiza paddle do jogador remoto
                }
                else if (object instanceof NetworkListener.BallUpdate) {
                    NetworkListener.BallUpdate ball = (NetworkListener.BallUpdate) object;
                    // Atualiza posição da bola
                }
            }
        });

        client.start();
    }

    public void sendPaddleUpdate(float y) {
        NetworkListener.PaddleUpdate update = new NetworkListener.PaddleUpdate();
        update.playerId = myPlayerId;
        update.y = y;
        client.sendUDP(update); // Envia via UDP!
    }

    public void connect(String ip) throws IOException {
        client.connect(5000, ip, NetworkListener.PORT, NetworkListener.PORT + 1);
    }
}

