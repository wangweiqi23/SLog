/*
 * Copyright 2015 Elvis Hew
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weiqi.slog.printer;


import com.weiqi.slog.cache.CacheHelper;
import com.weiqi.slog.cache.LogCacheHelper;
import com.weiqi.slog.formatter.MessageFormatter;
import com.weiqi.slog.mode.LogPacket;
import com.weiqi.slog.printer.file.LogFileHelper;
import com.weiqi.slog.printer.file.LogFileWriterRunnable;
import com.weiqi.slog.util.LogLevel;

import java.util.List;


/**
 * 日志文件打印机
 * <p>
 * Created by alexwangweiqi on 17/8/27.
 */
public class DefaultFilePrinter implements Printer {

    /**
     * 日志保存的目录.
     */
    private String mLogDir;

    /**
     * 日志写入本地文件.
     */
    private LogFileHelper mLogFileHelper;

    private LogFileWriterRunnable mWriterRunnable;

    /**
     * 写入文件的日志级别.
     */
    private List<LogLevel> mLogLevelsForFile;

    /**
     * 日志信息格式化工具.
     */
    private MessageFormatter mMessageFormatter;

    public DefaultFilePrinter(String logDir) {
        CacheHelper cacheHelper = new LogCacheHelper(logDir, 30);
        init(logDir, cacheHelper);
    }

    public DefaultFilePrinter(String logDir, CacheHelper cacheHelper) {
        init(logDir, cacheHelper);
    }

    private void init(String logDir, CacheHelper cacheHelper) {
        mLogDir = logDir;
        mLogFileHelper = new LogFileHelper(mLogDir, cacheHelper);
    }

    @Override
    public void println(LogLevel logLevel, String tag, String msg, boolean isBorder,
                        boolean isThread, boolean isStackTrace, boolean isSync, String
                                fileSubffix) {
        if (mLogLevelsForFile != null && !mLogLevelsForFile.contains(logLevel)) {
            return;
        }

        if (mMessageFormatter != null) {
            msg = mMessageFormatter.format(logLevel, tag, msg, isBorder, isThread, isStackTrace);
        }

        onHandlePrint(msg, isSync, fileSubffix);
    }

    @Override
    public void printNetwork(LogLevel logLevel, String tag, String url, String params, String
            result, boolean isBorder, boolean isSync, String fileSubffix) {
        if (mLogLevelsForFile != null && !mLogLevelsForFile.contains(logLevel)) {
            return;
        }

        if (mMessageFormatter != null) {
            result = mMessageFormatter.formatNetwork(logLevel, tag, url, params, result, isBorder);
        }

        onHandlePrint(result, isSync, fileSubffix);
    }

    private void onHandlePrint(String msg, boolean isSync, String fileSubffix) {
        LogPacket logPacket = new LogPacket(msg, fileSubffix);
        if (!isSync) {
            if (mWriterRunnable == null) {
                synchronized (this) {
                    LogFileWriterRunnable temp = mWriterRunnable;
                    if (temp == null) {
                        temp = new LogFileWriterRunnable(mLogFileHelper);
                        new Thread(temp).start();
                        mWriterRunnable = temp;
                    }
                }
            }
            mWriterRunnable.enqueue(logPacket);
        } else {
            mLogFileHelper.doPrintln(logPacket);
        }
    }

    @Override
    public void addLevelForFile(List<LogLevel> logLevelsForFile) {
        this.mLogLevelsForFile = logLevelsForFile;
    }

    @Override
    public void setFormatter(MessageFormatter mFormatter) {
        this.mMessageFormatter = mFormatter;
    }

    @Override
    public void onDestroy() {
        if (mWriterRunnable != null) {
            mWriterRunnable.finish();
            mWriterRunnable = null;
        }

        if (mLogFileHelper != null) {
            mLogFileHelper.onDestroy();
            mLogFileHelper = null;
        }
    }

}
