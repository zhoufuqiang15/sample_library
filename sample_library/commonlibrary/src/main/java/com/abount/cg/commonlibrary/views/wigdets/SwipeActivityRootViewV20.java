package com.abount.cg.commonlibrary.views.wigdets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.WindowInsets;

/**
 * Created by mo_yu on 2018/3/23.侧滑关闭
 */

public class SwipeActivityRootViewV20 extends SwipeActivityRootView {


    public SwipeActivityRootViewV20(@NonNull Context context) {
        super(context);
    }

    public SwipeActivityRootViewV20(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeActivityRootViewV20(
            @NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public final WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(0,
                    0,
                    0,
                    insets.getSystemWindowInsetBottom()));
        } else {
            return insets;
        }
    }
}
