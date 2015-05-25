package com.igumnov.common;

import java.io.IOException;
import java.util.Date;

public class Log {
    public static final int TRACE = 0;
    public static final int DEBUG = 1;
    public static final int WARN = 2;
    public static final int INFO = 3;
    public static final int ERROR = 4;
    private static int logLevel = 3;
    private static boolean stdout = true;
    private static String file = null;

    public static void setLogLevel(int l) {
        logLevel = l;
    }

    public static void disableStdout() {
        stdout = false;
    }

    public static void file(String fileName) {
        file = fileName;
    }

    public static void info(String message) {
        if (logLevel <= INFO) {
            out(message);
        }
    }

    public static void error(String message) {
        if (logLevel <= ERROR) {
            out(message);
        }
    }

    public static void trace(String message) {
        if (logLevel <= TRACE) {
            out(message);
        }
    }

    public static void debug(String message) {
        if (logLevel <= DEBUG) {
            out(message);
        }

    }

    public static void warn(String message) {
        if (logLevel <= WARN) {
            out(message);
        }
    }

    public static void info(String message, Exception e) {
        if (logLevel <= INFO) {
            out(message + " " + Reflection.stackTraceToString(e));
        }
    }

    public static void error(String message, Exception e) {
        if (logLevel <= ERROR) {
            out(message + " " + Reflection.stackTraceToString(e));
        }
    }

    public static void trace(String message, Exception e) {
        if (logLevel <= TRACE) {
            out(message + " " + Reflection.stackTraceToString(e));
        }
    }

    public static void debug(String message, Exception e) {
        if (logLevel <= DEBUG) {
            out(message + " " + Reflection.stackTraceToString(e));
        }

    }

    public static void warn(String message, Exception e) {
        if (logLevel <= WARN) {
            out(message + " " + Reflection.stackTraceToString(e));
        }
    }

    private static void out(String message) {
        String line = new Date() + " " + message;
        if (stdout) {
            System.out.println(line);
        }

        if (file != null) {
            try {
                File.appendLine(line, file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
