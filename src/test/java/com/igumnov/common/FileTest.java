package com.igumnov.common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class FileTest {

    @Before
    public void before() {
        java.io.File d = new java.io.File("tmp");
        if (!d.exists()) {
            d.mkdir();
        }
    }

    @Test
    public void testReadAllToStringByFileName() throws IOException {
        assertEquals(File.readAllToStringByFileName("src/test/resources/File/readAllToStringByFileName.txt"), "123");
    }

    @Test
    public void testWriteStringByFileName() throws IOException {
        File.writeStringByFileName("123\n123", "tmp/testWriteStringByFileName.txt");
        assertEquals(File.readAllToStringByFileName("tmp/testWriteStringByFileName.txt"), "123\n123");
    }


    @Test
    public void testDeleteDirectoryWithContentByDirName() throws IOException {
        new java.io.File("tmp/deleteDir").mkdir();
        File.writeStringByFileName("tmp/deleteDir/1.txt", "123");
        new java.io.File("tmp/deleteDir/sub").mkdir();
        File.writeStringByFileName("tmp/deleteDir/sub/2.txt", "123");
        File.deleteDirectoryWithContentByDirName("tmp/deleteDir");
        assertTrue(!new java.io.File("tmp/deleteDir").exists());
    }

}