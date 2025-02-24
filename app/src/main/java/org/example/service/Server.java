package org.example.service;

import com.sun.net.httpserver.HttpServer;
import org.example.controller.api.ContactController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

public class Server {
    private static final int PORT = 8080;
    private final HttpServer server;

    public Server() throws IOException, SQLException {
        server = HttpServer.create(new InetSocketAddress(8080), 0);
        ContactController contactController = new ContactController();
        server.createContext("/contacts", contactController::handle);
        server.setExecutor(null);
    }

    public void start() {
        server.start();
        System.out.println("Server started");
    }

    public void stop() {
        server.stop(0);
    }
}
