package com.igumnov.common;


import com.igumnov.common.webserver.FileHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


public class WebServer {

    private WebServer() {

    }


    public static void start(String hostName, int port, String folder, int poolSize) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new FileHandler(folder));
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static void scan(String prefix) {
    }

    public static void stop() {
    }
}
