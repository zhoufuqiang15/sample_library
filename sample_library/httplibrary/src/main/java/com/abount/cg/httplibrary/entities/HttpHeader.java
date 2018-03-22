package com.abount.cg.httplibrary.entities;

import android.content.Context;


import com.abount.cg.commonlibrary.utils.CommonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mo_yu on 2018/3/22.
 * 客户端使用的授权管理信息model
 */
public class HttpHeader extends HttpHeaderBase {
    private Context context;
    private String appVersion;

    public HttpHeader(Context mContext) {
        this.context = mContext.getApplicationContext();
        this.appVersion = CommonUtil.getAppVersion(mContext);
    }

    private String getAppName(Context context) {
        String appName = null;

        return appName;
    }

    /**
     * 获取授权信息key value的map数据,用于设置http header的授权信息
     *
     * @return
     * @override
     */
    public Map<String, String> getHeaderMap() {
        if (headerMap == null) {
            headerMap = new HashMap<>();
        }
        if (!headerMap.containsKey("appver")) {
            headerMap.put("appver", appVersion);
        }
        if (!headerMap.containsKey("devicekind")) {
            headerMap.put("devicekind", "android");
        }
//        User user = UserSession.getInstance()
//                .getUser(context);
//        if (user != null) {
//            if (user.getAccessToken() != null) {
//                headerMap.put("Http-Access-Token", user.getAccessToken());
//            }
//            if (user.getToken() != null) {
//                headerMap.put("token", user.getToken());
//            }
//            if (user.getToken() != null) {
//                headerMap.put("secret", CommonUtil.getMD5(user.getToken() + HljCommon.LOGIN_SEED));
//            }
//        } else {
//            headerMap.remove("Http-Access-Token");
//            headerMap.remove("token");
//            headerMap.remove("secret");
//        }

        return headerMap;
    }

}
