# common

Common Java library:

* Sleep/Delay/Pause
* Timer for Benchmark
* Random range generator

Usage:

    import com.igumnov.common.*;
    
    Benchmark.timerStart();
    Time.sleepInSeconds(1.5);
    System.out.println("Sleep time: " + Benchmark.timerStop() + "ms");

    Benchmark.timerStartByName("loop1");
    for(int i = 0; i<10000 ; ++i) {
        // do something
    }
    System.out.println("loop1 time: " + Benchmark.timerStopByName("loop1") + "ms");

    Benchmark.timerStartByName("pausedTimer");
    // do something
    Benchmark.timerPauseByName("pausedTimer");
    // do something
    Benchmark.timerResumeByName("pausedTimer");
    // do something
    System.out.println("pausedTimer time: " + Benchmark.timerStopByName("pausedTimer") + "ms");

    for(int i = 0; i<10000 ; ++i) {
        Benchmark.timerBeginByName("loop2");
        // do something
        Benchmark.timerEndByName("loop2");
    }
    System.out.println("loop2 total time: " + Benchmark.timerGetTotalTimeByName("loop2") + "ms");
    System.out.println("loop2 repeat count: " + Benchmark.timerGetRepeatCountByName("loop2"));
    System.out.println("loop2 average time: " + Benchmark.timerGetAverageTimeByName("loop2") + "ms");

    Timer = new Timer();
    timer.start();
    //do something
    System.out.println("Object Timer time: " + timer.stop() + "ms");

    int rndInt = Number.randomIntByRange(-10, 300);
    long rndLong = Number.randomLongByRange(-5L, -2L);
    double rndDouble = Number.randomDoubleByRange(100.5, 2222.343);



Maven:

    <dependency>
      <groupId>com.igumnov</groupId>
      <artifactId>common</artifactId>
      <version>0.0.5</version>
    </dependency>
