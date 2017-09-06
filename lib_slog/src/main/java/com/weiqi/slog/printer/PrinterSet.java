package com.weiqi.slog.printer;


import com.weiqi.slog.formatter.MessageFormatter;
import com.weiqi.slog.util.LogLevel;

import java.util.List;

/**
 * 打印机集合
 */
public class PrinterSet implements Printer {

    private Printer[] printers;

    public PrinterSet(Printer... printers) {
        this.printers = printers;
    }

    @Override
    public void println(LogLevel logLevel, String tag, String msg, boolean isBorder, boolean
            isThread,
                        boolean isStackTrace, boolean isSync, String fileSubffix) {
        for (Printer printer : printers) {
            printer.println(logLevel, tag, msg, isBorder, isThread, isStackTrace, isSync,
                    fileSubffix);
        }
    }

    @Override
    public void printNetwork(LogLevel logLevel, String tag, String url, String params, String
            result, boolean isBorder, boolean isSync, String fileSubffix) {
        for (Printer printer : printers) {
            printer.printNetwork(logLevel, tag, url, params, result, isBorder, isSync, fileSubffix);
        }
    }

    @Override
    public void addLevelForFile(List<LogLevel> logLevelsForFile) {
    }

    @Override
    public void setFormatter(MessageFormatter mFormatter) {
    }

    @Override
    public void onDestroy() {
        for (Printer printer : printers) {
            printer.onDestroy();
        }
    }
}