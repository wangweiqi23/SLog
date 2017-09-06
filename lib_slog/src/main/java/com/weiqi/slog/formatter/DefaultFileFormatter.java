package com.weiqi.slog.formatter;

import com.weiqi.slog.SLogConstants;
import com.weiqi.slog.util.LogLevel;
import com.weiqi.slog.util.PrinterUtils;
import com.weiqi.slog.util.TimeUtils;

/**
 * 组装写入文件的日志信息
 * Created by alexwangweiqi on 17/8/31.
 */

public class DefaultFileFormatter implements MessageFormatter {

    /**
     * 文件中保存的内容格式.
     */
    private static final String PRINT_FILE_FORMAT = "[%1$s]" + SLogConstants.LINE_SEPARATOR
            + "[%2$s]" + SLogConstants.LINE_SEPARATOR + "[%3$s]" + SLogConstants.LINE_SEPARATOR
            + "%4$s" + SLogConstants.LINE_SEPARATOR;

    @Override
    public String format(LogLevel logLevel, String tag, String message, boolean isBorder,
                         boolean isThread, boolean isStackTrace) {
        return decorateMsgForFile(message, isThread ? PrinterUtils.getThreadInfo(Thread
                .currentThread()) : null, isStackTrace ? PrinterUtils.getElementFromStackStr(-1)
                : null);
    }

    @Override
    public String formatNetwork(LogLevel logLevel, String tag, String url, String params, String
            result, boolean isBorder) {
        return decorateMsgForFile(result, url, params);
    }

    /**
     * 装饰打印到文件的信息.
     *
     * @param message    日志
     * @param threadInfo 线程信息
     * @param element    堆栈跟踪信息
     * @return 装饰后的信息
     */
    private String decorateMsgForFile(String message, String threadInfo, String element) {
        String time = TimeUtils.getCurTimeLimitMs();

        return String.format(PRINT_FILE_FORMAT, time, threadInfo, element, message);
    }
}
