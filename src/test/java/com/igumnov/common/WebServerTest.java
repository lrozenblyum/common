package com.igumnov.common;


import java.io.IOException;

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
        WebServer.start("localhost", 8080, "tmp");
        WebServer.scan("com.igumnov.common");
        File.writeString("123", "tmp/webserver.txt");
        assertEquals("123", URL.getAllToString("http://localhost:8080/webserver.txt"));
        WebServer.addHandler("/script", () -> {
            return "Bla-Bla";
        });
        assertEquals("Bla-Bla", URL.getAllToString("http://localhost:8080/script"));
        WebServer.stop();
    }
}
