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
    public void testWebServer() throws IOException {
        WebServer.start("localhost", 8181, "tmp");
        WebServer.scan("com.igumnov.common");
        File.writeString("123", "tmp/webserver.txt");
        assertEquals("123", URL.getAllToString("http://localhost:8181/webserver.txt"));
        WebServer.addHandler("/script", () -> {
            return "Bla-Bla";
        });

        WebServer.addRestController("/get", (method, params) -> {
            if (method.equals(WebServer.METHOD_GET)) {
                HashMap<String, String> ret = new HashMap<String, String>();
                ret.put("key1", "val1");
                ret.put("key2", "val2");
                return ret;
            }
            throw new WebServerException("Unsupported method");
        });
        assertEquals("Bla-Bla", URL.getAllToString("http://localhost:8181/script"));
        assertEquals("{\"key1\":\"val1\",\"key2\":\"val2\"}", URL.getAllToString("http://localhost:8181/get"));
        WebServer.stop();
    }
}
