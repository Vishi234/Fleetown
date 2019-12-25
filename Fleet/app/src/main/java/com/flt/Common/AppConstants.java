package com.flt.Common;

import android.Manifest;
import android.Manifest.permission;

public interface AppConstants {

    public static String[] ALL_PERMISSIONS = {
           /* permission.CAMERA,
            permission.WRITE_EXTERNAL_STORAGE,
            permission.READ_EXTERNAL_STORAGE,
            permission.ACCESS_FINE_LOCATION,
            permission.ACCESS_COARSE_LOCATION,
            permission.READ_PHONE_STATE,*/
            permission.ACCESS_FINE_LOCATION,
            permission.ACCESS_COARSE_LOCATION,
            permission.READ_SMS,
            permission.SEND_SMS,
            permission.RECEIVE_SMS
    };

    public static String[] PERMISSIONS = {
            permission.CAMERA,
            permission.WRITE_EXTERNAL_STORAGE,
            permission.READ_EXTERNAL_STORAGE,
    };

    public static String[] LOCATION_PERMISSIONS = {
            permission.ACCESS_FINE_LOCATION,
            permission.ACCESS_COARSE_LOCATION,
    };
    public static final String FIRST_TIME_RUN_APP="firstTime";
    public static final String LAST_LOGIN_TIME="lastLogin";
    public static final String USER_ID_ALIAS="uid";
    public static final String LANGUAGE_CODE = "lcode";
    public static final String USER_NAME = "uname";
    public static final String LOGIN_STATE="login";
}
