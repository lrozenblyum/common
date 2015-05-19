# common

Why?

A lot of questions on Stack Overflow have answer on 3-5 lines code. This library collect
typical cases which developers copy-past in their projects. All functions in this library covered
by JUnit test. Simply use this small library to have clean and more stable code in your project.
This library could work like Dependency Injection Framework the same. Library size:  ~20Kb


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

    File.writeString("Some text", "dir/somefile.txt");
    String fileContent = File.readAllToString("dir/somefile.txt");
    Folder.deleteWithContent"tmp/someDirForDeletion");
    Folder.copyWithContent("/src.dir","/target.dir");

    Future<?> task = Task.startProcedure(() -> {
        // Long time procedure code there
    });
    // Do something
    if(!task.isDone()) {
       task.get(); // Wait for done
    }

    Task.setThreadPoolSize(2); // Default value: 10
    Future<Object> taskResult = Task.startFunction(() -> {
        // Long time function code there
        return resultObject;
    });
    // Do something
    Object result = taskResult.get();

    Benchmark.timerStart("loop1");
    for(int i = 0; i<10000 ; ++i) {
        // do something
    }
    System.out.println("loop1 time: " + Benchmark.timerStop("loop1") + "ms");

    Benchmark.timerStart("pausedTimer");
    // do something
    Benchmark.timerPause("pausedTimer");
    // do something
    Benchmark.timerResume("pausedTimer");
    // do something
    System.out.println("pausedTimer time: " + Benchmark.timerStop("pausedTimer") + "ms");

    for(int i = 0; i<10000 ; ++i) {
        Benchmark.timerBegin("loop2");
        // do something
        Benchmark.timerEnd("loop2");
    }
    System.out.println("loop2 total time: " + Benchmark.timerGetTotalTime("loop2") + "ms");
    System.out.println("loop2 repeat count: " + Benchmark.timerGetRepeatCount("loop2"));
    System.out.println("loop2 average time: " + Benchmark.timerGetAverageTime("loop2") + "ms");

    Timer = new Timer();
    timer.start();
    //do something
    System.out.println("Object Timer time: " + timer.stop() + "ms");


Declaration instance

    @Named("nameInstance")
    public class SomeClass {
        ...
    }


Dependency injection in field

    public class OtherClass {

        @Inject("nameInstance")
        private SomeClass someClass;

        ...

    }

Get instance from context

    SomeClass objInstance = (SomeClass) Dependency.findInstance("newInstance");

Initialization of the Dependency Injection Framework

    // scans classes of the specified package recursively for Annotations:
    Dependency.scan("com.yourpackage.app");

    // performing the injection of dependencies of a class (transitively):
    OtherClass otherClassInstance = new OtherClass();
    Dependency.inject(otherClassInstance);

    // put instance to context
    Dependency.createInstance("otherInstanse", SomeClass.class);



Maven:

    <dependency>
      <groupId>com.igumnov</groupId>
      <artifactId>common</artifactId>
      <version>1.2</version>
    </dependency>
