package com.flt.Common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class AppPreferences {
    private static final String APP_SHARED_PREFS = "com.flt";
    private static SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    public AppPreferences(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }
    public String getImeiNo()
    {
        return appSharedPrefs.getString( AppConstants.IMEI, "");
    }
    public void setImeiNo(String text)
    {
        prefsEditor.putString(AppConstants.IMEI, text);
        prefsEditor.commit();
    }
    public String getFirstTimeRunApp() {
        return appSharedPrefs.getString( AppConstants.FIRST_TIME_RUN_APP, "");
    }

    public void setFirstTimeRunApp(String text) {
        prefsEditor.putString(AppConstants.FIRST_TIME_RUN_APP, text);
        prefsEditor.commit();
    }
    public String getLoginId() {
        return appSharedPrefs.getString(AppConstants.LOGIN_ID, "");
    }

    public void setLoginId(String text) {
        prefsEditor.putString(AppConstants.LOGIN_ID, text);
        prefsEditor.commit();
    }
    public String getUserStatus() {
        return appSharedPrefs.getString(AppConstants.STATUS, "");
    }

    public void setUserStatus(String text) {
        prefsEditor.putString(AppConstants.STATUS, text);
        prefsEditor.commit();
    }
    public String getEmail() {
        return appSharedPrefs.getString(AppConstants.EMAIL, "");
    }

    public void setEmail(String text) {
        prefsEditor.putString(AppConstants.EMAIL, text);
        prefsEditor.commit();
    }
    public String getIsGPS() {
        return appSharedPrefs.getString(AppConstants.ISGPS, "");
    }

    public void setIsGPS(String text) {
        prefsEditor.putString(AppConstants.ISGPS, text);
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

    public void setLanName(String code) {
        prefsEditor.putString(AppConstants.LANGUAGE_NAME, code);
        prefsEditor.commit();
    }

    public String getLanName() {
        return appSharedPrefs.getString(AppConstants.LANGUAGE_NAME, "");
    }


    public String getUserLoation()
    {
        return appSharedPrefs.getString(AppConstants.USER_ADDRESS, "");
    }
    public void setUserLocation(String address)
    {
        prefsEditor.putString(AppConstants.USER_ADDRESS, address);
        prefsEditor.commit();
    }
    public String getInnerModule()
    {
        return appSharedPrefs.getString("moduleFlag", "");
    }
    public void setInnerModule(String flag)
    {
        prefsEditor.putString("moduleFlag", flag);
        prefsEditor.commit();
    }
    public String getCarSpecification()
    {
        return appSharedPrefs.getString("carSpecification", "");
    }
    public void setCarSpecification(HashMap<String, String> specification)
    {
        prefsEditor.putString("carSpecification", String.valueOf(specification));
        prefsEditor.commit();
    }
    public String getLat()
    {
        return appSharedPrefs.getString("Lat", "");
    }
    public void setLat(double flag)
    {
        prefsEditor.putString("Lat", String.valueOf(flag));
        prefsEditor.commit();
    }
    public String getLong()
    {
        return appSharedPrefs.getString("Lng", "");
    }
    public void setLong(double flag)
    {
        prefsEditor.putString("Lng", String.valueOf(flag));
        prefsEditor.commit();
    }
    public String getServerIp() {
        return appSharedPrefs.getString("server_ip", "");
    }

    public void setServerIp(String text) {
        prefsEditor.putString("server_ip", text);
        prefsEditor.commit();
    }
    public String getServerPort() {
        return appSharedPrefs.getString("server_port", "");
    }

    public void setServerPort(String text) {
        prefsEditor.putString("server_port", text);
        prefsEditor.commit();
    }
}
