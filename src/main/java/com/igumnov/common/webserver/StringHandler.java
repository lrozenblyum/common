package com.igumnov.common.webserver;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class StringHandler  implements HttpHandler {

    StringInterface stringInterface;
    public StringHandler(StringInterface i) {
        stringInterface = i;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = stringInterface.response();
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}
