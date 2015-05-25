package com.igumnov.common.webserver;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class StringHandler extends HttpServlet {

    StringInterface stringInterface;
    public StringHandler(StringInterface i) {
        stringInterface = i;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

    }
}
