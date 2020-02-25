package com.flt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.flt.Common.Common;

public class ComingSoon extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);
        Common.StatusBarColor(getWindow(),this,R.color.header);
    }
    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(ComingSoon.this,Dashboard.class);
        startActivity(i);
        finish();
    }
}