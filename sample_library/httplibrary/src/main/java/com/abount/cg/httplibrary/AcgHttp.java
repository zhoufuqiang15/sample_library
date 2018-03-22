package com.abount.cg.httplibrary;

import android.content.Context;
import android.text.TextUtils;


import com.abount.cg.commonlibrary.utils.FileUtil;
import com.abount.cg.httplibrary.authorization.UserConverterDelegate;
import com.abount.cg.httplibrary.entities.HttpHeader;
import com.abount.cg.httplibrary.entities.HttpHeaderBase;
import com.abount.cg.httplibrary.utils.RetrofitBuilder;
import com.abount.cg.httplibrary.utils.UriUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static com.abount.cg.httplibrary.utils.RetrofitBuilder.OK_CACHE_DIR;


/**
 * Created by mo_yu on 2018/3/22.
 * AcgHttp这个类库中的一些Constants参数
 * 和一些常用的,暴露给使用者的静态方法
 */
public class AcgHttp {

    public static final String TAG = AcgHttp.class.getSimpleName();
    public static boolean debug = true;

    private static Context mContext;
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private static String HOST = "http://www.hunliji.com/";
    private static HttpHeaderBase AcgHttpHeaderBase;

    public class TimeFormatPattern {
        public static final String PATTERN_1 = "yyyy-MM-dd HH:mm:ss";
        public static final String PATTERN_2 = "MM-dd HH:mm";
    }

    /**
     * AcgHttp模块初始化
     * 任何使用到该模块的地方都要确保先进行正确的初始化工作
     * 如果需要自定义http header帮助类，则用这个版本，指明特定的AcgHttpHeaderBase
     *
     * @param debug             debug开关
     * @param host              AcgHttp的全局host
     * @param AcgHttpHeaderBase 需要自定义的http header帮助类，用于获取http header
     */
    public static OkHttpClient init(
            Context context,
            boolean debug,
            String host,
            UserConverterDelegate converterDelegate,
            HttpHeaderBase AcgHttpHeaderBase) {
        AcgHttp.debug = debug;
        AcgHttp.HOST = host;
        AcgHttp.mContext = context;
        AcgHttp.AcgHttpHeaderBase = AcgHttpHeaderBase;
        build();

        return okHttpClient;
    }

    private static void build() {
        RetrofitBuilder builder = new RetrofitBuilder(mContext, AcgHttpHeaderBase);
        retrofit = builder.build();
        okHttpClient = builder.getOkHttpClient();
    }

    /**
     * AcgHttp模块初始化
     * 任何使用到该模块的地方都要确保先进行正确的初始化工作
     * 不需要自定义http header的版本，使用默认的http header生成方法，支持婚礼纪用户端和商家端
     *
     * @param context
     * @param debug
     * @param host
     * @param converterDelegate
     */
    public static OkHttpClient init(
            Context context, boolean debug, String host, UserConverterDelegate converterDelegate) {
        HttpHeader AcgHttpHeader = new HttpHeader(context);
        return init(context, debug, host, converterDelegate, AcgHttpHeader);
    }


    /**
     * 获取全局通用的retrofit实例
     *
     * @return
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            build();
        }
        return retrofit;
    }

    public static OkHttpClient rebuildRetrofit() {
        build();

        return okHttpClient;
    }

    /**
     * 设置AcgHttp库的debug开关
     *
     * @param debug
     */
    public static void setDebug(boolean debug) {
        AcgHttp.debug = debug;
    }

    /**
     * 获取Host地址,所有需要获得这个地址的地方都应该直接从这里获取
     * 全局配置
     */
    public static String getHOST() {
        return HOST;
    }

    /**
     * 修改AcgHttp中的host配置,可以运行时修改
     * 修改host之后必须重建retrofit
     *
     * @param HOST
     */
    public static void setHOST(String HOST) {
        AcgHttp.HOST = HOST;
        build();
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public static void clearCache(Context context) {
        try {
            File file = new File(context.getExternalCacheDir(), OK_CACHE_DIR);
            FileUtil.deleteFolder(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAuthorization(String method, HttpUrl url, String string, String timestamp) {
        String AUTH_TYPE = "HUNLIJI ";
        String SHARED_CLIENT_SECRET = "196702C9-2171-4AA2-A101-6B4C304DC9B6";
        String CLIENT_ID = "49F14C2D-F0F9-4BB2-BC49-B6159CBDEAA2";

        StringBuilder authorization = new StringBuilder(AUTH_TYPE);
        StringBuilder sha1Str = new StringBuilder(method).append(url.uri()
                .getPath());
        String query = url.query();
        if (!TextUtils.isEmpty(query)) {
            sha1Str.append("?")
                    .append(query);
        }
        if (!TextUtils.isEmpty(string)) {
            sha1Str.append(string);
        }

        sha1Str.append(CLIENT_ID)
                .append(SHARED_CLIENT_SECRET)
                .append(timestamp);
        try {
            authorization.append(UriUtil.SHA1(sha1Str.toString()));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        authorization.append(":")
                .append(CLIENT_ID);
        return authorization.toString();
    }
}
