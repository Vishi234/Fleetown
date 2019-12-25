package com.flt.Home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flt.Common.AppPreferences;
import com.flt.Common.SmsListener;
import com.flt.Dashboard.Dashboard;
import com.flt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

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
    String value;
    Button btn_login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
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
                Verify(value);
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
        et_first.setEnabled(false);
        et_second=(EditText)findViewById(R.id.et_second);
        et_second.setEnabled(false);
        et_third=(EditText)findViewById(R.id.et_third);
        et_third.setEnabled(false);
        et_forth=(EditText)findViewById(R.id.et_forth);
        et_forth.setEnabled(false);
        et_fifth=(EditText)findViewById(R.id.et_fifth);
        et_fifth.setEnabled(false);
        et_sixth=(EditText)findViewById(R.id.et_sixth);
        et_sixth.setEnabled(false);
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
                            Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(OtpVerificationActivity.this, Dashboard.class);
                            startActivity(intent);
                            finish();
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
        pd.show();
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification_code, otp);
        //signing the user
        SignInWithPhone(credential);
    }
}
