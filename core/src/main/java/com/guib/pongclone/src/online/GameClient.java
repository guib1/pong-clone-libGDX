package com.guib.pongclone.src.online;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class GameClient {
    private Client client;

    public GameClient() throws IOException {
        client = new Client();
        client.start();
        client.connect(5000, "127.0.0.1", 54555, 54777);
        client.sendTCP("teste");

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof String) {
                    // Handle keep-alive message
                } else {
                    // Handle other messages
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {
        new GameClient();
    }
}
