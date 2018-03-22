package com.abount.cg.httplibrary.utils;


import com.abount.cg.commonlibrary.utils.TimeUtils;
import com.abount.cg.httplibrary.entities.ApiException;
import com.abount.cg.httplibrary.entities.HttpResult;

import rx.functions.Func1;

/**
 * Created by mo_yu on 2018/3/22.
 * 用来统一处理Http的retCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(HttpResult<T> tHljHttpResult) {
        if (tHljHttpResult.getStatus()
                .getRetCode() != 0) {
            throw new ApiException(tHljHttpResult.getStatus());
        }
        if (tHljHttpResult.getCurrentTime() > 0) {
            TimeUtils.setTimeOffset(tHljHttpResult.getCurrentTime() * 1000);
        }

        return tHljHttpResult.getData();
    }
}
