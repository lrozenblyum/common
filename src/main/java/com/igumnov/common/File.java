package com.igumnov.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class File {
    public static String readAllToStringByFileName(String fileName) throws IOException {
        java.io.File file = new java.io.File(fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        return new String(data, "UTF-8");
    }

}
