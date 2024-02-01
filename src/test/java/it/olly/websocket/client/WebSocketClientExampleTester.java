package it.olly.websocket.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

// Client WebSocket
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.Test;

public class WebSocketClientExampleTester {

    class WebSocketClientExample extends WebSocketClient {

        public WebSocketClientExample(URI serverUri) {
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            System.out.println("c> connection open");
        }

        @Override
        public void onMessage(String message) {
            System.out.println("c> got message from server: " + message);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            System.out.println("c> connection closed. doce: " + code + " reason: " + reason);
        }

        @Override
        public void onError(Exception ex) {
            System.err.println("c> error: " + ex);
            ex.printStackTrace();
        }
    }

    @Test
    void testClient() throws URISyntaxException {
        WebSocketClientExample client = new WebSocketClientExample(new URI("ws://localhost:8887"));
        client.connect();

        System.out.println("type messages and RETURN to send them to server ('exit' to exit)");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = scanner.nextLine();

                if ("exit".equalsIgnoreCase(input)) {
                    client.close();
                    break;
                }

                client.send(input);
            }
        }
    }
}