package com.igumnov.common;


import com.igumnov.common.webserver.*;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";
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

    public static void addRestController(String name, RestControllerInterface i) {
        HttpContext context = server.createContext(name, new RestControllerHandler(i));
        context.getFilters().add(new ParameterFilter());

    }
}
