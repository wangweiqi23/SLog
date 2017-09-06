package com.weiqi.slog.comparator;

import java.io.File;
import java.util.Comparator;

/**
 * 缓存日志文件排序
 * Created by alexwangweiqi on 17/9/1.
 */

public class LogFileComparator implements Comparator<File> {

    @Override
    public int compare(File lhs, File rhs) {
        return lhs.getName().compareTo(rhs.getName());
    }
}
