package com.igumnov.common;


class Timer {
    private long timerStartValue = 0;
    private long timerAccamulator = 0;
    private Integer lock = new Integer(0);

    public void timerStart() throws TimeException {
        synchronized (lock) {
            if (timerStartValue != 0) {
                timerResetValues();
                throw new TimeException("timerStop should be call before");
            }
            timerStartValue = System.currentTimeMillis();
        }
    }

    public long timerStop() throws TimeException {
        synchronized (lock) {
            if (timerStartValue == 0) {
                throw new TimeException("timerStart should be call before");
            }
            long timerStartValueOld = timerStartValue;
            long timerAccamulatorOld = timerAccamulator;
            timerResetValues();
            return System.currentTimeMillis() - timerStartValueOld + timerAccamulatorOld;
        }
    }

    private void timerResetValues() {
        timerStartValue = 0;
        timerAccamulator = 0;
    }

    public long timerPause() throws TimeException {
        synchronized (lock) {

            if (timerStartValue == 0 && timerAccamulator == 0) {
                timerResetValues();
                throw new TimeException("timerStart should be call before");
            }
            long timerStartValueOld = timerStartValue;
            timerStartValue = 0;
            timerAccamulator += System.currentTimeMillis() - timerStartValueOld;
            return timerAccamulator;
        }
    }

    public void timerResume() throws TimeException {
        synchronized (lock) {
            if (timerStartValue == 0 && timerAccamulator == 0) {
                throw new TimeException("timerStart should be call before");
            }
            if (timerStartValue != 0 && timerAccamulator == 0) {
                throw new TimeException("timerPause should be call before");
            }
            timerStartValue = System.currentTimeMillis();
        }
    }


}