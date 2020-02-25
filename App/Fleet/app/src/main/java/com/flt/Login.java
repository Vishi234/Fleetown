package com.flt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flt.Common.AppConstants;
import com.flt.Common.AppPreferences;
import com.flt.Common.SearchableAdapter;
import com.flt.Common.Utils;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Login extends Activity {
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    boolean status = false;
    String verification_code;
    TextView tv_terms, tv_list_vehicle,tv_login_logo;
    Button btn_login;
    EditText et_mobile_number;
    LocationManager locationManager;
    String provider;
    private static final int REQUEST_CODE = 100;
    ProgressDialog pd;
    AppPreferences mAppPreferences;
    TelephonyManager telephonyManager;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAppPreferences = new AppPreferences(Login.this);
        if (mAppPreferences.getLoginState() == 1) {
            Intent intent = new Intent(Login.this, Dashboard.class);
            startActivity(intent);
        }
        Init();
        if (!Utils.hasPermissions(Login.this, AppConstants.ALL_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, AppConstants.ALL_PERMISSIONS, REQUEST_CODE);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isNetworkAvailable(Login.this))
                {
                    if(Validate())
                    {
                        new OTPSend(Login.this).execute();
                    }
                }
                else{
                    Toast.makeText(Login.this, Utils.GetString(R.string.nointernet,Login.this), Toast.LENGTH_SHORT).show();
                }

            }
        });
        et_mobile_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String word = s.toString();
                if (word.equalsIgnoreCase("+91")) {
                    et_mobile_number.setText("+91-");
                    et_mobile_number.setSelection(et_mobile_number.length());
                }
               /* if (word.length() == 14) {
                    btn_login.setEnabled(true);
                } else {
                    btn_login.setEnabled(false);
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_mobile_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_mobile_number.setText("+91-");
                    et_mobile_number.setSelection(et_mobile_number.length());
                }
            }
        });
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                status = false;
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                status = true;
                verification_code = s;
                Toast.makeText(getApplicationContext(), "OTP sent successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, OtpVerificationActivity.class);
                intent.putExtra("verification_code", verification_code);
                startActivity(intent);
                finish();
            }
        };
        /*tv_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*Intent intent=new Intent(LoginActivity.this,UserTypeActivity.class);
                startActivity(intent);
                finish();*//*
            }
        });*/
        tv_login_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,LanguageActivity.class);
                i.putExtra("className","Login");
                startActivity(i);
            }
        });
    }
    public void SendOtp() {
        try {
            String number = et_mobile_number.getText().toString();
            number = number.replace("-", "");
            mAppPreferences.setUserId(number);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            }
            else
            {
                mAppPreferences.setImeiNo(telephonyManager.getDeviceId());
                //mAppPreferences.setName(et_name.getText().toString());
                PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60, TimeUnit.SECONDS,this,mCallback);
            }

        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),"OTP sent failed",Toast.LENGTH_SHORT).show();
            status=false;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void Init()
    {
        btn_login=(Button)findViewById(R.id.btn_login);
        et_mobile_number=(EditText)findViewById(R.id.et_mobile_number);
        //et_name = findViewById(R.id.et_name);
        auth=FirebaseAuth.getInstance();
        telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        tv_login_logo = findViewById(R.id.tv_login_logo);
        if(mAppPreferences.getLanName()!= "")
        {
            tv_login_logo.setText(mAppPreferences.getLanName());
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );

        if(requestCode==REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = shouldShowRequestPermissionRationale( permission );
                    if (! showRationale) {
                        // user also CHECKED "never ask again"
                        // you can either enable some fall back,
                        // disable features of your app
                        // or open another dialog explaining
                        // again the permission and directing to
                        // the app setting
                        Toast.makeText( this,"Fleetown required some permission, please allow us.",Toast.LENGTH_SHORT ).show();
                        /*((ActivityManager)this.getSystemService(ACTIVITY_SERVICE))
                                .clearApplicationUserData();*/
                        /*if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                            ((ActivityManager)this.getSystemService(ACTIVITY_SERVICE))
                                    .clearApplicationUserData();
                        }*/
                        //ActivityCompat.requestPermissions(this, Constants.PERMISSIONS,REQUEST_CODE);
                    } else if (android.Manifest.permission.CAMERA.equals(permission)
                            || android.Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)
                            || android.Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)
                            || android.Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)
                            || android.Manifest.permission.ACCESS_COARSE_LOCATION.equals(permission)
                            || Manifest.permission.READ_PHONE_STATE.equals(permission)) {
                        // user did NOT check "never ask again"
                        // this is a good    place to explain the user
                        // why you need the permission and ask if he wants
                        // to accept it (the rationale)
                        //Toast.makeText( this,"persmission reason",Toast.LENGTH_SHORT ).show();
                        ActivityCompat.requestPermissions(this, AppConstants.ALL_PERMISSIONS,REQUEST_CODE);
                    } else  {
                        //Toast.makeText( this,"other111",Toast.LENGTH_SHORT ).show();
                    }
                }else{
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    //iemi = telephonyManager.getDeviceId();


                }
            }
        }
    }
    public class OTPSend extends AsyncTask<Void,Void,Void>
    {
        Context con;
        public OTPSend(Context con)
        {
            this.con = con;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(con, null, "Please Wait.....");
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //pd.hide();
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SendOtp();
            return null;
        }
    }

    public boolean Validate()
    {
        boolean status = true;
        /*if(et_name.getText().length() == 0)
        {
            Toast.makeText(Login.this,"Name can't be blank",Toast.LENGTH_SHORT).show();
            status = false;
        }*/
        if(et_mobile_number.getText().length() == 0 || et_mobile_number.getText().length() == 4)
        {
            Toast.makeText(Login.this,"Mobile no. can't be blank",Toast.LENGTH_SHORT).show();
            status = false;
        }
        return status;
    }
}
