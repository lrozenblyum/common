package com.igumnov.common.webserver;


import com.igumnov.common.File;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class FileHandler implements HttpHandler {

    private String f;
    public FileHandler(String folder)  {
        f=folder;
    }
    @Override
    public void handle(com.sun.net.httpserver.HttpExchange exchange) throws IOException {
        String response = File.readAllToString(f + exchange.getRequestURI().getPath());
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}
