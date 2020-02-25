package com.flt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flt.Common.AppPreferences;
import com.flt.Common.Common;
import com.flt.Common.SearchableAdapter;
import com.flt.Common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehicleRegistration extends Activity {
    BottomSheetBehavior sheetBehavior;
    RelativeLayout rl_car_brand;
    ListView lst_brand,lst_make;
    View bottomSheet;
    ArrayList<String> list_brand,list_make;
    EditText et_brandsearch,et_brand;
    SearchableAdapter searchableAdapter;
    TextView bs_carbrand_cls;
    TextInputLayout text_car_brand;
    String url;
    AppPreferences mapAppPreferences;
    Button btn_submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_registration);
        Common.StatusBarColor(getWindow(),this,R.color.vehicleBgColor);
        mapAppPreferences = new AppPreferences(VehicleRegistration.this);
        Init();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(VehicleRegistration.this,UserLocationActivity.class);
                startActivity(i);
            }
        });
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        rl_car_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleBottomSheet();
            }
        });
        text_car_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleBottomSheet();
            }
        });
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        sheetBehavior.setPeekHeight(bottomSheet.getHeight());
                    }
                    break;
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });
        et_brandsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchableAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bs_carbrand_cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.setPeekHeight(0);
                ToggleBottomSheet();
            }
        });
        lst_brand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_brand.setText(list_brand.get(position).toString());
                new GetMakeTask(VehicleRegistration.this,list_brand.get(position).toString());
            }
        });
    }
    public void ToggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            //btnBottomSheet.setText("Close sheet");
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            //btnBottomSheet.setText("Expand sheet");
        }
    }
    public void Init()
    {
        rl_car_brand = findViewById(R.id.rl_car_brand);
        bottomSheet = findViewById(R.id.ll_carsheet);
        lst_brand = findViewById(R.id.lst_brand);
        et_brandsearch = findViewById(R.id.et_brandsearch);
        bs_carbrand_cls = findViewById(R.id.bs_carbrand_cls);
        text_car_brand = findViewById(R.id.text_car_brand);
        list_brand = new ArrayList<String>();//0.6
        btn_submit = findViewById(R.id.btn_submit);
        new GetModelTask(VehicleRegistration.this).execute();
    }
    public class GetModelTask extends AsyncTask<Void,Void,Void>
    {
        Context con;
        ProgressDialog pd;
        String response;
        String success , msg;
        public GetModelTask(Context con) {
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
                url = Utils.GetString(R.string.serverip,VehicleRegistration.this);
                response = Utils.httpGetRequest(url+"api/GetVehicleMake");
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
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray subArray = jsonObject.getJSONArray("Result");
                    for(int i= 0;i<subArray.length();i++){
                       list_brand.add(subArray.getJSONObject(i).getString("Title"));
                    }
                    searchableAdapter = new SearchableAdapter(VehicleRegistration.this,list_brand);
                    lst_brand.setAdapter(searchableAdapter);
                    //lst_brand.setAdapter(new SearchableAdapter(VehicleRegistration.this,list_brand));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        }
    }
    public class GetMakeTask extends AsyncTask<Void,Void,Void>
    {
        Context con;
        ProgressDialog pd;
        String response;
        String makeName;
        public GetMakeTask(Context con,String makeName) {
            this.con = con;
            this.makeName = makeName;
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

                response = Utils.httpGetRequest("http://192.168.0.103:9001/api/GetVehicleModel?makeName="+makeName);
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
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray subArray = jsonObject.getJSONArray("Result");
                    for(int i= 0;i<subArray.length();i++){
                        list_make.add(subArray.getJSONObject(i).getString("Title"));
                    }
                    searchableAdapter = new SearchableAdapter(VehicleRegistration.this,list_make);
                    lst_make.setAdapter(searchableAdapter);
                    //lst_brand.setAdapter(new SearchableAdapter(VehicleRegistration.this,list_brand));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        }
    }
}
