package com.abount.cg.sample_library;


import android.support.multidex.MultiDexApplication;

import com.abount.cg.commonlibrary.AcgCommon;
import com.abount.cg.httplibrary.AcgHttp;
import com.abount.cg.httplibrary.authorization.AppUserConverter;

import io.realm.Realm;

/**
 * Created by Administrator on 2017/12/14.
 */

public class SampleApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initRealmConfiguration();
        AcgHttp.init(this.getApplicationContext(),
                AcgCommon.DEBUG,
                AcgCommon.HOST,
                new AppUserConverter());
    }

    private void initRealmConfiguration() {
        Realm.init(this);
    }

}
