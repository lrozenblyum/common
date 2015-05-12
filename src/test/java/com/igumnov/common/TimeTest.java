package com.igumnov.common;


import static org.junit.Assert.*;

import org.junit.Test;

public class TimeTest {
    @Test
    public void testPauseInSeconds() throws TimeException {
        Time.timerStart();
        Time.pauseInSeconds(1);
        long pause1 = Time.timerStop();
        assertTrue(pause1 >= 1000 && pause1 <= 1500);

        Time.timerStart();
        Time.pauseInSeconds(0.5);
        long pause2 = Time.timerStop();
        assertTrue(pause2 >= 500 && pause2 <= 1000);

    }

    @Test
    public void testTimerStartPauseResumeStop() throws Exception {
        Time.timerStart();
        Time.pauseInSeconds(1);
        long pause1 = Time.timerPause();
        assertTrue("1 sec", pause1 >= 1000 && pause1 <= 1500);

        Time.pauseInSeconds(0.5);
        Time.timerResume();
        Time.pauseInSeconds(0.5);
        long pause2 = Time.timerPause();
        assertTrue("1.5 sec", pause2 >= 1500 && pause2 <= 2000);

        Time.pauseInSeconds(1);
        Time.timerResume();
        Time.pauseInSeconds(1);
        long pause3 = Time.timerStop();
        assertTrue("2.5 sec", pause3 >= 2500 && pause3 <= 3000);


    }

    @Test
    public void testTimerStop() throws Exception {
        try {
            Time.timerStop();
        } catch (Exception e) {
            assertTrue(true);
        }
    }


    @Test
    public void testTimerPause() throws Exception {
        try {
            Time.timerPause();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testTimerResume() throws Exception {
        try {
            Time.timerResume();
        } catch (Exception e) {
            assertTrue(true);
        }
    }


    @Test
    public void testTimerStartResume() throws Exception {
        try {
            Time.timerStart();
            Time.timerResume();
        } catch (Exception e) {
            assertTrue(true);
        }
    }


}