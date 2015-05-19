package com.igumnov.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class URL {
    public static String getAllToString(String url) throws IOException {

        java.net.URL urlObj = new java.net.URL(url);
        StringBuffer ret = new StringBuffer();
        InputStreamReader stream = new InputStreamReader(urlObj.openStream(), "UTF-8");
        try  {
            BufferedReader reader = new BufferedReader(stream);
            //TODO Replace readLine
            for (String line; (line = reader.readLine()) != null;) {
                if(ret.length() > 0) {
                    ret.append("\r\n");
                }
                ret.append(line);
            }
        } finally {
            stream.close();
        }
        return ret.toString();

    }
}
