package com.igumnov.common;


import java.util.HashMap;
import java.util.Map;

import com.igumnov.common.webserver.WebServerException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WebServerTest {


    @Before
    public void before() {
        java.io.File d = new java.io.File("tmp");
        if (!d.exists()) {
            d.mkdir();
        } else {
            Folder.deleteWithContent("tmp");
            d.mkdir();
        }
    }

    @Test
    public void testWebServer() throws Exception {


        WebServer.init("localhost", 8181);
        //WebServer.https(8282, "src/test/resources/key.jks", "storepwd", "keypwd");
        WebServer.security("/*", "/login");

        WebServer.addStaticContentHandler("/static", "tmp");
        File.writeString("123", "tmp/webserver.txt");

        WebServer.addHandler("/script", (request) -> {
            return "Bla-Bla";
        });

        WebServer.addHandler("/new", (request) -> {
            return "new new";
        });

        WebServer.allow("/*");
        WebServer.restrict("/new", new String[]{"user"});

        WebServer.addHandler("/login", (request) -> {
            return "<form method='POST' action='/j_security_check'>"
                    + "<input type='text' name='j_username'/>"
                    + "<input type='password' name='j_password'/>"
                    + "<input type='submit' value='Login'/></form>";
        });

        WebServer.addRestController("/get", (request) -> {

            if (request.getMethod().equals(WebServer.METHOD_GET)) {
                HashMap<String, String> ret = new HashMap<String, String>();
                ret.put("key1", "val1");
                ret.put("key2", "val2");
                return ret;
            }
            throw new WebServerException("Unsupported method");


        });

        WebServer.addRestController("/put", ObjectDTO.class, (request, postObject) -> {

            if (request.getMethod().equals(WebServer.METHOD_PUT)) {
                return postObject;
            }
            throw new WebServerException("Unsupported method");


        });

        WebServer.addTemplates("tmp");
        File.writeString("<html><body><span th:text=\"${varName}\"></span></body><html>", "tmp/example.html");
        WebServer.addController("/index", (request, model) -> {
            model.put("varName", new Integer("123"));
            return "example";
        });

        WebServer.start();
        assertEquals("123", URL.getAllToString("http://localhost:8181/static/webserver.txt"));
        assertEquals("Bla-Bla", URL.getAllToString("http://localhost:8181/script"));
        assertEquals("{\"key1\":\"val1\",\"key2\":\"val2\"}", URL.getAllToString("http://localhost:8181/get"));
        ObjectDTO o = new ObjectDTO();
        o.setName("a");
        o.setSalary(1);
        assertEquals(JSON.toString(o), URL.getAllToString("http://localhost:8181/put", WebServer.METHOD_PUT, null, JSON.toString(o)));

        assertEquals("<html><head></head><body><span>123</span></body></html>", URL.getAllToString("http://localhost:8181/index"));
        assertNotEquals("new new", URL.getAllToString("http://localhost:8181/new"));

        WebServer.stop();
    }
}
