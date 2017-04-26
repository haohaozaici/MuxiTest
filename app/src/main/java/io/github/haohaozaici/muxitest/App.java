package io.github.haohaozaici.muxitest;

import android.app.Application;
import org.greenrobot.greendao.database.Database;

/**
 * Created by hao on 2017/4/25.
 */

public class App extends Application {

    private DaoSession mDaoSession;


    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "muxi-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();

    }


    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
