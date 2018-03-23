package com.abount.cg.sample_library.models.realm;

import android.util.Log;


import com.abount.cg.commonlibrary.models.realm.CommonRealmMigrationUtil;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by mo_yu on 2018/3/23.realm配置
 */

public class CustomerRealmMigration implements RealmMigration {

    public static final int REALM_VERSION = CommonRealmMigrationUtil.COMMON_VERSION + 1;

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        Log.e("RealmMigration:", "oldVersion: " + oldVersion + " newVersion: " + newVersion);
        RealmSchema schema = realm.getSchema();
        while (oldVersion < newVersion) {
            if (oldVersion < 6) {
                CommonRealmMigrationUtil.migrate(schema, oldVersion, newVersion);
            } else {
                CommonRealmMigrationUtil.migrate(schema, oldVersion - 1, newVersion - 1);
            }
            oldVersion++;
        }
    }
}
