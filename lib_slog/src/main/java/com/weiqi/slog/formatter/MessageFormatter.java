package com.weiqi.slog.formatter;

import com.weiqi.slog.util.LogLevel;

/**
 * 日志数据处理
 * Created by alexwangweiqi on 17/8/31.
 */

public interface MessageFormatter {

    /**
     * 格式化 日志输出信息
     *
     * @param logLevel
     * @param tag
     * @param message
     * @param isThread
     * @param isStackTrace
     * @return
     */
    String format(LogLevel logLevel, String tag, String message, boolean isBorder, boolean
            isThread, boolean isStackTrace);

    /**
     * 格式化 日志输出信息(网络)
     *
     * @param logLevel
     * @param tag
     * @param url
     * @param params
     * @param result
     * @param isBorder
     * @return
     */
    String formatNetwork(LogLevel logLevel, String tag, String url, String params, String result,
                         boolean isBorder);
}
