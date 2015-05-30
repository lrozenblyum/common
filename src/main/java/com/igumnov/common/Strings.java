package com.igumnov.common;

import java.util.stream.Stream;

public class Strings {
    public static Stream<Character> stream(String s) {
        return s.chars().mapToObj(i -> (char) i);
    }
}
