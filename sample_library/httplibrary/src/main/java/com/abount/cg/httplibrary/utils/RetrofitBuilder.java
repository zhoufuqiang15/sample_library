package com.abount.cg.httplibrary.utils;

import android.content.Context;

import com.abount.cg.commonlibrary.utils.TimeUtils;
import com.abount.cg.httplibrary.AcgHttp;
import com.abount.cg.httplibrary.entities.HttpHeaderBase;
import com.abount.cg.httplibrary.models.InterceptorLogger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by mo_yu on 2018/3/22.
 * 专用的用于创建Retrofit实例
 */
public class RetrofitBuilder {
    private Context mContext;
    private List<Converter.Factory> converterFactories = new ArrayList<>();
    private HttpHeaderBase headerBase;
    private OkHttpClient okHttpClient;
    private static Cache httpCache;
    public static final String OK_CACHE_DIR = "ok_http_cache";
    public static final int OK_CACHE_SIZE = 10 * 1024 * 1024; // 10 M


    private void initBuilder(Context context) {
        this.mContext = context;

        // 默认添加婚礼纪服务器返回数据中需要特殊处理的gson反序列化的converter
        // 这里设置的时间解析格式将会一直使用,直到添加新的格式的时间解析器将覆盖这一个
        converterFactories.add(GsonUtil.buildHljCommonGsonConverter(AcgHttp.TimeFormatPattern
                .PATTERN_1));
    }

    public RetrofitBuilder(Context context, HttpHeaderBase hljHttpHeaderBase) {
        initBuilder(context);
        this.headerBase = hljHttpHeaderBase;
    }

    /**
     * 添加一个用于给Retrofit使用的解析特有格式的json的converter
     *
     * @param factory
     * @return
     */
    public RetrofitBuilder addConverterFactory(Converter.Factory factory) {
        converterFactories.add(factory);

        return this;
    }

    public Retrofit build() {
        Retrofit.Builder builder = new Retrofit.Builder();

        builder.client(getAuthHttpClient());

        for (Converter.Factory factory : converterFactories) {
            builder.addConverterFactory(factory);
        }
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(AcgHttp.getHOST());

        return builder.build();
    }

    /**
     * 必须在build()之后调用才是有效值
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * 将的http Auth信息放入http请求头中
     *
     * @return
     */
    private OkHttpClient getAuthHttpClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        // 添加cache
        if (httpCache == null && mContext != null) {
            File httpCacheDirectory = new File(mContext.getExternalCacheDir(), OK_CACHE_DIR);
            httpCache = new Cache(httpCacheDirectory, OK_CACHE_SIZE);
        }
        clientBuilder.cache(httpCache);

        // 顺便添加一个logger interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new InterceptorLogger());
        clientBuilder.connectTimeout(1, TimeUnit.MINUTES);
        clientBuilder.readTimeout(1, TimeUnit.MINUTES);
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder();
                Map<String, String> authMap = headerBase.getHeaderMap();
                if (authMap != null && !authMap.isEmpty()) {
                    Iterator iterator = authMap.entrySet()
                            .iterator();
                    while (iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterator.next();
                        String key = (String) entry.getKey();
                        String value = (String) entry.getValue();

                        builder.header(key, value);
                    }
                }
                String timestamp = String.valueOf(TimeUtils.getServerCurrentTimeMillis() / 1000);
                builder.addHeader("timestamp", timestamp);
                builder.addHeader("Authorization",
                        AcgHttp.getAuthorization(request.method(),
                                request.url(),
                                UriUtil.bodyToString(request.body()),
                                timestamp));
                return chain.proceed(builder.build());
            }
        });

        if (AcgHttp.debug) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(logging);
        }

        okHttpClient = clientBuilder.build();
        return okHttpClient;
    }

}
