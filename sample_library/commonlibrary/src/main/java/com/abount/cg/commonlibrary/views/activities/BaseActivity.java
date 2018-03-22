package com.abount.cg.commonlibrary.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.abount.cg.commonlibrary.R;

/**
 * Created by mo_yu on 2018/3/22.通用跳转动画activity
 */

public class BaseActivity extends AppCompatActivity {

    @SuppressLint("RestrictedApi")
    @Override
    public void startActivityForResult(
            Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.activity_anim_default, R.anim.slide_out_right);
    }

}
