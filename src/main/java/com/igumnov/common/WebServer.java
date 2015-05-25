package com.igumnov.common;


import com.igumnov.common.webserver.*;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.ArrayList;

public class WebServer {


    private static TemplateEngine templateEngine;


    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";


    private static  Server server;
    private static ArrayList<Handler> handlers = new ArrayList<Handler>();
    private static String templateFolder;
    private static ServerConnector connector;
    private static ServerConnector https;
    private WebServer() {

    }


    public static void init(String hostName, int port) {

        server = new Server();

        connector=new ServerConnector(server);
        connector.setHost(hostName);
        connector.setPort(port);





    }

    public static void https(int port, String keystoreFile, String storePassword, String managerPassword) {
        HttpConfiguration http_config = new HttpConfiguration();
        http_config.setSecureScheme("https");
        http_config.setSecurePort(port);

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(keystoreFile);
        sslContextFactory.setKeyStorePassword(storePassword);
        sslContextFactory.setKeyManagerPassword(managerPassword);

        HttpConfiguration https_config = new HttpConfiguration(http_config);
        https_config.addCustomizer(new SecureRequestCustomizer());

        https = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
                new HttpConnectionFactory(https_config));
        https.setPort(port);
    }

    public static void start() throws Exception {
        if(https == null) {
            server.setConnectors(new Connector[]{connector});
        } else {
            server.setConnectors(new Connector[]{connector,https});
        }

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
