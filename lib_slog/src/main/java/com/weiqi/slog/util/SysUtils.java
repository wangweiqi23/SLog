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

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.weiqi.slog.R;
import com.weiqi.slog.SLog;
import com.weiqi.slog.SLogConstants;


/**
 * 系统相关工具类.
 */
public class SysUtils {

    private SysUtils() {
    }

    /**
     * 获取设备制造商信息.
     *
     * @return 设备制造商信息
     */
    public static String getManufacturerInfo() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取设备信息.
     *
     * @return 设备信息
     */
    public static String getModelInfo() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取产品信息.
     *
     * @return 产品信息
     */
    public static String getProductInfo() {
        return android.os.Build.PRODUCT;
    }

    /**
     * 获取品牌信息.
     *
     * @return 品牌信息
     */
    public static String getBrandInfo() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取操作系统版本号.
     *
     * @return 操作系统版本号
     */
    public static int getOsVersionCode() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取操作系统版本名.
     *
     * @return 操作系统版本名
     */
    public static String getOsVersionName() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取操作系统版本显示名.
     *
     * @return 操作系统版本显示名
     */
    public static String getOsVersionDisplayName() {
        return android.os.Build.DISPLAY;
    }

    /**
     * 获取版本号.
     *
     * @param context {@link Context}
     * @return 版本号
     */
    public static int getAppVersionCode(Context context) {
        int appVersionCode = 0;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            appVersionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("SysUtils", e.getMessage());
        }
        return appVersionCode;
    }

    /**
     * 获取版本名.
     *
     * @param context {@link Context}
     * @return 版本名
     */
    public static String getAppVersionName(Context context) {
        String appVersionName = context.getString(R.string.unknow_version);
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            appVersionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("SysUtils", e.getMessage());
        }
        return appVersionName;
    }

    /**
     * 获取系统换行符.
     *
     * @return 系统换行符
     */
    public static String getLineSeparator() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return "\n";
        }
        return System.getProperty("line.separator");
    }

    /**
     * 生成系统相关的信息.
     *
     * @return 系统相关的信息
     */
    public static String getDevInfo() {
        Context context = SLog.getSettings().getContext();
        String info = "";
        if (context == null) {
            return info;
        }
        info += context.getString(R.string.app_version_name) + ": " + getAppVersionName(context)
                + SLogConstants.LINE_SEPARATOR;
        info += context.getString(R.string.app_version_code) + ": " + getAppVersionCode(context)
                + SLogConstants.LINE_SEPARATOR;
        info += context.getString(R.string.os_version_name) + ": " + getOsVersionName() +
                SLogConstants.LINE_SEPARATOR;
        info += context.getString(R.string.os_version_code) + ": " + getOsVersionCode() +
                SLogConstants.LINE_SEPARATOR;
        info += context.getString(R.string.os_display_name) + ": " + getOsVersionDisplayName() +
                SLogConstants.LINE_SEPARATOR;
        info += context.getString(R.string.brand_info) + ": " + getBrandInfo() + SLogConstants
                .LINE_SEPARATOR;
        info += context.getString(R.string.product_info) + ": " + getProductInfo() +
                SLogConstants.LINE_SEPARATOR;
        info += context.getString(R.string.model_info) + ": " + getModelInfo() + SLogConstants
                .LINE_SEPARATOR;
        info += context.getString(R.string.manufacturer_info) + ": " + getManufacturerInfo() +
                SLogConstants.LINE_SEPARATOR + SLogConstants.LINE_SEPARATOR;
        return info;
    }
}