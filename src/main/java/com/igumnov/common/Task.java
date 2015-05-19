package com.igumnov.common;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Task {

    private static ExecutorService executor =  Executors.newFixedThreadPool(10);
    private static int poolSize=10;

    public static Future<?>  startProcedure(Runnable procedure) {
        Future<?> future = executor.submit(procedure);
        return future;
    }


    public static Future<Object> startFunction(Callable<Object> function) {
        Future<Object> future = executor.submit(function);
        return future;
    }

    public static void setThreadPoolSize(int size) {
        if(poolSize != size) {
            executor = Executors.newFixedThreadPool(size);
            poolSize = size;
        }
    }
}
