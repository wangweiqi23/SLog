package com.weiqi.slog.mode;

/**
 * 日志写文件包装
 * Created by alexwangweiqi on 17/8/27.
 */

public class LogPacket {

    //中断写线程
    public static final int TYPE_INTERRUPT = -1;
    //普通日志
    public static final int TYPE_NORMAL = 0;

    /**
     * 日志信息
     */
    private String message;

    /**
     * 文件后缀
     */
    private String fileSubffix;

    /**
     * 包类型
     */
    private int type = TYPE_NORMAL;

    public LogPacket(int type) {
        this.type = type;
    }

    public LogPacket(String message, String fileSubffix) {
        this.message = message;
        this.fileSubffix = fileSubffix;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileSubffix() {
        return fileSubffix;
    }

    public void setFileSubffix(String fileSubffix) {
        this.fileSubffix = fileSubffix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
