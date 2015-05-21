package com.igumnov.common;


import com.igumnov.common.webserver.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class WebServer {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";


    private static  Server server;
    private static ArrayList<Handler> handlers = new ArrayList<Handler>();

    private WebServer() {

    }


    public static void init(String hostName, int port) {

        server = new Server(InetSocketAddress.createUnresolved(hostName,port));

    }


    public static void start() throws Exception {
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        Handler list[] = new Handler[handlers.size()];
        list = handlers.toArray(list);
        contexts.setHandlers(list);
        server.setHandler(contexts);
        server.start();
    }
    public static void stop() throws Exception {
        server.stop();
    }

    public static void addHandler(String name, StringInterface i) {

        ContextHandler context = new ContextHandler();
        context.setContextPath(name);
        context.setHandler(new StringHandler(i));
        handlers.add(context);
    }

    public static void addRestController(String name, Class c, RestControllerInterface i) {

        ContextHandler context = new ContextHandler();
        context.setContextPath(name);
        context.setHandler(new RestControllerHandler(i, c));
        handlers.add(context);

    }

    public static void addRestController(String name, RestControllerSimpleInterface i) {

        ContextHandler context = new ContextHandler();
        context.setContextPath(name);
        context.setHandler(new RestControllerHandler(i));
        handlers.add(context);

    }


    public static void addStaticContentHandler(String name, String folder) {
        ContextHandler context = new ContextHandler();
        context.setContextPath("/");
        ResourceHandler rh = new ResourceHandler();
        rh.setDirectoriesListed(true);
        rh.setResourceBase(folder);
        context.setHandler(rh);
        handlers.add(context);
    }
}
