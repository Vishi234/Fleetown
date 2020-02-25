package com.flt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.flt.Adapter.RecyclerAdapterTimeSlot;
import com.flt.Bean.BeanParam;
import com.flt.Common.Utils;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

public class OtherInformation extends Activity {
    ArrayList<String> futureDateList;
    BeanParam response_param = null;
    RecyclerView mylist , timeview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager,timeLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherinformation);
        Init();
    }
    public void Init()
    {
        futureDateList = new ArrayList<String>();
        futureDateList = Utils.GetFutureDateList(10);
        mylist = findViewById(R.id.recyclerView);
        timeview = findViewById(R.id.timeview);
        mylist.setHasFixedSize(true);
        timeview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mAdapter = new RecyclerAdapterTimeSlot(response_param,futureDateList);
        mylist.setLayoutManager(mLayoutManager);
        mylist.setAdapter(mAdapter);
        timeLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        timeview.setLayoutManager(timeLayoutManager);
        new GetTimeSlot(this).execute();
    }
    public class GetTimeSlot extends AsyncTask<Void,Void,Void>
    {
        Context con;
        ProgressDialog pd;
        String response;
        public GetTimeSlot(Context con) {
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
                String url = Utils.GetString(R.string.serverip,OtherInformation.this);
                String query = URLEncoder.encode(String.valueOf(1), "utf-8");
                response = Utils.httpGetRequest(url + "api/GetParamList?paramType="+query);
                Gson gson = new Gson();
                response_param = gson.fromJson(response,BeanParam.class);
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
            if (response != null && response.length()>0) {
               if(response_param.getResult().size() > 0)
               {
                   mAdapter = new RecyclerAdapterTimeSlot(response_param,null);
                   timeview.setAdapter(mAdapter);
               }
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        }
    }
}
