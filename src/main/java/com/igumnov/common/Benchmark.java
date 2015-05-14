package com.igumnov.common;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Benchmark {

    private static ReentrantReadWriteLock timersLock = new ReentrantReadWriteLock();
    private static HashMap<String, Timer> timers = new HashMap<String, Timer>();
    protected static final String timerDefaultName = "com.igumnov.common.timer";

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

            timer.start();

        } catch (TimeException e) {
            throw e;
        } finally {
            timersLock.writeLock().unlock();
        }

    }

    public static long timerPauseByName(String name) throws TimeException {
        Timer timer;
        try {
            timersLock.readLock().lock();
            if (timers.containsKey(name)) {
                timer = timers.get(name);
            } else {
                throw new TimeException("startProcedure should be call before");
            }
            return timer.pause();
        } catch (TimeException e) {
            throw e;
        } finally {
            timersLock.readLock().unlock();
        }
    }

    public static void timerResumeByName(String name) throws TimeException {
        Timer timer;
        try {
            timersLock.readLock().lock();
            if (timers.containsKey(name)) {
                timer = timers.get(name);
            } else {
                throw new TimeException("startProcedure should be call before");
            }
            timer.resume();

        } catch (TimeException e) {
            throw e;
        } finally {
            timersLock.readLock().unlock();
        }

    }

    public static long timerStopByName(String name) throws TimeException {
        Timer timer;
        try {
            timersLock.writeLock().lock();
            if (timers.containsKey(name)) {
                timer = timers.get(name);
            } else {
                throw new TimeException("startProcedure should be call before");
            }

            long ret = timer.stop();
            timers.remove(name);
            return ret;

        } catch (TimeException e) {
            throw e;
        } finally {
            timersLock.writeLock().unlock();
        }
    }

    public static void timerStart() throws TimeException {
        timerStartByName(Benchmark.timerDefaultName);
    }

    public static long timerStop() throws TimeException {
        return timerStopByName(Benchmark.timerDefaultName);
    }


    public static long timerPause() throws TimeException {
        return timerPauseByName(Benchmark.timerDefaultName);
    }

    public static void timerResume() throws TimeException {
        timerResumeByName(Benchmark.timerDefaultName);
    }


    public static void timerBeginByName(String name) throws TimeException {
        Timer timer;
        try {
            timersLock.writeLock().lock();
            if (timers.containsKey(name)) {
                timer = timers.get(name);
                timer.resume();
            } else {
                timer = new Timer();
                timers.put(name, timer);
                timer.start();
            }
        } catch (TimeException e) {
            throw e;
        } finally {
            timersLock.writeLock().unlock();
        }

    }

    public static long timerEndByName(String name) throws TimeException {
        return timerPauseByName(name);
    }

    public static long timerGetRepeatCountByName(String name) throws TimeException {

        Timer timer;
        try {
            timersLock.readLock().lock();
            if (timers.containsKey(name)) {
                timer = timers.get(name);
            } else {
                throw new TimeException("startProcedure should be call before");
            }
            return timer.getRepeatCount();

        } catch (TimeException e) {
            throw e;
        } finally {
            timersLock.readLock().unlock();
        }
    }

    public static long timerGetAverageTimeByName(String name) throws TimeException {
        Timer timer;
        try {
            timersLock.readLock().lock();
            if (timers.containsKey(name)) {
                timer = timers.get(name);
            } else {
                throw new TimeException("startProcedure should be call before");
            }
            return timer.getAverageTime();

        } catch (TimeException e) {
            throw e;
        } finally {
            timersLock.readLock().unlock();
        }
    }

    public static long timerGetTotalTimeByName(String name) throws TimeException {
        Timer timer;
        try {
            timersLock.readLock().lock();
            if (timers.containsKey(name)) {
                timer = timers.get(name);
            } else {
                throw new TimeException("startProcedure should be call before");
            }
            return timer.getTotlaTime();

        } catch (TimeException e) {
            throw e;
        } finally {
            timersLock.readLock().unlock();
        }
    }
}
