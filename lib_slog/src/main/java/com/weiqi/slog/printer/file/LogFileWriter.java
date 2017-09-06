package com.weiqi.slog.printer.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件操作类
 * Created by alexwangweiqi on 17/8/27.
 */
public class LogFileWriter {

    private String mLastFileName;

    private File mLogFile;

    private BufferedWriter mBufferedWriter;

    private String mLogDir;

    public LogFileWriter(String logDir) {
        mLogDir = logDir;
    }

    public boolean isOpened() {
        return mBufferedWriter != null;
    }

    public String getLastFileName() {
        return mLastFileName;
    }

    public String setLastFileName(String fileName) {
        return mLastFileName = fileName;
    }

    public File getFile() {
        return mLogFile;
    }

    public boolean create(String newFileName) {
        boolean isCreateFile = false;

        mLogFile = new File(mLogDir, newFileName);
        if (!mLogFile.exists()) {
            try {
                File parent = mLogFile.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                mLogFile.createNewFile();
                isCreateFile = true;
            } catch (IOException e) {
                e.printStackTrace();
                mLastFileName = null;
                mLogFile = null;
            }
        }
        return isCreateFile;
    }

    public boolean open() {
        if (mLogFile == null) {
            return false;
        }
        try {
            mBufferedWriter = new BufferedWriter(new FileWriter(mLogFile, true));
        } catch (Exception e) {
            e.printStackTrace();
            mLastFileName = null;
            mLogFile = null;
            return false;
        }
        return true;
    }

    public boolean close() {
        if (mBufferedWriter != null) {
            try {
                mBufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                mBufferedWriter = null;
                mLastFileName = null;
                mLogFile = null;
            }
        }
        return true;
    }

    public boolean appendLog(String message) {
        boolean isWritered = false;
        try {
            mBufferedWriter.write(message);
            mBufferedWriter.newLine();
            mBufferedWriter.flush();
            isWritered = true;
        } catch (IOException e) {
        }
        return isWritered;
    }
}