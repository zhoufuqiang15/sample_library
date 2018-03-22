package com.abount.cg.commonlibrary.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


/**
 * Created by mo_yu on 18/2/22.提示工具类
 */
public class ToastUtil {

    private static Toast toast;

    public static void showToast(Context context, String hintStr, int hintId) {
        showToast(context, hintStr, hintId, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String hintStr, int hintId, int duration) {
        if (hintId != 0 || !TextUtils.isEmpty(hintStr)) {
            if (toast != null) {
                toast.cancel();
            }
            if (!TextUtils.isEmpty(hintStr)) {
                toast = Toast.makeText(context, hintStr, duration);
            } else {
                toast = Toast.makeText(context, hintId, duration);
            }
            toast.show();
        }
    }

}
