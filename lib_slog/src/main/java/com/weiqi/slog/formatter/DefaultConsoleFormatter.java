package com.weiqi.slog.formatter;

import android.text.TextUtils;

import com.weiqi.slog.SLogConstants;
import com.weiqi.slog.util.LogLevel;
import com.weiqi.slog.util.PrinterUtils;

/**
 * 组装输出控制台的日志信息
 * Created by alexwangweiqi on 17/8/31.
 */

public class DefaultConsoleFormatter implements MessageFormatter {

    /**
     * 日志框格式工具.
     */
    private DefaultBorderFormatter mBorderFormatter;

    /**
     * 日志堆栈跟踪信息深度. 默认打印所以栈信息
     */
    private int mStackTraceLevel = -1;

    /**
     * 是否自动数据Json格式化
     */
    private boolean mIsAutoJson = false;

    public DefaultConsoleFormatter(int stackTraceLevel, boolean isAutoJson) {
        this.mStackTraceLevel = stackTraceLevel;
        this.mIsAutoJson = isAutoJson;
    }

    @Override
    public String format(LogLevel logLevel, String tag, String message, boolean isBorder,
                         boolean isThread, boolean isStackTrace) {

        return decorateMsgForConsole(JsonMessage(message), isThread ? PrinterUtils.getThreadInfo
                (Thread.currentThread()) : null, isStackTrace ? PrinterUtils.getElementFromStackStr
                (mStackTraceLevel) : null, isBorder);
    }


    @Override
    public String formatNetwork(LogLevel logLevel, String tag, String url, String params, String
            result, boolean isBorder) {
        return decorateMsgForConsole(JsonMessage(result), url, params, isBorder);
    }


    /**
     * 装饰打印到控制台的信息.
     *
     * @param message 信息
     * @param element 堆栈跟踪元素
     * @return 装饰后的信息
     */
    private String decorateMsgForConsole(String message, String threadInfo, String element,
                                         boolean isBorder) {
        if (isBorder) {
            if (mBorderFormatter == null) {
                mBorderFormatter = new DefaultBorderFormatter();
            }
            return mBorderFormatter.format(threadInfo, element, message);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            if (!TextUtils.isEmpty(threadInfo)) {
                stringBuilder.append(threadInfo).append(SLogConstants.LINE_SEPARATOR);
            }
            if (element != null) {
                stringBuilder.append(element).append(SLogConstants.LINE_SEPARATOR);
            }
            if (!TextUtils.isEmpty(message)) {
                stringBuilder.append(message);
            }
            return stringBuilder.toString();
        }
    }

    /**
     * 数据json格式化
     *
     * @param message
     * @return
     */
    private String JsonMessage(String message) {
        if (mIsAutoJson && !TextUtils.isEmpty(message)) {
            String jsonMsg = PrinterUtils.jsonFormat(message);
            if (jsonMsg != null && jsonMsg.length() != 0) {
                return jsonMsg;
            }
        }
        return message;
    }
}