package com.igumnov.common;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class File {
    public static String readAllToString(String fileName) throws IOException {
        java.io.File file = new java.io.File(fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        return new String(data, "UTF-8");
    }

    public static void writeString(String str, String fileName) throws IOException {
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "UTF-8"));
        try {
            out.write(str);
        } finally {
            out.close();
        }
    }

}
