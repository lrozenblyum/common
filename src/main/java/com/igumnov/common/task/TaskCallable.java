package com.igumnov.common.task;

import java.util.concurrent.Callable;

public class TaskCallable implements Callable<Object> {

    private FunctionInterface function = null;
    private ProcedureInterface instructions = null;
    public TaskCallable(FunctionInterface f) {
        this.function = f;
    }
    TaskCallable(ProcedureInterface i) {
        this.instructions = i;
    }
    @Override
    public Object call() throws Exception {
        if(function != null) {
            return function.exec();
        } else {
            instructions.run();
            return new Object();
        }
    }
}
