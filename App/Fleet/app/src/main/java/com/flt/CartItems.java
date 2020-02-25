package com.flt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flt.Adapter.AdapterCartItems;
import com.flt.Bean.BeanServiceRpt;
import com.flt.Common.DataBaseHelper;
import com.flt.Common.NonScrollListView;
import com.flt.Common.Utils;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.text.DecimalFormat;

public class CartItems extends Activity {
    DataBaseHelper db;
    BeanServiceRpt response_service_rpt;
    NonScrollListView list_cart_items;
    TextView tv_item_total,tv_item_discount,tv_order_total,tv_final_amount,tv_doorstep_charges;
    final Handler handler = new Handler();
    double actualPrice,discountPrice,disPer,totalPrice,totalDiscount,temp,afterDiscount,tempAfter = 0;
    RelativeLayout rl_discount_block;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_items);
        db = new DataBaseHelper(this);
        db.open();
        Init();
        new GetServiceList(this).execute();
    }
    public void Init()
    {
        list_cart_items = findViewById(R.id.list_cart_items);
        tv_item_total = findViewById(R.id.tv_item_total);
        tv_item_discount = findViewById(R.id.tv_item_discount);
        tv_order_total = findViewById(R.id.tv_order_total);
        tv_final_amount = findViewById(R.id.tv_final_amount);
        tv_doorstep_charges =findViewById(R.id.tv_doorstep_charges);
        rl_discount_block =findViewById(R.id.rl_discount_block);
    }
    public class GetServiceList extends AsyncTask<Void,Void,Void>
    {
        Context con;
        ProgressDialog pd;
        public GetServiceList(Context con) {
            this.con = con;
        }
        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show( con, null, "Please Wait..." );
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                response_service_rpt = db.getCartItems("");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                response_service_rpt = null;
            }
            return null;

        }
        @Override
        protected void onPostExecute(Void result) {
            if(response_service_rpt != null)
            {
                if(response_service_rpt.getResult().get(0).getName() == null)
                {
                    Toast.makeText(con, "Server not available", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CartItems.this,ComingSoon.class);
                    startActivity(i);
                    finish();
                }
                else if(response_service_rpt.getResult() != null && response_service_rpt.getResult().size() > 0)
                {
                    list_cart_items.setAdapter(new AdapterCartItems(CartItems.this,response_service_rpt));
                }
                else
                {
                    Intent i = new Intent(CartItems.this,ComingSoon.class);
                    startActivity(i);
                    finish();
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        PriceCalculation();
                    }
                }, 1000);
            }
            else
            {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }


        }
    }
    public void PriceCalculation()
    {
        for(int i = 0 ; i < response_service_rpt.getResult().size() ; i++)
        {
            actualPrice = Integer.parseInt(response_service_rpt.getResult().get(i).getPrice());
            if(response_service_rpt.getResult().get(i).getDiscount() != null && response_service_rpt.getResult().get(i).getDiscount().length() != 0)
            {
                disPer = Integer.parseInt(response_service_rpt.getResult().get(i).getDiscount());
                discountPrice =(actualPrice * disPer) / 100;
            }
            if(discountPrice != 0)
            {
                afterDiscount = actualPrice - discountPrice;
            }
            else
            {
                afterDiscount = Integer.parseInt(response_service_rpt.getResult().get(i).getPrice());
            }
            totalPrice = actualPrice + totalPrice;
            temp = discountPrice + temp;
            totalDiscount = temp*100/totalPrice;
            tempAfter = afterDiscount + tempAfter;
        }
        if(discountPrice == 0)
        {
            rl_discount_block.setVisibility(View.GONE);
        }
        else
        {
            rl_discount_block.setVisibility(View.VISIBLE);
        }
        DecimalFormat numFormat = new DecimalFormat("#,##,###.##");
        numFormat.setMinimumFractionDigits(2);
        tv_item_total.setText(numFormat.format(totalPrice));
        tv_item_discount.setText(numFormat.format(totalDiscount)+"%");
        tv_order_total.setText(numFormat.format(afterDiscount));
        tv_final_amount.setText(numFormat.format(tempAfter + Integer.parseInt(tv_doorstep_charges.getText().toString())));
    }
}
