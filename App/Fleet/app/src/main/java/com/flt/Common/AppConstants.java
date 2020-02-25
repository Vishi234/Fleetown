package com.flt.Common;

import android.Manifest;
import android.Manifest.permission;

import java.util.HashMap;

public interface AppConstants {
    public static HashMap<String, Class> classMap = new HashMap<String, Class>();
    public static HashMap<String, Integer> menuIcon = new HashMap<String, Integer>();
    public static String[] ALL_PERMISSIONS = {
            permission.READ_PHONE_STATE,
            permission.CAMERA,
            permission.READ_EXTERNAL_STORAGE,
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
    public static final String LANGUAGE_NAME = "lname";
    public static final String USER_NAME = "uname";
    public static final String LOGIN_STATE="login";
    public static final String IMEI ="imei";
    public static final String USER_ADDRESS="uLoc";
    public static final String LOGIN_ID="loginId";
    public static final String STATUS="status";
    public static final String EMAIL="email";
    public static final String ISGPS="isGpsLoc";
}
