package com.igumnov.common.webserver;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class ControllerHandler extends AbstractHandler {

    private TemplateEngine templateEngine;
    private ControllerInterface controller;
    public ControllerHandler (TemplateEngine t, ControllerInterface c) {
        templateEngine = t;
        controller = c;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        HashMap<String, Object> model = new HashMap<String, Object>();
        String templateName = null;
        int status = HttpServletResponse.SC_OK;

        try {
            templateName = controller.process(request,model);
        } catch (WebServerException e) {

            status = HttpServletResponse.SC_BAD_REQUEST;
        }

        IContext context = new ControllerContext(model, baseRequest.getServletContext());
        String ret = templateEngine.process(templateName, context);

        response.setContentType("text/html; charset=utf-8");
        response.setStatus(status);

        PrintWriter out = response.getWriter();

        out.write(ret);

        baseRequest.setHandled(true);

    }
}
