package com.abount.cg.httplibrary.entities;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mo_yu on 2018/3/22.
 * Http返回结果中的status结构
 * 自顶一个状态码和响应信息
 * RetCode=0时表示正常状态
 * 非正常状态时,msg中含有错误信息
 */
public class HttpStatus {
    @SerializedName(value = "RetCode",alternate = "code")
    int retCode;
    String msg;
    @SerializedName(value = "current_time")
    long currentTimeLong;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCurrentTimeLong() {
        return currentTimeLong;
    }

    public void setCurrentTimeLong(long currentTimeLong) {
        this.currentTimeLong = currentTimeLong;
    }

    public HttpStatus() {}

    protected HttpStatus(Parcel in) {
        this.retCode = in.readInt();
        this.msg = in.readString();
        this.currentTimeLong = in.readLong();
    }
}
