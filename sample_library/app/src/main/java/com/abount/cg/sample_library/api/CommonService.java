package com.abount.cg.sample_library.api;

import com.abount.cg.httplibrary.entities.HttpResult;
import com.google.gson.JsonElement;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by mo_yu on 2018/3/22.
 */

public interface CommonService {

    @GET("api/random/data/{category}/{pageSize}")
    Observable<JsonElement> getRandomContent(
            @Path("category") String category,
            @Path("pageSize") int pageSize);
}
