package com.igumnov.common;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class TaskTest {

    @Test
    public void testStartProcedure() throws InterruptedException, ExecutionException, TimeException {

        Benchmark.timerBeginByName("TaskStart");
        Benchmark.timerBeginByName("TaskProgress");
        ProcedureThread task = Task.startProcedure(() -> {
            Time.sleepInSeconds(2);
        });
        assertTrue(Benchmark.timerStopByName("TaskStart") < 0.5 * 1000);
        task.join();
        assertTrue(Benchmark.timerStopByName("TaskProgress") > 1 * 1000);

    }

    @Test
    public void testStartFunction() throws InterruptedException, ExecutionException, TimeException {
        Benchmark.timerBeginByName("TaskStart2");
        Benchmark.timerBeginByName("TaskProgress2");
        Future<Object> ret = Task.startFunction(() -> {
            Time.sleepInSeconds(2);
            return "123";
        });

        assertTrue(Benchmark.timerStopByName("TaskStart2") < 0.5 * 1000);
        assertEquals("123", ret.get());
        assertTrue(Benchmark.timerStopByName("TaskProgress2") > 1 * 1000);

    }

}