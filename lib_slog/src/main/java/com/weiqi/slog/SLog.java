package com.weiqi.slog;

import android.text.TextUtils;
import android.util.Log;

import com.weiqi.slog.printer.Printer;
import com.weiqi.slog.printer.PrinterSet;
import com.weiqi.slog.util.LogLevel;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 日志库
 * Created by alexwangweiqi on 17/8/25.
 */

public class SLog {

    private static Settings sSettings;

    private static Printer sPrinter;

    public static void init(Settings settings, Printer... printers) {
        sSettings = settings;

        sPrinter = new PrinterSet(printers);
    }

    public static Settings getSettings() {
        return sSettings;
    }

    public static void setSettings(Settings settings) {
        sSettings = settings;
    }

    public static void setPrinter(Printer printer) {
        sPrinter = printer;
    }

    /**
     * verbose类型的日志.
     *
     * @param tag     标签
     * @param message 信息
     */
    public static void v(String tag, String message) {
        printLog(LogLevel.VERBOSE, tag, null, message, null, false);
    }

    /**
     * debug类型的日志.
     *
     * @param tag     标签
     * @param message 信息
     */
    public static void d(String tag, String message) {
        printLog(LogLevel.DEBUG, tag, null, message, null, false);
    }

    /**
     * info类型的日志.
     *
     * @param tag     标签
     * @param message 信息
     */
    public static void i(String tag, String message) {
        printLog(LogLevel.INFO, tag, null, message, null, false);
    }

    /**
     * warn类型的日志.
     *
     * @param tag     标签
     * @param message 信息
     */
    public static void w(String tag, String message) {
        printLog(LogLevel.WARN, tag, null, message, null, false);
    }

    /**
     * error类型的日志.
     *
     * @param tag     标签
     * @param t       {@link Throwable}
     * @param message 信息
     */
    public static void e(String tag, Throwable t, String message) {
        printLog(LogLevel.ERROR, tag, t, message, null, false);
    }

    /**
     * error类型的日志.
     *
     * @param tag     标签
     * @param message 信息
     */
    public static void e(String tag, String message) {
        printLog(LogLevel.ERROR, tag, null, message, null, false);
    }

    /**
     * error类型的日志.
     *
     * @param tag 标签
     * @param t   {@link Throwable}
     */
    public static void e(String tag, Throwable t) {
        printLog(LogLevel.ERROR, tag, t, null, null, false);
    }

    /**
     * error类型的日志 同步写日志.
     *
     * @param tag
     * @param t
     * @param message
     */
    public static void eSync(String tag, Throwable t, String message) {
        printLog(LogLevel.ERROR, tag, t, message, null, true);
    }

    /**
     * wtf类型的日志.
     *
     * @param tag     标签
     * @param t       {@link Throwable}
     * @param message 信息
     */
    public static void wtf(String tag, Throwable t, String message) {
        printLog(LogLevel.WTF, tag, t, message, null, false);
    }

    /**
     * wtf类型的日志.
     *
     * @param tag     标签
     * @param message 信息
     */
    public static void wtf(String tag, String message) {
        printLog(LogLevel.WTF, tag, null, message, null, false);
    }

    /**
     * wtf类型的日志 异步写日志.
     *
     * @param tag 标签
     * @param t   {@link Throwable}
     */
    public static void wtf(String tag, Throwable t) {
        printLog(LogLevel.WTF, tag, t, null, null, false);
    }

    /**
     * wtf类型的日志 同步写日志.
     *
     * @param tag     标签
     * @param message 信息
     */
    public static void wtfSync(String tag, String message) {
        printLog(LogLevel.WTF, tag, null, message, null, true);
    }

    /**
     * wtf类型的日志 自定义参数.
     *
     * @param tag         标签
     * @param message     信息
     * @param fileSubffix 日志文件的特殊后缀. 例如： 2017-2-2_xx.log、 2017-2-2_xx.log_subfix
     * @param isSync      是否同步方式写入文件
     */
    public static void wtfParams(String tag, String message, String fileSubffix, boolean isSync) {
        printLog(LogLevel.WTF, tag, null, message, fileSubffix, isSync);
    }


    /**
     * 网络日志.
     * 通常情况下网络日志较多不建议写入文件
     *
     * @param tag
     * @param requestUrl
     * @param requestHeader
     * @param result
     */
    public static void netWork(String tag, String requestUrl, String requestHeader, String result) {
        printLog(LogLevel.NETWORK, tag, requestUrl, requestHeader, result, null, false);
    }

    /**
     * 打印日志.
     *
     * @param level       {@link LogLevel}，日志级别
     * @param tag         标签
     * @param t           {@link Throwable}
     * @param message     信息
     * @param fileSubffix 文件存储后缀标签 默认 null  例如： 2017-2-2-xx.log、 17-2-2-xx_subfix.log
     * @param isSync      是否同步存储 默认 false
     */
    public static void printLog(LogLevel level, String tag, Throwable t, String
            message, String fileSubffix, boolean isSync) {

        message = getMessage(message, t);
        if (TextUtils.isEmpty(message)) {
            return;// 不记录没有信息和异常的日志
        }

        if (sPrinter != null) {
            sPrinter.println(level, tag, message, sSettings.isBorder(), sSettings.isThread(),
                    sSettings.isStackTrace(), isSync, fileSubffix);
        }
    }

    /**
     * 打印网络日志.
     *
     * @param level       {@link LogLevel}，日志级别
     * @param tag         标签
     * @param url         请求地址
     * @param params      请求参数等头信息
     * @param result      请求结果
     * @param fileSubffix 文件存储后缀标签 默认 null  例如： 2017-2-2-xx.log、 17-2-2-xx_subfix.log
     * @param isSync      是否同步存储 默认 false
     */
    public static void printLog(LogLevel level, String tag, String url, String params, String
            result, String fileSubffix, boolean isSync) {

        if (sPrinter != null) {
            sPrinter.printNetwork(level, tag, url, params, result, sSettings.isBorder(), isSync,
                    fileSubffix);
        }
    }

    /**
     * 非必须调用Api接口
     * 可以应用退出或释放Log相关内存 调用
     */
    public static void onDestroy() {
        sPrinter.onDestroy();
    }

    private static String getMessage(String message, Throwable t) {
        if (TextUtils.isEmpty(message)) {
            message = null;
        }
        if (message == null) {
            if (t != null) {
                message = Log.getStackTraceString(t);
            }
        } else {
            if (t != null) {
                message += SLogConstants.LINE_SEPARATOR + getStackTraceString(t);
            }
        }
        return message;
    }

    /**
     * 获取异常堆栈跟踪信息，不同于Log.getStackTraceString()，该方法不会过滤掉UnknownHostException.
     *
     * @param t {@link Throwable}
     * @return 异常栈里的信息
     */
    private static String getStackTraceString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
