package com.abount.cg.httplibrary.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mo_yu on 2018/3/22.
 * http返回的result,对应http结构里的body
 */
public class HttpResult<T> {
    HttpStatus status;
    T data;
    @SerializedName("current_time")
    long currentTime;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * Data数据就是接口返回的数据节点,每个接口对应不同的结构,但主要就是有几种
     * 一种是HttpData中定义的含有list节点和分页等信息的列表结构
     * 一种是model实体本身的JsonObject
     *
     * @return
     */
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getCurrentTime() {
        return currentTime;
    }
}
