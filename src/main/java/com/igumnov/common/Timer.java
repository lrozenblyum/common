package com.igumnov.common;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Timer {

    //TODO change to nanoseconds
    private long startValue = 0;
    private long accamulator = 0;
    private long repeatCount = 0;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void timerStart() throws TimeException {
        try {
            lock.writeLock().lock();
            if (startValue != 0) {
                timerResetValues();
                throw new TimeException("timerStop should be call before");
            }
            startValue = System.currentTimeMillis();
            repeatCount++;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public long timerStop() throws TimeException {
        try {
            lock.writeLock().lock();

            if (startValue == 0) {
                throw new TimeException("timerStart should be call before");
            }
            long timerStartValueOld = startValue;
            long timerAccamulatorOld = accamulator;
            timerResetValues();
            return System.currentTimeMillis() - timerStartValueOld + timerAccamulatorOld;
        } finally {
            lock.writeLock().unlock();
        }

    }

    private void timerResetValues() {
        try {
            lock.writeLock().lock();
            startValue = 0;
            accamulator = 0;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public long timerPause() throws TimeException {
        try {
            lock.writeLock().lock();
            if (startValue == 0 && accamulator == 0) {
                timerResetValues();
                throw new TimeException("timerStart should be call before");
            }
            long timerStartValueOld = startValue;
            startValue = 0;
            accamulator += System.currentTimeMillis() - timerStartValueOld;
            return accamulator;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void timerResume() throws TimeException {
        try {
            lock.writeLock().lock();
            if (startValue == 0 && accamulator == 0) {
                throw new TimeException("timerStart should be call before");
            }
            if (startValue != 0 && accamulator == 0) {
                throw new TimeException("timerPause should be call before");
            }
            startValue = System.currentTimeMillis();
            repeatCount++;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public long getRepeatCount() {
        try {
            lock.readLock().lock();
            return repeatCount;
        } finally {
            lock.readLock().unlock();
        }
    }
    public long getAccamulator() {
        try {
            lock.readLock().lock();
            return accamulator;
        } finally {
            lock.readLock().unlock();
        }
    }

    public long getAverageTime() {
        try {
            lock.readLock().lock();
            return accamulator / repeatCount;
        } finally {
            lock.readLock().unlock();
        }
    }

}