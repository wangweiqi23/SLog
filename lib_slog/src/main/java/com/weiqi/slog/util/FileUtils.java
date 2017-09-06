/*
 * Copyright JiongBull 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weiqi.slog.util;

import android.text.TextUtils;

import com.weiqi.slog.SLog;

import static com.weiqi.slog.SLogConstants.LOG_EXT;

/**
 * 文件工具类.
 */
public class FileUtils {

    /**
     * 根据日志类型用不同文件后缀来区分
     *
     * @return 比如“.log_crash”表示崩溃日志类型
     */
    public static String getFileExten(String type) {
        if (TextUtils.isEmpty(type)) {
            return LOG_EXT;
        } else {
            return LOG_EXT + "_" + type;
        }
    }

    /**
     * 根据切片时间获取当前的时间段.
     *
     * @return 比如“0001”表示00:00-01:00
     */
    public static String getCurSegment() {
        int hour = TimeUtils.getCurHour();
        LogSegment logSegment = SLog.getSettings().getLogSegment();
        int segmentValue = logSegment.getValue();
        int start = hour - hour % segmentValue;
        int end = start + segmentValue;
        if (end == 24) {
            end = 0;
        }
        return getDoubleNum(start) + getDoubleNum(end);
    }

    /**
     * 对于1-9的数值进行前置补0.
     *
     * @param num 数值
     * @return num在[0, 9]时前置补0，否则返回原值
     */
    public static String getDoubleNum(int num) {
        return num < 10 ? "0" + num : String.valueOf(num);
    }

    /**
     * 生成日志文件名.
     *
     * @return 日志文件名
     */
    public static String getFileName(String subffix) {
        String curDate = TimeUtils.getCurDate();
        String fileName;
        if (SLog.getSettings().getLogSegment() == LogSegment.TWENTY_FOUR_HOURS) {
            fileName = curDate + getFileExten(subffix);
        } else {
            fileName = curDate + "_" + getCurSegment() + getFileExten(subffix);
        }
        return fileName;
    }
}