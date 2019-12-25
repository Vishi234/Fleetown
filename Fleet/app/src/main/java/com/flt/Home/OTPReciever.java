package com.flt.Home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.flt.Common.SmsListener;

public class OTPReciever extends BroadcastReceiver {
    private static SmsListener mListener;
    Boolean b;
    String abcd,xyz;

    @Override
    public void onReceive(Context context, Intent intent) {
        try
        {
            Bundle data  = intent.getExtras();
            Object[] pdus = (Object[]) data.get("pdus");
            for(int i=0;i<pdus.length;i++){
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                String sender = smsMessage.getDisplayOriginatingAddress();
                b=sender.endsWith("57575454");  //Just to fetch otp sent from WNRCRP
                String messageBody = smsMessage.getMessageBody();
                abcd = messageBody.replaceAll("[^0-9]","");   // here abcd contains otp
                //abcd="234567";
                //which is in number format
                //Pass on the text to our listener.
                if(b==true) {
                    mListener.RecievedOtp(abcd);  // attach value to interface object
                }
                else
                {

                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
