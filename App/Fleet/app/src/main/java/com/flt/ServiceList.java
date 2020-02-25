package com.flt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.flt.Bean.BeanServiceList;
import com.flt.Bean.BeanServiceRpt;
import com.flt.Common.Common;
import com.flt.Common.DataBaseHelper;
import com.flt.Common.SearchableAdapter;
import com.flt.Common.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ServiceList extends Activity {
    BeanServiceRpt response_service_rpt = null;
    ListView lst_service_list;
    TextView tv_brand_logo,cart_badge;
    String name;
    DataBaseHelper db;
    int count;
    final Handler handler = new Handler();
    FrameLayout fl_cart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        Common.StatusBarColor(getWindow(),this,R.color.header);
        db = new DataBaseHelper(this);
        db.open();
        Init();
        name = getIntent().getExtras().getString("serviceName");
        tv_brand_logo.setText(name);

        fl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ServiceList.this,CartItems.class);
                startActivity(i);
            }
        });
        new GetServiceList(this,name).execute();
    }
    public void Init()
    {
        lst_service_list = findViewById(R.id.lst_service_list);
        tv_brand_logo = findViewById(R.id.tv_brand_logo);
        cart_badge = findViewById(R.id.cart_badge);
        fl_cart = findViewById(R.id.fl_cart);
        count = db.getCartItems("").getResult().size();
        if(count == 0)
        {
            cart_badge.setVisibility(View.GONE);
            response_service_rpt = null;
        }
        else if(count == 1 && db.getCartItems("").getResult().get(0).getName() == null)
        {
            cart_badge.setVisibility(View.GONE);
            response_service_rpt = null;
        }
        else
        {
            cart_badge.setVisibility(View.VISIBLE);
            cart_badge.setText(String.valueOf(count));
        }
    }
    public class GetServiceList extends AsyncTask<Void,Void,Void>
    {
        Context con;
        ProgressDialog pd;
        String response;
        String serviceName;
        public GetServiceList(Context con,String makeName) {
            this.con = con;
            this.serviceName = makeName;
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
                String url = Utils.GetString(R.string.serverip,ServiceList.this);
                String query = URLEncoder.encode(this.serviceName, "utf-8");
                response = Utils.httpGetRequest(url + "api/GetServiceList?serviceName="+query);
                Gson gson = new Gson();
                response_service_rpt = gson.fromJson(response,BeanServiceRpt.class);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                response = null;
            }
            return null;

        }
        @Override
        protected void onPostExecute(Void result) {
            if(response != null)
            {
                if(response_service_rpt.getResult().get(0).getName() == null)
                {
                    Toast.makeText(con, "Server not available", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ServiceList.this,ComingSoon.class);
                    startActivity(i);
                    finish();
                }
                else if(response_service_rpt.getResult() != null && response_service_rpt.getResult().size() > 0)
                {
                    lst_service_list.setAdapter(new AdapterServiceList(ServiceList.this,response,cart_badge));
                }
                else
                {
                    Intent i = new Intent(ServiceList.this,ComingSoon.class);
                    startActivity(i);
                    finish();
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
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
}
