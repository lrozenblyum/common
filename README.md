# common

Why?

A lot of questions on Stack Overflow have answer on 3-5 lines code. This library collect
typical cases which developers copy-past in their projects. All functions in this library covered
by JUnit test. Simply use this small library to have clean and more stable code in your project.
This library could work like Dependency Injection Framework and/or Embedded WebServer with MVC Framework.


Common Java library functions:

* Sleep/Delay/Pause
* Timer for Benchmark
* Random range generator
* File operations
* Tasks/Threads
* Reflection
* JSON
* WebServer (Static Content/CGI/Rest support)
* MVC Framework with Template Engine
* Dependency Injection Framework
* URL

Dependencies from other projects:
* Jetty - WebServer
* Jackson - JSON parser
* Thymeleaf - HTML template engine
* NekoHTML - HTML parser

Usage:

    import com.igumnov.common.*;

Maven:

    <dependency>
      <groupId>com.igumnov</groupId>
      <artifactId>common</artifactId>
      <version>2.2</version>
    </dependency>

If you do not want use webserver

      <exclusions>
                <exclusion>
                    <groupId>org.eclipse.jetty</groupId>
                    <artifactId>jetty-server</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.thymeleaf</groupId>
                    <artifactId>thymeleaf</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-simple</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>net.sourceforge.nekohtml</groupId>
                    <artifactId>nekohtml</artifactId>
                </exclusion>
      </exclusions>



Sleep

    Benchmark.timerStart();
    Time.sleepInSeconds(1.5);
    System.out.println("Sleep time: " + Benchmark.timerStop() + "ms");

Random generator

    int rndInt = Number.randomIntByRange(-10, 300);
    long rndLong = Number.randomLongByRange(-5L, -2L);
    double rndDouble = Number.randomDoubleByRange(100.5, 2222.343);

File/Folder

    File.writeString("Some text", "dir/somefile.txt");
    String fileContent = File.readAllToString("dir/somefile.txt");
    Folder.deleteWithContent"tmp/someDirForDeletion");
    Folder.copyWithContent("/src.dir","/target.dir");

Tasks

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

Benchmark

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

JSON

    SomePOJO obj = (SomePOJO) JSON.parse(jsonString, SomePOJO.class));
    String json = JSON.toString(obj);

URL

    String responseBody = URL.getAllToString("http://localhost:8181/script");

Reflection

    ArrayList<String> names = Reflection.getClassNamesFromPackage("com.your_package_name");

Embedded WebServer

    WebServer.init("localhost", 8181);
    WebServer.addStaticContentHandler("/static", "/path_to_static_content");
    WebServer.addHandler("/script", (request) -> {
        return "Bla-Bla script result";
    });


Rest controller

    WebServer.addRestController("/rest/get", (request) -> {
        if (request.getMethod().equals(WebServer.METHOD_GET)) {
                ObjectDTO objectDTO = new ObjectDTO();
                objectDTO.setVal(1);
                ...
                return objectDTO;
        }
        throw new WebServerException("Unsupported method");
    });


    WebServer.addRestController("/rest/put", ObjectDTO.class, (request, putObjectDTO) -> {
        if (request.getMethod().equals(WebServer.METHOD_PUT)) {
                putObjectDTO.getAttr();
                ...
                return putObjectDTO;
        }
        throw new WebServerException("Unsupported method");
    });



ModelViewController with TemplateEngine

    WebServer.addTemplates("/path_to_templates");
    WebServer.addController("/", (request, model) -> {
        model.put("varName", new Integer("123"));
        return "home";
    });


    home.html in "/path_to_templates" folder

    <html><body><span th:text="${varName}"></span></body><html>

WebServer start/stop

    WebServer.start();
    WebServer.stop();


Dependency Injection Framework

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



