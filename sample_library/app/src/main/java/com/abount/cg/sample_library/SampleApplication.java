package com.abount.cg.sample_library;


import android.support.multidex.MultiDexApplication;

import com.abount.cg.commonlibrary.AcgCommon;
import com.abount.cg.commonlibrary.models.realm.CommonRealmModule;
import com.abount.cg.httplibrary.AcgHttp;
import com.abount.cg.httplibrary.authorization.AppUserConverter;
import com.abount.cg.sample_library.models.realm.CustomerRealmMigration;
import com.alibaba.android.arouter.launcher.ARouter;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by mo_yu on 2018/3/22.Application
 */

public class SampleApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initRealmConfiguration();
        initARouter();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        AcgHttp.init(this.getApplicationContext(),
                AcgCommon.DEBUG,
                AcgCommon.HOST,
                new AppUserConverter());
    }

    private void initRealmConfiguration() {
        Realm.init(this);
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder().modules(Realm
                        .getDefaultModule(),
                new CommonRealmModule())
                .schemaVersion(CustomerRealmMigration.REALM_VERSION);
        try {
            Realm.setDefaultConfiguration(builder.migration(new CustomerRealmMigration())
                    .build());
            Realm.getDefaultInstance()
                    .close();
        } catch (Exception e) {
            e.printStackTrace();
            if (!Constants.DEBUG) {
                Realm.setDefaultConfiguration(builder.deleteRealmIfMigrationNeeded()
                        .build());
            }
        }
    }

    /**
     * 初始化路由组件
     */
    private void initARouter() {
        if (AcgCommon.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

}
