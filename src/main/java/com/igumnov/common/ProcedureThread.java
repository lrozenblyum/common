package com.igumnov.common;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ProcedureThread extends Thread {

    private ProcedureInterface instructions;
    private boolean done = false;

    ProcedureThread(ProcedureInterface i) {
        this.instructions = i;
    }

    @Override
    public void run() {
        done = true;
        instructions.run();
        done = false;
    }

    public boolean isDone() {
        return done;
    }

}
