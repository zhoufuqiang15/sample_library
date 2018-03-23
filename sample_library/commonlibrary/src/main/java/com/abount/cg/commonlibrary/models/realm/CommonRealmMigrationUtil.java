package com.abount.cg.commonlibrary.models.realm;

import android.util.Log;

import java.util.Date;

import io.realm.FieldAttribute;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by mo_yu on 2018/3/23.realm配置
 */

public class CommonRealmMigrationUtil {

    public static final int COMMON_VERSION = 1;

    public static void migrate(RealmSchema schema, long oldVersion, long newVersion) {
        Log.e("RealmMigration:", "oldVersion: " + oldVersion + " newVersion: " + newVersion);
    }
}
