# common

Common Java library.

    import com.igumnov.common.*;
    
    Benchmark.timerStart();
    Time.sleepInSeconds(1.5);
    System.out.println("Sleep time: " + Benchmark.timerStop() + "ms");


    Benchmark.timerStartByName("loop1");
    for(int i = 0; i<10000 ; ++i) {
        // do something
    }
    System.out.println("loop1 time: " + Benchmark.timerStopByName("loop1") + "ms");


    for(int i = 0; i<10000 ; ++i) {
        Benchmark.timerBeginByName("loop2");
        // do something
        Benchmark.timerEndByName("loop2");
    }
    System.out.println("loop2 total time: " + Benchmark.timerGetTotalTimeByName("loop2") + "ms");
    System.out.println("loop2 repeat count: " + Benchmark.timerGetRepeatCountByName("loop2"));
    System.out.println("loop2 average time: " + Benchmark.timerGetAverageTimeByName("loop2") + "ms");



Maven:

    <dependency>
      <groupId>com.igumnov</groupId>
      <artifactId>common</artifactId>
      <version>0.0.3</version>
    </dependency>
