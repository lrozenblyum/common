package com.igumnov.common;

@Named("inject")
public class InjectClass {

    @Inject("inject")
    private InjectClass injectTest;


    private String retVal = "1";

    private static InjectClass singlenton;

    InjectClass() {
        singlenton = this;
    }


    public String ret () {
        return retVal;
    }




    public static String  test() {
        return singlenton.injectTest.ret();
    }

}
