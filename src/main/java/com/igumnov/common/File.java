package com.igumnov.common;

import java.io.*;

public class File {
    public static String readAllToStringByFileName(String fileName) throws IOException {
        java.io.File file = new java.io.File(fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        return new String(data, "UTF-8");
    }

    public static void writeStringByFileName(String str, String fileName) throws IOException {
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "UTF-8"));
        try {
            out.write(str);
        } finally {
            out.close();
        }
    }

    public static void deleteDirectoryWithContentByDirName(String dirName) {
        java.io.File folder = new java.io.File(dirName);
        java.io.File[] files = folder.listFiles();
        if(files!=null) {
            for(java.io.File f: files) {
                if(f.isDirectory()) {
                    deleteDirectoryWithContentByDirName(f.getPath());
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
}
