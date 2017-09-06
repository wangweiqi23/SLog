package com.weiqi.slog;

import android.content.Context;

import com.weiqi.slog.util.LogSegment;
import com.weiqi.slog.util.ZoneOffset;

/**
 * Created by alexwangweiqi on 17/8/25.
 */

public class Settings {

    /**
     * 可以为null
     */
    private Context mContext = null;

    /**
     * 时间格式.
     */
    private String mTimeFormat;

    /**
     * 时区偏移时间.
     */
    private final ZoneOffset mZoneOffset;

    /**
     * 切片间隔，单位小时.
     */
    private final LogSegment mLogSegment;

    /**
     * 是否添加日志边框.
     */
    private final boolean mIsBorder;

    /**
     * 是否打印线程信息.
     */
    private final boolean mIsThread;

    /**
     * 是否打印堆栈跟踪数据.
     */
    private final boolean mIsStackTrace;

    private Settings(Builder builder) {
        mContext = builder.mContext;
        mTimeFormat = builder.mTimeFormat;
        mZoneOffset = builder.mZoneOffset;
        mLogSegment = builder.mLogSegment;
        mIsBorder = builder.mIsBorder;
        mIsThread = builder.mIsThread;
        mIsStackTrace = builder.mIsStackTrace;
    }

    public Context getContext() {
        return mContext;
    }

    public String getTimeFormat() {
        return mTimeFormat;
    }

    public ZoneOffset getZoneOffset() {
        return mZoneOffset;
    }

    public LogSegment getLogSegment() {
        return mLogSegment;
    }

    public boolean isBorder() {
        return mIsBorder;
    }

    public boolean isThread() {
        return mIsThread;
    }

    public boolean isStackTrace() {
        return mIsStackTrace;
    }



    public static class Builder {

        private Context mContext;
        private String mTimeFormat = SLogConstants.DEFAULT_TIME_FORMAT;
        private ZoneOffset mZoneOffset = ZoneOffset.P0800;
        private LogSegment mLogSegment = LogSegment.TWENTY_FOUR_HOURS;
        private boolean mIsBorder = true;
        private boolean mIsThread = true;
        private boolean mIsStackTrace = true;

        public Builder() {
        }

        public Builder context(Context context) {
            this.mContext = context;
            return this;
        }

        public Builder timeFormat(String timeFormat) {
            this.mTimeFormat = timeFormat;
            return this;
        }

        public Builder zoneOffset(ZoneOffset zoneOffset) {
            this.mZoneOffset = zoneOffset;
            return this;
        }

        public Builder mLogSegment(LogSegment logSegment) {
            this.mLogSegment = logSegment;
            return this;
        }

        public Builder isBorder(boolean isBorder) {
            this.mIsBorder = isBorder;
            return this;
        }

        public Builder isThread(boolean isThread) {
            this.mIsThread = isThread;
            return this;
        }

        public Builder isStackTrace(boolean isStackTrace) {
            this.mIsStackTrace = isStackTrace;
            return this;
        }

        public Settings build() {
            return new Settings(this);
        }

    }

}
