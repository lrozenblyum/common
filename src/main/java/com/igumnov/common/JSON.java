package com.igumnov.common;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

// TODO add test
public class JSON {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String toString(Object o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mapper.writeValue(baos, o);
        return new String(baos.toByteArray());
    }

    public static  Object parse(String src, Class c) throws IOException {
        return mapper.readValue(src, c);
    }


}
