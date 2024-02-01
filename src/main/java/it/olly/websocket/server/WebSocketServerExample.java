package it.olly.websocket.server;

import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class WebSocketServerExample extends WebSocketServer {

    public WebSocketServerExample() {
        super(new InetSocketAddress(8887));
    }

    @PostConstruct
    void init() {
        start();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("s> new connection from: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("s> connection closed with client: " + conn.getRemoteSocketAddress() + " code: " + code
                + " reason: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("s> got message from client: " + conn.getRemoteSocketAddress() + " -> " + message);
        conn.send("this is the server response to [" + message.toUpperCase() + "] - " + System.currentTimeMillis());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("s> error: " + conn.getRemoteSocketAddress());
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("s> server started");
    }

}