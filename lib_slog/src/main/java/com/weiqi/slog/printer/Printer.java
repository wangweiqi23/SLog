/*
 * Copyright JiongBull 2016
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


import com.weiqi.slog.formatter.MessageFormatter;
import com.weiqi.slog.util.LogLevel;

import java.util.List;

/**
 * 打印接口.
 */
public interface Printer {

    /**
     * 普通日志
     *
     * @param logLevel
     * @param tag
     * @param msg
     * @param isBorder
     * @param isThread
     * @param isStackTrace
     * @param isSync
     * @param fileSubffix
     */
    void println(LogLevel logLevel, String tag, String msg, boolean isBorder, boolean isThread,
                 boolean isStackTrace, boolean isSync, String fileSubffix);

    /**
     * 网络日志
     *
     * @param logLevel
     * @param tag
     * @param url
     * @param params
     * @param result
     * @param isBorder
     * @param isSync
     * @param fileSubffix
     */
    void printNetwork(LogLevel logLevel, String tag, String url, String params, String result,
                      boolean isBorder, boolean isSync, String fileSubffix);

    void addLevelForFile(List<LogLevel> logLevelsForFile);

    void setFormatter(MessageFormatter mFormatter);

    void onDestroy();

}
