package com.igumnov.common;


public class Time extends Benchmark {


    public static void delayInSeconds(double seconds) {
        pauseInSeconds(seconds);
    }

    public static void sleepInSeconds(double seconds) {
        pauseInSeconds(seconds);
    }

    public static void pauseInSeconds(double seconds) {
        try {
            Thread.sleep((long) (1000 * seconds));
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


}