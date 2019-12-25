package com.flt.Common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private static final String APP_SHARED_PREFS = "com.flt";
    private static SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    public AppPreferences(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }
    public String getFirstTimeRunApp() {
        return appSharedPrefs.getString( AppConstants.FIRST_TIME_RUN_APP, "");
    }

    public void setFirstTimeRunApp(String text) {
        prefsEditor.putString(AppConstants.FIRST_TIME_RUN_APP, text);
        prefsEditor.commit();
    }
    public String getUserId() {
        return appSharedPrefs.getString(AppConstants.USER_ID_ALIAS, "");
    }

    public void setUserId(String text) {
        prefsEditor.putString(AppConstants.USER_ID_ALIAS, text);
        prefsEditor.commit();
    }
    public String getName() {
        return appSharedPrefs.getString(AppConstants.USER_NAME, "");
    }

    public void setName(String text) {
        prefsEditor.putString(AppConstants.USER_NAME, text);
        prefsEditor.commit();
    }
    public String getLastLogin() {
        return appSharedPrefs.getString(AppConstants.LAST_LOGIN_TIME, "");
    }

    public void setLastLogin(String text) {
        prefsEditor.putString(AppConstants.LAST_LOGIN_TIME, text);
        prefsEditor.commit();
    }
    public int getLoginState() {
        return appSharedPrefs.getInt(AppConstants.LOGIN_STATE, 0);
    }

    public void setLoginState(int text) {
        prefsEditor.putInt(AppConstants.LOGIN_STATE, text);
        prefsEditor.commit();
    }
    public void setLanCode(String code) {
        prefsEditor.putString(AppConstants.LANGUAGE_CODE, code);
        prefsEditor.commit();
    }

    public String getLanCode() {
        return appSharedPrefs.getString(AppConstants.LANGUAGE_CODE, "");
    }

}
