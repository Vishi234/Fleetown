package com.flt.Common;

import android.app.Activity;
import android.os.Build;
import android.view.Window;

import com.flt.R;

public class Common {
    public static void StatusBarColor(Window window, Activity activity,int resourceId) {
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(activity.getResources().getColor(resourceId));
        }
    }
}
