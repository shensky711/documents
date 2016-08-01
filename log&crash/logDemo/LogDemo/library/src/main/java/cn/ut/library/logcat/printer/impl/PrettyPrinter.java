package cn.ut.library.logcat.printer.impl;

import android.util.Log;

import cn.ut.library.bean.MemoryState;
import cn.ut.library.logcat.bean.AdvancedLogInfo;
import cn.ut.library.bean.LogLevel;
import cn.ut.library.logcat.printer.Printer;
import cn.ut.library.utils.StringUtils;


/**
 * Created by chenhang on 2016/6/1.
 */
public class PrettyPrinter implements Printer {

    /**
     * Android's max limit for a log entry is ~4076 bytes, so 4000 bytes is used as chunk size since default charset is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    /**
     * Drawing toolbox
     */
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    private static PrettyPrinter sInstance;

    private PrettyPrinter() {
    }

    public static PrettyPrinter getInstance() {
        if (sInstance == null) {
            synchronized (PrettyPrinter.class) {
                if (sInstance == null) {
                    sInstance = new PrettyPrinter();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void print(AdvancedLogInfo logInfo) {
        doPrint(logInfo);
    }

    /**
     * This method is synchronized in order to avoid messy of logs' order.
     */
    private synchronized void doPrint(AdvancedLogInfo logInfo) {

        LogLevel logLevel = logInfo.getLevel();
        String tag = logInfo.getTag();

        printTopBorder(logLevel, tag);
        printTitle(logLevel, tag, logInfo.getTitle());
        printThreadName(logLevel, tag, logInfo.getThreadName());
        printThrowable(logLevel, tag, logInfo.getThrowable());
        printStackTrace(logLevel, tag, logInfo.getStackTrace());
        printMemory(logLevel, tag, logInfo.getMemoryInfo());
        printMessage(logLevel, tag, logInfo.getMessage());
        printBottomBorder(logLevel, tag);
    }

    private void printTopBorder(LogLevel logLevel, String tag) {
        printChunk(logLevel, tag, TOP_BORDER);
    }

    private void printThreadName(LogLevel logLevel, String tag, String threadName) {
        if (!StringUtils.isEmpty(threadName)) {
            printChunk(logLevel, tag, HORIZONTAL_DOUBLE_LINE + " Thread: " + threadName);
            printDivider(logLevel, tag);
        }
    }

    private void printTitle(LogLevel logLevel, String tag, String title) {
        if (!StringUtils.isEmpty(title)) {
            printChunk(logLevel, tag, HORIZONTAL_DOUBLE_LINE + " " + title);
            printDivider(logLevel, tag);
        }
    }

    private void printThrowable(LogLevel logLevel, String tag, Throwable throwable) {
        if (throwable != null) {
            printChunk(logLevel, tag, HORIZONTAL_DOUBLE_LINE + " Throwable: " + throwable.toString());
            printDivider(logLevel, tag);
        }
    }

    private void printStackTrace(LogLevel logLevel, String tag, StackTraceElement[] trace) {

        if (trace == null || trace.length <= 0) {
            return;
        }

        //corresponding method count with the current stack may exceeds the stack trace. Trims the count
        String level = "";
        for (int i = trace.length - 1; i >= 0; i--) {

            StringBuilder builder = new StringBuilder();
            builder.append("║ ")
                    .append(level)
                    .append(getSimpleClassName(trace[i].getClassName()))
                    .append(".")
                    .append(trace[i].getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(trace[i].getFileName())
                    .append(":")
                    .append(trace[i].getLineNumber())
                    .append(")");
            level += "   ";
            printChunk(logLevel, tag, builder.toString());
        }

        printDivider(logLevel, tag);
    }

    private void printMemory(LogLevel logLevel, String tag, MemoryState memoryState) {
        if (memoryState != null) {
            printChunk(logLevel, tag, HORIZONTAL_DOUBLE_LINE + " " + memoryState.toString());
            printDivider(logLevel, tag);
        }
    }

    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    private void printMessage(LogLevel logLevel, String tag, String message) {
        if (!StringUtils.isEmpty(message)) {
            //get bytes of message with system's default charset (which is UTF-8 for Android)
            byte[] bytes = message.getBytes();
            int length = bytes.length;
            if (length <= CHUNK_SIZE) {
                printContent(logLevel, tag, message);
            } else {
                for (int i = 0; i < length; i += CHUNK_SIZE) {
                    int count = Math.min(length - i, CHUNK_SIZE);
                    //create a new String with system's default charset (which is UTF-8 for Android)
                    printContent(logLevel, tag, new String(bytes, i, count));
                }
            }
        }
    }

    private void printBottomBorder(LogLevel logLevel, String tag) {
        printChunk(logLevel, tag, BOTTOM_BORDER);
    }

    private void printDivider(LogLevel logLevel, String tag) {
        printChunk(logLevel, tag, MIDDLE_BORDER);
    }

    private void printContent(LogLevel logLevel, String tag, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        for (String line : lines) {
            printChunk(logLevel, tag, HORIZONTAL_DOUBLE_LINE + " " + line);
        }
    }

    private void printChunk(LogLevel logLevel, String tag, String chunk) {

        if (StringUtils.isEmpty(tag) || StringUtils.isEmpty(chunk)) {
            return;
        }
        
        switch (logLevel) {
            case ERROR:
                Log.e(tag, chunk);
                break;
            case INFO:
                Log.i(tag, chunk);
                break;
            case VERBOSE:
                Log.v(tag, chunk);
                break;
            case WARN:
                Log.w(tag, chunk);
                break;
            case ASSERT:
                Log.wtf(tag, chunk);
                break;
            case DEBUG:
                // Fall through, log debug by default
            default:
                Log.d(tag, chunk);
                break;
        }
    }
}
