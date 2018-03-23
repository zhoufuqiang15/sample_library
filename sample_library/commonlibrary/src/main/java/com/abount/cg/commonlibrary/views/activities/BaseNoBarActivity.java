package com.abount.cg.commonlibrary.views.activities;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by mo_yu on 2018/3/22.不带actionbar的通用跳转动画activity
 */

public class BaseNoBarActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customRootView.appBarLayout.setVisibility(View.GONE);
        setSupportActionBar(null);
    }

    public void setDefaultStatusBarPadding() {
        setActionBarPadding(this, customRootView.drawerContentLayout);
    }

    public void setAppBar(@Nullable Toolbar toolbar) {
        setSupportActionBar(toolbar);
        setDefaultStatusBarPadding();
    }

    public void setStatusBarPaddingColor(@ColorInt int color) {
        customRootView.drawerContentLayout.setBackgroundColor(color);
    }
}
