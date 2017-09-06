/*
 * Copyright 2016 Elvis Hew
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


import android.util.Log;

import com.weiqi.slog.formatter.MessageFormatter;
import com.weiqi.slog.util.LogLevel;

import java.util.List;

import static com.weiqi.slog.SLogConstants.MAX_LENGTH_OF_SINGLE_MESSAGE;

/**
 * 控制台输出日志
 * <p>
 * Created by alexwangweiqi on 17/8/27.
 */
public class DefaultConsolePrinter implements Printer {

    /**
     * 日志信息格式化工具.
     */
    private MessageFormatter mMessageFormatter;

    @Override
    public void println(LogLevel logLevel, String tag, String msg, boolean isBorder,
                        boolean isThread, boolean isStackTrace, boolean isSync, String
                                fileSubffix) {

        if (mMessageFormatter != null) {
            msg = mMessageFormatter.format(logLevel, tag, msg, isBorder, isThread, isStackTrace);
        }

        onHandlePrint(logLevel, tag, msg);
    }

    @Override
    public void printNetwork(LogLevel logLevel, String tag, String url, String params, String
            result, boolean isBorder, boolean isSync, String fileSubffix) {
        if (mMessageFormatter != null) {
            result = mMessageFormatter.formatNetwork(logLevel, tag, url, params, result, isBorder);
        }

        onHandlePrint(logLevel, tag, result);
    }

    private void onHandlePrint(LogLevel logLevel, String tag, String msg) {
        if (msg.length() <= MAX_LENGTH_OF_SINGLE_MESSAGE) {
            printLine(logLevel, tag, msg);
            return;
        }

        int msgLength = msg.length();
        int start = 0;
        int end = start + MAX_LENGTH_OF_SINGLE_MESSAGE;
        while (start < msgLength) {
            printLine(logLevel, tag, msg.substring(start, end));

            start = end;
            end = Math.min(start + MAX_LENGTH_OF_SINGLE_MESSAGE, msgLength);
        }
    }

    /**
     * 使用LogCat输出日志.
     *
     * @param level 级别
     * @param tag   标签
     * @param msg   信息
     */
    private void printLine(LogLevel level, String tag, String msg) {
        switch (level) {
            case VERBOSE:
                Log.v(tag, msg);
                break;
            case DEBUG:
                Log.d(tag, msg);
                break;
            case INFO:
                Log.i(tag, msg);
                break;
            case WARN:
                Log.w(tag, msg);
                break;
            case ERROR:
                Log.e(tag, msg);
                break;
            case WTF:
                Log.wtf(tag, msg);
                break;
            case NETWORK:
                Log.d(tag, msg);
            default:
                break;
        }
    }

    @Override
    public void addLevelForFile(List<LogLevel> logLevelsForFile) {
    }

    @Override
    public void setFormatter(MessageFormatter mFormatter) {
        this.mMessageFormatter = mFormatter;
    }

    @Override
    public void onDestroy() {
    }
}
