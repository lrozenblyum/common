package com.igumnov.common;

public class InstructionsThread  extends Thread {

    private InstructionsInterface instructions;

    InstructionsThread(InstructionsInterface i) {
        this.instructions = i;
    }

    @Override
    public void run() {
        instructions.run();
    }

}
