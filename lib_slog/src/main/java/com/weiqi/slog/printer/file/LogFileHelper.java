package com.weiqi.slog.printer.file;

import android.text.TextUtils;

import com.weiqi.slog.cache.CacheHelper;
import com.weiqi.slog.mode.LogPacket;
import com.weiqi.slog.util.FileUtils;
import com.weiqi.slog.util.SysUtils;

/**
 * 日志文件 处理类
 * Created by alexwangweiqi on 17/8/28.
 */

public class LogFileHelper {

    private LogFileWriter mLogFileWriter;
    private CacheHelper mLogCacheHelper;

    public LogFileHelper(String logDir, CacheHelper cacheHelper) {
        mLogCacheHelper = cacheHelper;
        mLogFileWriter = new LogFileWriter(logDir);
    }

    public boolean doPrintln(LogPacket logObject) {
        boolean isWritered = false;
        synchronized (this) {
            String lastFileName = mLogFileWriter.getLastFileName();
            String newFileName = FileUtils.getFileName(logObject.getFileSubffix());
            if (!TextUtils.equals(lastFileName, newFileName)) {

                //关闭旧文件IO
                if (mLogFileWriter.isOpened()) {
                    mLogFileWriter.close();
                }
                //创建新文件
                boolean isCreateFile = mLogFileWriter.create(newFileName);

                //打开新文件
                if (!mLogFileWriter.open()) {
                    return isWritered;
                }

                //检查是否达到缓存限制
                checkCache(isCreateFile, newFileName);

                //写入新文件头信息
                if (isCreateFile) {
                    mLogFileWriter.appendLog(SysUtils.getDevInfo());
                }

                mLogFileWriter.setLastFileName(newFileName);
            }

            isWritered = mLogFileWriter.appendLog(logObject.getMessage());
        }
        return isWritered;
    }

    private void checkCache(boolean isCreateFile, String newFileName) {
        if (isCreateFile && mLogCacheHelper != null) {
            mLogCacheHelper.checkCache(newFileName);
        }
    }

    public void onDestroy() {
        if (mLogCacheHelper != null) {
            mLogCacheHelper = null;
        }

        if (mLogFileWriter != null) {
            mLogFileWriter.close();
        }
    }

}
