package com.igumnov.common;


import java.io.IOException;
import java.util.HashMap;

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


        WebServer.addStaticContentHandler("/", "tmp");
        File.writeString("123", "tmp/webserver.txt");

        WebServer.addHandler("/script", (request) -> {
            return "Bla-Bla";
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


        WebServer.start();
        assertEquals("123", URL.getAllToString("http://localhost:8181/webserver.txt"));
        assertEquals("Bla-Bla", URL.getAllToString("http://localhost:8181/script"));
        assertEquals("{\"key1\":\"val1\",\"key2\":\"val2\"}", URL.getAllToString("http://localhost:8181/get"));
        ObjectDTO o = new ObjectDTO();
        o.setName("a");
        o.setSalary(1);
        assertEquals(JSON.toString(o), URL.getAllToString("http://localhost:8181/put", WebServer.METHOD_PUT, null, JSON.toString(o)));

        WebServer.stop();
    }
}
