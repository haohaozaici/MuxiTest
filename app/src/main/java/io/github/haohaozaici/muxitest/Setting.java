package io.github.haohaozaici.muxitest;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hao on 2017/4/25.
 */

public class Setting {

    private Context mContext;
    private boolean isFirstRun = true;
    private SharedPreferences mPreferences;

    private static final String SETTING = "setting";


    public Setting(Context context) {
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
    }


    public void loadSetting() {
        isFirstRun = mPreferences.getBoolean("isFirstRun", true);
    }


    public void saveSetting() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("isFirstRun", isFirstRun);
        editor.apply();
    }


    public boolean isFirstRun() {
        return isFirstRun;
    }


    public void setFirstRun(boolean firstRun) {
        isFirstRun = firstRun;
    }
}
