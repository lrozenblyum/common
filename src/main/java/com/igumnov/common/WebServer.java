package com.igumnov.common;


import com.igumnov.common.webserver.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class WebServer {

//    final static Logger logger = Logger.getLogger(WebServer.class);

    private static TemplateEngine templateEngine;


    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";


    private static  Server server;
    private static ArrayList<Handler> handlers = new ArrayList<Handler>();
    private static String templateFolder;

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

//        logger.info("WebServer started");
    }
    public static void stop() throws Exception {
        server.stop();
//        logger.info("WebServer stoped");
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

    public static void addTemplates(String folder) {
        templateFolder = folder;
        ServletContextTemplateResolver templateResolver =
                new ServletContextTemplateResolver();
        templateResolver.setTemplateMode("LEGACYHTML5");
        templateResolver.setPrefix("/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(3600000L);
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

    }

    public static void addController(String name, ControllerInterface i) {
        ContextHandler context = new ContextHandler();
        context.setResourceBase(templateFolder);
        context.setContextPath(name);
        context.setHandler(new ControllerHandler(templateEngine,i));
        handlers.add(context);

    }
}
