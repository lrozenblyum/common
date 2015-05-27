package com.igumnov.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Strings {

    static Stream<Character> stream(String s) {
        int len = s.length();
        Character[] chars = new Character[len];
        for (int i = 0; i < len ; i++) {
            chars[i] = new Character(s.charAt(i));
        }
        ArrayList<Character> line = new ArrayList<Character>(Arrays.asList(chars));
        return line.stream();
    }
}
