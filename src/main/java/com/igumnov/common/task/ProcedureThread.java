package com.igumnov.common.task;

public class ProcedureThread extends Thread {

    private ProcedureInterface instructions;
    private boolean done = false;

    public ProcedureThread(ProcedureInterface i) {
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
