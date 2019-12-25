package com.flt.Common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;

import java.util.Locale;

public class Utils {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public  void SetLocale(Context con, String localeName, String currentLanguage)
    {
        Locale myLocale;
        if(!localeName.equalsIgnoreCase(currentLanguage))
        {
            myLocale=new Locale(localeName);
            Resources res=con.getResources();
            DisplayMetrics dm=res.getDisplayMetrics();
            Configuration conf=res.getConfiguration();
            conf.setLocale(myLocale);
            res.updateConfiguration(conf,dm);
        }
    }
    public static boolean hasPermissions(Context context,String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
