package com.igumnov.common.webserver;


import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class StringHandler extends AbstractHandler {

    StringInterface stringInterface;
    public StringHandler(StringInterface i) {
        stringInterface = i;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int status = HttpServletResponse.SC_OK;
        String ret = null;
        try {
            ret = stringInterface.response(request);
        } catch (WebServerException e) {
            ret = e.getMessage();
            status = HttpServletResponse.SC_BAD_REQUEST;
        }

        response.setContentType("text/html; charset=utf-8");
        response.setStatus(status);

        PrintWriter out = response.getWriter();

        out.write(ret);

        baseRequest.setHandled(true);
    }
}
