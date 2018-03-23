package com.abount.cg.commonlibrary.views.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.abount.cg.commonlibrary.R;
import com.abount.cg.commonlibrary.adapters.CrashRecyclerAdapter;
import com.abount.cg.commonlibrary.models.realm.CrashInfo;
import com.abount.cg.commonlibrary.utils.CommonUtil;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;


/**
 * Created by mo_yu on 2018/3/23.奔溃日志列表
 */

public class CrashListActivity extends BaseActivity {

    RecyclerView recyclerView;

    private CrashRecyclerAdapter adapter;
    private Realm realm;
    private Subscription loadSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_fragment_recycler_view___cm);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new CrashRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        realm = Realm.getDefaultInstance();
        loadSubscription = realm.where(CrashInfo.class)
                .findAllSortedAsync("time", Sort.DESCENDING)
                .asObservable()
                .filter(new Func1<RealmResults<CrashInfo>, Boolean>() {
                    @Override
                    public Boolean call(RealmResults<CrashInfo> crashInfos) {
                        return crashInfos.isLoaded();
                    }
                })
                .first()
                .subscribe(new Subscriber<RealmResults<CrashInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onBackPressed();
                    }

                    @Override
                    public void onNext(RealmResults<CrashInfo> crashInfos) {
                        adapter.setCrashs(crashInfos);
                    }
                });
    }

    @Override
    protected void onFinish() {
        CommonUtil.unSubscribeSubs(loadSubscription);
        realm.close();
        super.onFinish();
    }
}
