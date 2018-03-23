package com.abount.cg.commonlibrary.models.realm;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by mo_yu on 2018/3/23.奔溃日志
 */

public class CrashInfo extends RealmObject {

    private Date time;
    private String appVersion;
    private String msg;

    public CrashInfo() {
    }

    public CrashInfo(String appVersion, String msg) {
        this.time = new Date();
        this.appVersion = appVersion;
        this.msg = msg;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
