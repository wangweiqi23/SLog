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

import com.weiqi.slog.SLogConstants;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 打印机工具类.
 */
public class PrinterUtils {

    /**
     * 日志类名.
     */
    private static final String LOG_CLASS_NAME = "com.weiqi.slog.SLog";

    /**
     * 日志的打印方法名.
     */
    private static final String LOG_PRINT_METHOD_NAME = "printLog";

    /**
     * 控制台打印的栈内容格式.
     */
    private static final String PRINT_FORMAT_THREAD = "thread ID:%s NAME:%s";

    private static final int JSON_INDENT = 4;


    public static String getThreadInfo(Thread thread) {
        return String.format(PRINT_FORMAT_THREAD, thread.getId(), thread.getName());
    }

    public static String getElementFromStackStr(int stackTraceLevel) {
        StackTraceElement[] elements = new Throwable().getStackTrace();
        int index = getStackIndex(elements);
        if (index == -1) {
            throw new IllegalStateException("set -keep class com.weiqi.slog.** { *; } in your" +
                    " proguard config file");
        }

        StringBuilder stringBuilder = new StringBuilder();

        int endIndex = stackTraceLevel == -1 ? elements.length : (index + stackTraceLevel <
                elements.length ? index + stackTraceLevel : elements.length);
        for (; index < endIndex; index++) {
            stringBuilder.append(elements[index].toString());
            if (index != endIndex - 1) {
                stringBuilder.append(SLogConstants.LINE_SEPARATOR);
            }
        }
        return stringBuilder.toString();
    }


    public static StackTraceElement getElementFromStack() {
        StackTraceElement[] elements = new Throwable().getStackTrace();
        int index = getStackIndex(elements);
        if (index == -1) {
            throw new IllegalStateException("set -keep class com.weiqi.slog.** { *; } in your" +
                    " proguard config file");
        }
        return elements[index];
    }

    /**
     * 获取调用日志类输出方法的堆栈元素索引.
     *
     * @param elements 堆栈元素
     * @return 索引位置，-1 - 不可用
     */
    private static int getStackIndex(StackTraceElement[] elements) {
        boolean isChecked = false;
        StackTraceElement element;
        for (int i = 0; i < elements.length; i++) {
            element = elements[i];
            if (LOG_CLASS_NAME.equals(element.getClassName())
                    && LOG_PRINT_METHOD_NAME.equals(element.getMethodName())) {
                isChecked = true;
            }
            if (isChecked) {
                int index = i + 2;
                if (index < elements.length) {
                    return index;
                }
            }
        }
        return -1;
    }

    public static String jsonFormat(String json) {
        String formattedString = null;
        if (json == null || json.trim().length() == 0) {
            return null;
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                formattedString = jsonObject.toString(JSON_INDENT);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                formattedString = jsonArray.toString(JSON_INDENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedString;
    }


}