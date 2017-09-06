package com.weiqi.slog;

import com.weiqi.slog.util.SysUtils;

/**
 * Created by alexwangweiqi on 17/8/26.
 */

public class SLogConstants {

    public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    /**
     * 日志的扩展名.
     */
    public static final String LOG_EXT = ".log";

    public static final String LINE_SEPARATOR = SysUtils.getLineSeparator();

    public static final int MAX_LENGTH_OF_SINGLE_MESSAGE = 4063;
}
