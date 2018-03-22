package com.abount.cg.sample_library.api;

import com.abount.cg.httplibrary.AcgHttp;
import com.abount.cg.httplibrary.utils.HttpResultFunc;
import com.google.gson.JsonElement;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mo_yu on 2018/3/22.
 */

public class CommonApi {

    public static Observable<JsonElement> getRandomContentObb(String category, int pageSize) {
        return AcgHttp.getRetrofit()
                .create(CommonService.class)
                .getRandomContent(category, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
