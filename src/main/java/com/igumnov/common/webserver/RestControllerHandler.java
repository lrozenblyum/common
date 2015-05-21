package com.igumnov.common.webserver;

import com.igumnov.common.JSON;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class RestControllerHandler extends AbstractHandler {

    private RestControllerInterface restController;
    private RestControllerSimpleInterface restControllerSimple;
    private Class postBody;

    public RestControllerHandler(RestControllerSimpleInterface i) {
        restControllerSimple = i;
    }
    public RestControllerHandler(RestControllerInterface i,Class pb) {
        restController = i;
        postBody = pb;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {



        Object postObject = null;
        Object responseObj = null;
        int status = HttpServletResponse.SC_OK;
        if(request.getMethod().equals("POST") || request.getMethod().equals("PUT") ) {
            StringBuffer jb = new StringBuffer();
            String line = null;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null)
                    jb.append(line);
            } catch (Exception e) {
                responseObj = e;
                status = HttpServletResponse.SC_BAD_REQUEST;
            }
            postObject = JSON.parse(jb.toString(), postBody);
        }

        try {
            if(restController != null)
            {
                responseObj =  restController.response(request, postObject);
            }
            else {
                responseObj =  restControllerSimple.response(request);

            }
        } catch (WebServerException e) {
            responseObj = e;
            status = HttpServletResponse.SC_BAD_REQUEST;

        }



        String ret = JSON.toString(responseObj);

        response.setContentType("application/json; charset=utf-8");
        response.setStatus(status);

        PrintWriter out = response.getWriter();

        out.write(ret);

        baseRequest.setHandled(true);


    }
}
