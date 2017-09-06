package com.weiqi.test.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by alexwangweiqi on 17/9/1.
 */

public class SDUtils {

    /**
     * @return 日志文件存储地址
     */
    public static String getLogPath(Context context) {
        String dataPath = getDataRoot(context);
        if (!TextUtils.isEmpty(dataPath)) {
            return dataPath + "slog";
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                "slog";
    }

    public static String getDataRoot(Context context) {
        File filesDir = context.getExternalFilesDir(null);//外存储锁定时,返回null
        return null != filesDir ? filesDir.getAbsolutePath() + File.separator : null;
    }
}
