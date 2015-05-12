# common

Common Java library.

    import com.igumnov.common.*;
    
    Time.timerStart();
    Time.sleepInSeconds(1.5);
    System.out.println("Sleep time: " + Time.timerStop() + "ms");

    Time.timerStartByName("loop1");
    for(int i = 0; i<10000 ; ++i) {
        // do something
    }
    System.out.println("loop1 time: " + Time.timerStopByName("loop1") + "ms");

