package com.igumnov.common;


public class Time {

    static long timerStartValue = 0;
    static long timerAccamulator = 0;

    public static void pauseInSeconds(double seconds) {
        try {
            Thread.sleep((long) (1000 * seconds));
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void timerStart() throws TimeException {
        if (timerStartValue != 0) {
            timerResetValues();
            throw new TimeException("timerStop() should be call before");
        }
        timerStartValue = System.currentTimeMillis();
    }

    public static long timerStop() throws TimeException {
        if (timerStartValue == 0) {
            throw new TimeException("timerStart() should be call before");
        }
        long timerStartValueOld = timerStartValue;
        long timerAccamulatorOld = timerAccamulator;
        timerResetValues();
        return System.currentTimeMillis() - timerStartValueOld + timerAccamulatorOld;
    }

    private static void timerResetValues() {
        timerStartValue = 0;
        timerAccamulator = 0;
    }

    public static long timerPause() throws TimeException {
        if (timerStartValue == 0 && timerAccamulator == 0) {
            timerResetValues();
            throw new TimeException("timerStart() should be call before");
        }
        long timerStartValueOld = timerStartValue;
        timerStartValue = 0;
        timerAccamulator += System.currentTimeMillis() - timerStartValueOld;
        return timerAccamulator;
    }

    public static void timerResume() throws TimeException {
        if (timerStartValue == 0 && timerAccamulator == 0) {
            timerResetValues();
            throw new TimeException("timerStart() should be call before");
        }
        if (timerStartValue != 0 && timerAccamulator == 0) {
            timerResetValues();
            throw new TimeException("timerPause() should be call before");
        }
        timerStartValue = System.currentTimeMillis();
    }

}