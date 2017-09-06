package com.weiqi.slog.cache;

import android.text.TextUtils;

import com.weiqi.slog.comparator.LogFileComparator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志本地文件缓存自动清理 优先删除旧文件
 * Created by alexwangweiqi on 17/8/28.
 */

public class LogCacheHelper implements CacheHelper {

    private LinkedHashMap<String, Object> mEntries = new LinkedHashMap<String, Object>(16, 0.75f,
            true);

    private boolean mIsInited;

    private String mLogDir;
    private int mMaxFileSize;

    public LogCacheHelper(String logDir, int maxFileSize) {
        mLogDir = logDir;
        mMaxFileSize = maxFileSize;
    }

    @Override
    public void checkCache(String newFileName) {
        if (!mIsInited) {
            initialize();
        }

        if (!mEntries.containsKey(newFileName)) {
            mEntries.put(newFileName, null);
        }

        removeIfNeed(newFileName);

    }

    private void removeIfNeed(String newFileName) {
        if (mEntries.size() > mMaxFileSize) {
            Iterator iterator = this.mEntries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                File file = getFileForName((String) entry.getKey());
                if (TextUtils.equals(newFileName, file.getName())) {
                    //手机时间混乱等异常
                    break;
                }
                file.delete();
                iterator.remove();
                if (mEntries.size() <= mMaxFileSize) {
                    break;
                }
            }
        }
    }

    private void initialize() {
        mIsInited = true;
        this.mEntries.clear();
        File dir = new File(mLogDir);
        List<File> listFiles = Arrays.asList(dir.listFiles());
        Collections.sort(listFiles, new LogFileComparator());
        for (int i = 0; i < listFiles.size(); i++) {
            mEntries.put(listFiles.get(i).getName(), null);
        }
    }

    private void removeDiskCache(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private File getFileForName(String fileName) {
        return new File(this.mLogDir, fileName);
    }
}
