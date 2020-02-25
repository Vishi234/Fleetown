package com.flt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flt.Common.AppPreferences;
import com.flt.Common.Common;
import com.flt.Common.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BasicInfo extends Activity {
    EditText et_name,et_mobileno,et_email;
    Button btn_submit;
    AppPreferences mAppPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        Common.StatusBarColor(getWindow(),this,R.color.infoBGColor);
        mAppPreferences = new AppPreferences(BasicInfo.this);
        Init();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_name.getText().length() == 0)
                {
                    Toast.makeText(BasicInfo.this, "Please enter full name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                /*Intent i = new Intent(BasicInfo.this,VehicleRegistration.class);
                startActivity(i);*/
            }
        });
    }
    public void Init()
    {
        et_name = findViewById(R.id.et_name);
        et_mobileno = findViewById(R.id.et_mobileno);
        et_mobileno.setText(mAppPreferences.getUserId());
        et_email = findViewById(R.id.et_email);
        btn_submit = findViewById(R.id.btn_submit);
    }

}
