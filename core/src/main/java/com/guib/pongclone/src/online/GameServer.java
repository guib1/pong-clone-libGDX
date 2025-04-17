package com.guib.pongclone.src.online;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class GameServer {
    private Server server;

    public GameServer() throws IOException {
        server = new Server();
        NetworkListener.register(server.getKryo());

        server.getKryo().setRegistrationRequired(false);
        server.bind(NetworkListener.PORT, NetworkListener.PORT + 1); // TCP + UDP

        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                // Envia via TCP (importante!)
                NetworkListener.PlayerConnected msg = new NetworkListener.PlayerConnected();
                msg.playerId = server.getConnections().length; // 1 ou 2
                connection.sendTCP(msg);
            }

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof NetworkListener.PaddleUpdate) {
                    NetworkListener.PaddleUpdate update = (NetworkListener.PaddleUpdate) object;
                    // Repassa para todos via UDP (rápido!)
                    server.sendToAllUDP(update);
                }
            }
        });

        server.start();
    }

    public void sendBallUpdate(float x, float y, float velX, float velY) {
        NetworkListener.BallUpdate update = new NetworkListener.BallUpdate();
        update.x = x; update.y = y; update.velX = velX; update.velY = velY;
        server.sendToAllUDP(update); // Envia via UDP
    }
}
