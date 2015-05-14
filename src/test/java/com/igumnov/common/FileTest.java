package com.igumnov.common;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;

public class FileTest {

    @Test
    public void testRandom() throws IOException {
        assertEquals(File.readAllToStringByFileName("src/test/resources/File/readAllToStringByFileName.txt"),"123");
    }
}