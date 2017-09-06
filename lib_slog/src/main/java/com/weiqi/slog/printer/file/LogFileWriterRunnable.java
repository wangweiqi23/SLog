package com.weiqi.slog.printer.file;

import com.weiqi.slog.mode.LogPacket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 日志写入文件线程
 * Created by alexwangweiqi on 17/8/27.
 */
public class LogFileWriterRunnable implements Runnable {

    private static final int IDLE_TIME = 200;

    private BlockingQueue<LogPacket> mLogs = new LinkedBlockingQueue<>();

    private LogFileHelper mLogFileHelper;

    private volatile boolean mIsRunning = true;

    public LogFileWriterRunnable(LogFileHelper writer) {
        mLogFileHelper = writer;
    }

    /**
     * 添加日志到队列
     */
    public void enqueue(LogPacket log) {
        try {
            mLogs.put(log);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void finish() {
        this.mIsRunning = false;
        enqueue(createPoisonObject());
    }

    @Override
    public void run() {
        LogPacket log;
        try {
            while (mIsRunning) {
                if ((log = mLogs.take()) != null) {
                    if (log.getType() == LogPacket.TYPE_INTERRUPT) {
                        return;
                    }
                    boolean isWritered = mLogFileHelper.doPrintln(log);
                } else {
                    Thread.sleep(IDLE_TIME);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
            this.mIsRunning = false;
        }

    }

    private LogPacket createPoisonObject() {
        return new LogPacket(LogPacket.TYPE_INTERRUPT);
    }
}