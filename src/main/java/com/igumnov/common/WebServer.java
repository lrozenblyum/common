package com.igumnov.common;


import com.igumnov.common.webserver.FileHandler;
import com.igumnov.common.webserver.StringHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.igumnov.common.webserver.StringInterface;

public class WebServer {

    private static  HttpServer server;
    private WebServer() {

    }


    public static void start(String hostName, int port, String folder) throws IOException {

        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new FileHandler(folder));
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static void scan(String prefix) {
    }

    public static void stop() {
        server.stop(0);
    }

    public static void addHandler(String name, StringInterface i) {
        server.createContext(name, new StringHandler(i));
    }
}
