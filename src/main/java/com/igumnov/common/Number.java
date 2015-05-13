package com.igumnov.common;


import java.util.Random;

public class Number {
    private static Random randomGenerator = new Random();

    public static int randomIntByRange(int fromValue, int toValue) {

        int bound = toValue - fromValue;
        return randomGenerator.nextInt(Math.abs(bound)) + fromValue;

    }


    public static double randomDoubleByRange(double fromValue, double toValue) {
        double bound = toValue - fromValue;
        return randomGenerator.nextDouble() * Math.abs(bound) + fromValue;
    }

    public static long randomLongByRange(long fromValue, long toValue) {
        long bound = toValue - fromValue;
        return (long) (randomGenerator.nextDouble() * Math.abs(bound)) + fromValue;
    }
}
