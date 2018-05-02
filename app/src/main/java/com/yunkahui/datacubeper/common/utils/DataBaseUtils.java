package com.yunkahui.datacubeper.common.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yunkahui.datacubeper.greendao.DaoMaster;
import com.yunkahui.datacubeper.greendao.DaoSession;

/**
 * Created by Administrator on 2018/5/2.
 */

public class DataBaseUtils {

    private static DaoSession mDaoSession;

    public static DaoSession getDaoSession(Context context) {
        if (mDaoSession == null) {
            String[] dbname = context.getApplicationInfo().processName.split("\\.");
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < dbname.length; i++) {
                builder.append("_");
                builder.append(dbname[i]);
            }
            builder.append(".db");
            String name = builder.toString().substring(1, builder.toString().length());
            Log.e("2018", "name=" + name);
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, name, null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            mDaoSession = daoMaster.newSession();
        }
        return mDaoSession;
    }

}
