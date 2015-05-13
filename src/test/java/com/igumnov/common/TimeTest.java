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
    public void testTimerStartPauseResumeStopByName() throws Exception {
        String name = "test";
        Time.timerStartByName(name);
        Time.pauseInSeconds(1);
        long pause1 = Time.timerPauseByName(name);
        assertTrue("1 sec", pause1 >= 1000 && pause1 <= 1500);

        Time.pauseInSeconds(0.5);
        Time.timerResumeByName(name);
        Time.pauseInSeconds(0.5);
        long pause2 = Time.timerPauseByName(name);
        assertTrue("1.5 sec", pause2 >= 1500 && pause2 <= 2000);

        Time.pauseInSeconds(1);
        Time.timerResumeByName(name);
        Time.pauseInSeconds(1);
        long pause3 = Time.timerStopByName(name);
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

    @Test
    public void testRepeatCase() throws TimeException {

        for (int i = 0; i < 100; ++i) {
            Benchmark.timerBeginByName("loop2");
            Time.sleepInSeconds(0.01);
            Benchmark.timerEndByName("loop2");
        }
        assertEquals(Benchmark.timerGetRepeatCountByName("loop2"), 100);
        assertTrue("timerGetAverageTime", (Benchmark.timerGetAverageTimeByName("loop2")) < Benchmark.timerGetTotalTimeByName("loop2") * 0.1);
        assertTrue("timerGetTotalTime", (Benchmark.timerGetAverageTimeByName("loop2")) * 100 * 0.9 < Benchmark.timerGetTotalTimeByName("loop2"));
        assertTrue("timerGetTotalTime", (Benchmark.timerGetAverageTimeByName("loop2")) * 100 * 1.1 > Benchmark.timerGetTotalTimeByName("loop2"));

    }


}