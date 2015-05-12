package com.igumnov.common;


import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Time {

    private static ReentrantReadWriteLock timersLock = new ReentrantReadWriteLock();
    private static HashMap<String, Timer> timers = new HashMap<String, Timer>();
    private static final String timerDefaultName = "com.igumnov.common.timer";

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

    public static void timerStart() throws TimeException {
        timerStartByName(timerDefaultName);
    }

    public static long timerStop() throws TimeException {
        return timerStopByName(timerDefaultName);
    }


    public static long timerPause() throws TimeException {
        return timerPauseByName(timerDefaultName);
    }

    public static void timerResume() throws TimeException {
        timerResumeByName(timerDefaultName);
    }

    public static void timerStartByName(String name) throws TimeException {

        Timer timer;
        try {
            timersLock.writeLock().lock();
            if (timers.containsKey(name)) {
                timers.get(name);
                throw new TimeException("timer already started");
            } else {
                timer = new Timer();
                timers.put(name, timer);
            }


        } catch (TimeException e) {
            throw e;
        } finally {
            timersLock.writeLock().unlock();
        }
        timer.timerStart();

    }

    public static long timerPauseByName(String name) throws TimeException {
        Timer timer;
        try {
            timersLock.readLock().lock();
            if (timers.containsKey(name)) {
                timer = timers.get(name);
            } else {
                throw new TimeException("timerStart should be call before");
            }
        } catch (TimeException e) {
            throw e;
        } finally {
            timersLock.readLock().unlock();
        }
        return timer.timerPause();
    }

    public static void timerResumeByName(String name) throws TimeException {
        Timer timer;
        try {
            timersLock.readLock().lock();
            if (timers.containsKey(name)) {
                timer = timers.get(name);
            } else {
                throw new TimeException("timerStart should be call before");
            }

        } catch (TimeException e) {
            throw e;
        } finally {
            timersLock.readLock().unlock();
        }
        timer.timerResume();

    }

    public static long timerStopByName(String name) throws TimeException {
        Timer timer;
        try {
            timersLock.writeLock().lock();
            if (timers.containsKey(name)) {
                timer = timers.get(name);
            } else {
                throw new TimeException("timerStart should be call before");
            }

            long ret = timer.timerStop();
            timers.remove(name);
            return ret;

        } catch (TimeException e) {
            throw e;
        } finally {
            timersLock.writeLock().unlock();
        }
    }


}