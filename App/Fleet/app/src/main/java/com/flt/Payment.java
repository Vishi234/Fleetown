package com.flt;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flt.Common.Utils;

import java.util.ArrayList;

public class Payment extends Activity {
    TextView tv_googlepay,tv_afterservice,tv_amount;
    String amount,note,name,upiId;
    final int UPI_PAYMENT = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Init();
        tv_googlepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUsingUpi(amount,upiId,name,note);
            }
        });
    }

    private void payUsingUpi(String amount, String upiId, String name, String note) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa",upiId)
                .appendQueryParameter("pn",name)
                .appendQueryParameter("tn",note)
                .appendQueryParameter("am",amount)
                .appendQueryParameter("cu","INR")
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent,"Pay With");

        if(null != chooser.resolveActivity(getPackageManager()))
        {
            startActivityForResult(chooser,UPI_PAYMENT);
        }
        else
        {
            Toast.makeText(this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }
    }

    public void Init()
    {
        tv_amount = findViewById(R.id.tv_amount);
        tv_googlepay = findViewById(R.id.tv_googlepay);
        tv_afterservice = findViewById(R.id.tv_afterservice);
        amount = tv_amount.getText().toString();
        note = "Fleetown car washing service at doorstep";
        name = "Vishal Singh";
        upiId = "v9650402952@okicici";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case UPI_PAYMENT:
                if(RESULT_OK == resultCode || (resultCode == 11))
                {
                    if(data != null)
                    {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI","onActivityResult:"+trxt);
                        ArrayList<String> dataList = new ArrayList<String>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    }
                    else
                    {
                        Log.d("UPI","onActivityResult: "+" Return data is null");
                        ArrayList<String> dataList = new ArrayList<String>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                }
                else
                {
                    Log.d("UPI","onActivityResult: "+" Return data is null");
                    ArrayList<String> dataList = new ArrayList<String>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> dataList) {
        if(Utils.isNetworkAvailable(this))
        {
            String str = dataList.get(0);
            Log.d("UPIPAY","upiPaymentDataOperation:"+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for(int i = 0 ; i<response.length ; i++)
            {
                String equalStr[] = response[i].split("=");
                if(equalStr.length > 2)
                {
                    if(equalStr[0].toLowerCase().equals("Status".toLowerCase()))
                    {
                        status = equalStr[1].toLowerCase();
                    }
                    else if(equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txtRef".toLowerCase()))
                    {
                        approvalRefNo = equalStr[1];
                    }
                }
                else
                {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if(status.equals("success"))
            {
                Toast.makeText(this, "Transaction successfull", Toast.LENGTH_SHORT).show();
                Log.d("UPI","responseStr:"+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel))
            {
                Toast.makeText(this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Internation connection is not available. Please check try again", Toast.LENGTH_SHORT).show();
        }
    }
}
