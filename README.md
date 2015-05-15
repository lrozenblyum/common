# common

Why?

A lot of questions on Stack Overflow have answer on 3-5 lines code. This library collect
typical cases which developers copy-past in their projects. All functions in this library covered
by JUnit test. Simply use this small library to have clean and more stable code in your project.


Common Java library:

* Sleep/Delay/Pause
* Timer for Benchmark
* Random range generator
* File operations
* Tasks/Threads


Usage:

    import com.igumnov.common.*;
    
    Benchmark.timerStart();
    Time.sleepInSeconds(1.5);
    System.out.println("Sleep time: " + Benchmark.timerStop() + "ms");

    int rndInt = Number.randomIntByRange(-10, 300);
    long rndLong = Number.randomLongByRange(-5L, -2L);
    double rndDouble = Number.randomDoubleByRange(100.5, 2222.343);

    String fileContent = File.readAllToStringByFileName("dir/somefile.txt");

    ProcedureThread task = Task.startProcedure(() -> {
        // Long time procedure code there
    });
    // Do something
    if(!task.isDone()) {
       task.join(); // Wait for done
    }

    Future<Object> taskResult = Task.startFunction(() -> {
        // Long time function code there
        return resultObject;
    });
    // Do something
    Object result = taskResult.get();

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



Maven:

    <dependency>
      <groupId>com.igumnov</groupId>
      <artifactId>common</artifactId>
      <version>0.0.7</version>
    </dependency>
