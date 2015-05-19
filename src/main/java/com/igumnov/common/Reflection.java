package com.igumnov.common;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.net.URI;

public class Reflection {


    public static ArrayList<String> getClassNamesFromPackage(String packageName) throws IOException, URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL packageURL;
        ArrayList<String> names = new ArrayList<String>();
        ;

        packageName = packageName.replace(".", "/");
        packageURL = classLoader.getResource(packageName);

        if (packageURL.getProtocol().equals("jar")) {
            String jarFileName;
            JarFile jf;
            Enumeration<JarEntry> jarEntries;
            String entryName;

            jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
            jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
            System.out.println(">" + jarFileName);
            jf = new JarFile(jarFileName);
            jarEntries = jf.entries();
            while (jarEntries.hasMoreElements()) {
                entryName = jarEntries.nextElement().getName();
                if (entryName.startsWith(packageName) && entryName.length() > packageName.length() + 5) {
                    entryName = entryName.substring(packageName.length(), entryName.lastIndexOf('.'));
                    names.add(entryName);
                }
            }

        } else {
            URI uri = new URI(packageURL.toString());
            java.io.File folder = new java.io.File(uri.getPath());
            java.io.File[] contenuti = folder.listFiles();
            String entryName;
            for (java.io.File actual : contenuti) {
                entryName = actual.getName();
                if(entryName.lastIndexOf('.') != -1) {
                    entryName = entryName.substring(0, entryName.lastIndexOf('.'));
                    names.add(packageName.replace("/", ".")+ "." +entryName);
                } else {
                  ArrayList<String> ret = getClassNamesFromPackage(packageName.replace("/", ".") + "." + entryName);
                  names.addAll(ret);
                }

            }
        }
        return names;
    }
}
