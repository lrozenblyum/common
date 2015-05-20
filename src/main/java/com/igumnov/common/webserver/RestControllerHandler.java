package com.igumnov.common.webserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class RestControllerHandler implements HttpHandler {

    private RestControllerInterface restController;

    public RestControllerHandler(RestControllerInterface i) {
        restController = i;
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Map<String, Object> params =
                (Map<String, Object>)httpExchange.getAttribute("parameters");

        String method = httpExchange.getRequestMethod();
        Object responseObj = null;
        try {
            responseObj =  restController.response(method,params);
        } catch (WebServerException e) {
            responseObj = e;
        }

        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mapper.writeValue(baos, responseObj);
        String response = new String(baos.toByteArray());
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}
