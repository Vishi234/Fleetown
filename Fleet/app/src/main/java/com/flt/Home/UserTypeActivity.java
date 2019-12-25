package com.flt.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.login.Login;
import com.flt.Driver.DriverRegisrationActivity;
import com.flt.R;

public class UserTypeActivity extends Activity {
    TextView tv_back;
    LinearLayout tv_circle_driver_base;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        Init();
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               GoBack();
            }
        });
        tv_circle_driver_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserTypeActivity.this, DriverRegisrationActivity.class);
                startActivity(intent);
            }
        });
    }
    public void Init()
    {
        tv_back=(TextView)findViewById(R.id.tv_back);
        tv_circle_driver_base=(LinearLayout)findViewById(R.id.tv_circle_driver_base);
    }
    @Override
    public void onBackPressed() {
        GoBack();
    }
    public void GoBack()
    {
        Intent intent=new Intent(UserTypeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
