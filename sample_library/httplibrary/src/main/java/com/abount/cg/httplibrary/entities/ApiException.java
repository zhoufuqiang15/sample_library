package com.abount.cg.httplibrary.entities;

import android.text.TextUtils;

/**
 * Created by mo_yu on 2018/3/22.
 * 接口返回的错误报告的时候当做网络请求异常处理,默认就使用status中的msg作为异常提示信息
 * 默认不需要做特殊处理,当异常提示信息需要根据特殊处理时,可在这里判断status retCode进行相应处理
 */
public class ApiException extends RuntimeException {


    public ApiException(
            String detailMessage) {
        super(detailMessage);
    }

    public ApiException(HttpStatus status) {
        this(getExceptionsMsg(status));
    }

    public static String getExceptionsMsg(HttpStatus status) {
        String msg;
        switch (status.getRetCode()) {
            // 在这里添加特殊处理
            default:
                if (TextUtils.isEmpty(status.getMsg())) {
                    msg = "未知错误";
                } else {
                    msg = status.getMsg();
                }
                break;
        }

        return msg;
    }
}
