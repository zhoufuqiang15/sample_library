package com.abount.cg.sample_library.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.AutoCompleteTextView;

import com.abount.cg.commonlibrary.utils.CommonUtil;
import com.abount.cg.commonlibrary.views.activities.BaseActivity;
import com.abount.cg.httplibrary.utils.HttpSubscriber;
import com.abount.cg.httplibrary.utils.SubscriberOnNextListener;
import com.abount.cg.sample_library.R;
import com.abount.cg.sample_library.api.CommonApi;
import com.google.gson.JsonElement;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mo_yu on 2018/3/22.示例
 */

public class SampleActivity extends BaseActivity {

    @BindView(R.id.tv_result)
    AutoCompleteTextView tvResult;
    private HttpSubscriber initSub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);
        initLoad();
    }

    private void initLoad() {
        initSub = HttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<JsonElement>() {
                    @Override
                    public void onNext(JsonElement jsonElement) {
                        tvResult.setText(jsonElement.toString());
                    }
                })
                .build();
        CommonApi.getRandomContentObb("all", 20)
                .subscribe(initSub);
    }

    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        initLoad();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtil.unSubscribeSubs(initSub);
    }
}
