package com.flt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flt.Bean.BeanServiceRpt;
import com.flt.Bean.BeanUserInfo;
import com.flt.Common.AppPreferences;
import com.flt.Common.Common;
import com.flt.Common.SmsListener;
import com.flt.Common.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Verify;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OtpVerificationActivity extends Activity {
    TextView tv;
    ProgressDialog pd;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    boolean status=false;
    String verification_code;
    EditText et_first,et_second,et_third,et_forth,et_fifth,et_sixth;
    AppPreferences mAppPreferences;
    String value = "";
    Button btn_login;
    boolean flag = false;
    LoginTask loginTask = null;
    GetUserInfoTask infoTask = null;
    CheckUserInfo userInfoTask = null;
    BeanUserInfo response_user_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        Common.StatusBarColor(getWindow(),this,R.color.otpBGColor);
        mAppPreferences = new AppPreferences(OtpVerificationActivity.this);
        Init();
        Timer();
        mCallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                status=false;
                if (pd !=null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(OtpVerificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                if (pd !=null && pd.isShowing()) {
                    pd.dismiss();
                }
                status=true;
                verification_code = s;
                Toast.makeText(getApplicationContext(),"OTP sent successfully",Toast.LENGTH_SHORT).show();
                Timer();
                ReadOtp();
            }
        };
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfoTask = new CheckUserInfo(OtpVerificationActivity.this);
                userInfoTask.execute();
                /*if(flag == false)
                {
                    Verify(value);
                }
                else
                {
                    infoTask = new GetUserInfoTask(OtpVerificationActivity.this,mAppPreferences.getUserId());
                    infoTask.execute();
                }*/
                /*Intent intent=new Intent(OtpVerificationActivity.this, BasicInfo.class);
                startActivity(intent);
                finish();*/
            }
        });
        et_first.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                ((EditText) view).setSelection(((EditText) view).getText().length());
            }
        });
        et_second.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                ((EditText) view).setSelection(((EditText) view).getText().length());
            }
        });
        et_third.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                ((EditText) view).setSelection(((EditText) view).getText().length());
            }
        });
        et_forth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                ((EditText) view).setSelection(((EditText) view).getText().length());
            }
        });
        et_fifth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                ((EditText) view).setSelection(((EditText) view).getText().length());
            }
        });
        et_sixth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                ((EditText) view).setSelection(((EditText) view).getText().length());
            }
        });
        et_first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_first.length() == 1)
                {
                    et_first.clearFocus();
                    et_second.requestFocus();
                    et_second.setCursorVisible(true);
                }
                else
                {
                    et_first.requestFocus();
                    et_first.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_second.length() == 1)
                {

                    et_second.clearFocus();
                    et_third.requestFocus();
                    et_third.setCursorVisible(true);
                }
                else
                {
                    et_second.clearFocus();
                    et_first.requestFocus();
                    et_first.setCursorVisible(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_third.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_third.length() == 1)
                {

                    et_third.clearFocus();
                    et_forth.requestFocus();
                    et_forth.setCursorVisible(true);
                }
                else
                {
                    et_third.clearFocus();
                    et_second.requestFocus();
                    et_second.setCursorVisible(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_forth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_forth.length() == 1)
                {

                    et_forth.clearFocus();
                    et_fifth.requestFocus();
                    et_fifth.setCursorVisible(true);
                }
                else
                {
                    et_forth.clearFocus();
                    et_third.requestFocus();
                    et_third.setCursorVisible(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_fifth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_fifth.length() > 0)
                {

                    et_fifth.clearFocus();
                    et_sixth.requestFocus();
                    et_sixth.setCursorVisible(true);
                }
                else
                {
                    et_fifth.clearFocus();
                    et_forth.requestFocus();
                    et_forth.setCursorVisible(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_sixth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_sixth.length() > 0)
                {

                    et_sixth.clearFocus();
                    btn_login.requestFocus();
                }
                else
                {
                    et_sixth.clearFocus();
                    et_fifth.requestFocus();
                    et_fifth.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ReadOtp();
    }
    public void Timer()
    {
        CountDownTimer timer=new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv.setText("Resend OTP "+ millisUntilFinished / 1000+"s");
            }

            @Override
            public void onFinish() {
                tv.setText(Html.fromHtml("<a href=\\\"javascript:void(0)\\\" style='text-decoration:underline;'>Resend OTP</a>"));
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new OTPSend(OtpVerificationActivity.this).execute();
                    }
                });
            }
        }.start();
    }
    public void ReadOtp()
    {
        OTPReciever.bindListener(new SmsListener() {
            @Override
            public void RecievedOtp(String otp) {
                value = otp;
                et_first.setText(String.valueOf(value.charAt(0)));
                et_second.setText(String.valueOf(value.charAt(1)));
                et_third.setText(String.valueOf(value.charAt(2)));
                et_forth.setText(String.valueOf(value.charAt(3)));
                et_fifth.setText(String.valueOf(value.charAt(4)));
                et_sixth.setText(String.valueOf(value.charAt(5)));
                Toast.makeText(OtpVerificationActivity.this, otp, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Init()
    {
        tv=(TextView)findViewById(R.id.tv_resend_otp);
        et_first=(EditText)findViewById(R.id.et_first);
        et_first.requestFocus();
        et_first.setCursorVisible(true);
        //        /*et_first.setEnabled(false);*/
        et_second=(EditText)findViewById(R.id.et_second);
        //        /*et_second.setEnabled(false);*/
        et_third=(EditText)findViewById(R.id.et_third);
                /*et_third.setEnabled(false);*/
        et_forth=(EditText)findViewById(R.id.et_forth);
                /*et_forth.setEnabled(false);*/
        et_fifth=(EditText)findViewById(R.id.et_fifth);
                /*et_fifth.setEnabled(false);*/
        et_sixth=(EditText)findViewById(R.id.et_sixth);
                /*et_sixth.setEnabled(false);*/
        btn_login=(Button)findViewById(R.id.btn_login);
        auth=FirebaseAuth.getInstance();
        verification_code = getIntent().getExtras().getString("verification_code");
    }
    public void SendOtp()
    {
        try
        {
            String number = mAppPreferences.getUserId();
            number = number.replace("-","");
            PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60, TimeUnit.SECONDS,this,mCallback);
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),"OTP sent failed",Toast.LENGTH_SHORT).show();
            status = false;
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
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SendOtp();
            return null;
        }
    }
    public void SignInWithPhone(PhoneAuthCredential credential)
    {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            if (pd !=null && pd.isShowing()) {
                                pd.dismiss();
                            }

                            flag = true;
                            infoTask = new GetUserInfoTask(OtpVerificationActivity.this,mAppPreferences.getUserId());
                            infoTask.execute();
                        }
                        else
                        {
                            if (pd !=null && pd.isShowing()) {
                                pd.dismiss();
                            }
                            Toast.makeText(getApplicationContext(),"Invalid OTP.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void Verify(String otp) {
        pd = ProgressDialog.show(OtpVerificationActivity.this, null, "Please Wait.....");
        try
        {
            if(CheckEmptyEditText() == true && otp != "" && verification_code != "" && value != "")
            {
                pd.show();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification_code, otp);
                SignInWithPhone(credential);
            }
            else
            {
                if(value == "")
                {
                    if(CheckEmptyEditText()==false)
                    {
                        pd.hide();
                        Toast.makeText(OtpVerificationActivity.this,"Invalid OTP",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        otp = et_first.getText().toString()+""+et_second.getText().toString()+""+et_third.getText().toString()+""+
                                et_forth.getText().toString()+""+et_fifth.getText().toString()+""+et_sixth.getText().toString();
                        pd.show();
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification_code, otp);
                        SignInWithPhone(credential);

                    }
                }
                else
                {
                    pd.hide();
                    Toast.makeText(OtpVerificationActivity.this,"Invalid OTP",Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception ex)
        {
            pd.hide();
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean CheckEmptyEditText()
    {
        boolean status = true;
        LinearLayout parent = findViewById(R.id.ll_otp_field);
        for(int i = 0; i < parent.getChildCount(); i++) {
            View singleChild = parent.getChildAt(i);
            if(singleChild instanceof EditText) {
                EditText mEditText = (EditText)singleChild;
                if(mEditText.getText().length() == 0){
                    status = false;
                }
            }
        }
        return status;
    }
    public class CheckUserInfo extends AsyncTask<Void,Void,Void>
    {
        Context con;
        ProgressDialog pd;
        String response;
        String success , msg;
        public CheckUserInfo(Context con) {
            this.con = con;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show( con, null, "Please Wait..." );
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>( 7 );
            nameValuePairs.add( new BasicNameValuePair( "UserId", mAppPreferences.getUserId().substring(3,mAppPreferences.getUserId().length()) ) );
            nameValuePairs.add( new BasicNameValuePair( "Name", "" ) );
            nameValuePairs.add( new BasicNameValuePair( "ImeiNo", mAppPreferences.getImeiNo() ) );
            nameValuePairs.add( new BasicNameValuePair( "Language", mAppPreferences.getLanCode() ) );
            nameValuePairs.add( new BasicNameValuePair( "Email", "" ) );
            nameValuePairs.add( new BasicNameValuePair( "Latitude", mAppPreferences.getLat()));
            nameValuePairs.add( new BasicNameValuePair( "Longitude", mAppPreferences.getLong() ) );
            String url = Utils.GetString(R.string.serverip,OtpVerificationActivity.this);
            response = Utils.httpPostRequest(con,url+"api/CheckUserInfo",nameValuePairs);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            if (response != null && response.length()>0)
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray subArray = jsonObject.getJSONArray("Result");
                    success = subArray.getJSONObject(0).getString("FLAG");
                    msg = subArray.getJSONObject(0).getString("MSG");

                    Toast.makeText(OtpVerificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                    if (success.equals("A")) {
                        Intent intent = new Intent(OtpVerificationActivity.this, BasicInfo.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(success.equals("U"))
                    {
                       if(subArray.getJSONObject(0).getString("Address").equals(""))
                       {
                           Intent intent = new Intent(OtpVerificationActivity.this, UserLocationActivity.class);
                           startActivity(intent);
                           finish();
                       }
                       else
                       {
                           Intent intent = new Intent(OtpVerificationActivity.this, Dashboard.class);
                           startActivity(intent);
                           finish();
                       }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            super.onPostExecute(aVoid);
        }
    }
}
