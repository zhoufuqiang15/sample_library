package com.abount.cg.sample_library.view;

import android.os.Bundle;

import com.abount.cg.commonlibrary.modules.helper.RouterPath;
import com.abount.cg.commonlibrary.views.activities.BaseActivity;
import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * Created by mo_yu on 2018/3/23.切换测试环境
 */
@Route(path = RouterPath.IntentPath.AcgApp.Debug.CHANGE_HOST)
public class ChangeHostActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
