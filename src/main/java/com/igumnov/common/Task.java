package com.igumnov.common;

public class Task {
    public static Thread start(InstructionsInterface instructions) {
        Thread thread = new InstructionsThread(instructions);
        thread.start();
        return thread;
    }

    public static Thread startWithTimeOut(InstructionsInterface instructions) {


        return null;
    }
}
