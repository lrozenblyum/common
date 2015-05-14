package com.igumnov.common;

import static org.junit.Assert.*;

import org.junit.Test;


public class TaskTest {

    @Test
    public void test() throws InterruptedException {
        Thread task = Task.start(()-> System.out.println("123"));
        task.join();

        Thread task2 = Task.startWithTimeOut(()-> {
            while(true) {}
        });
        task2.join();

    }
}