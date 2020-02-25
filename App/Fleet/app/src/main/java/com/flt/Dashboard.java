package com.flt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flt.Common.AppConstants;
import com.flt.Common.AppPreferences;
import com.flt.Common.Common;
import com.flt.Common.CustomGrid;
import com.flt.Common.ResponsiveGridView;
import com.flt.Common.SearchableAdapter;
import com.flt.Common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Dashboard extends Activity {
    ArrayList<Integer> lst_icon;
    ArrayList<Class> lst_class;
    ArrayList<String> lst_name;
    CustomGrid gridAdapter;
    CardView cv_free_trial;
    ResponsiveGridView grid;
    TextView tv_user_profile,tv_user_address;
    AppPreferences mAppPreferences;
    LinearLayout ll_loc_dashboard;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Common.StatusBarColor(getWindow(),this,R.color.header);
        mAppPreferences = new AppPreferences(this);
        Init();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Dashboard.this,ServiceList.class);
                i.putExtra("serviceName",lst_name.get(position).toString());
                startActivity(i);
            }
        });
    }
    public void Init()
    {
        lst_icon = new ArrayList<Integer>();
        lst_name = new ArrayList<String>();
        lst_class = new ArrayList<Class>();
        Utils.initializeHashmap(getResources());
        grid = findViewById(R.id.resgrid_grid);
        new GetMainMenu(Dashboard.this).execute();
    }
    public class GetMainMenu extends AsyncTask<Void,Void,Void>
    {
        Context con;
        ProgressDialog pd;
        String response;
        public GetMainMenu(Context con) {
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
                String url = Utils.GetString(R.string.serverip,Dashboard.this);
                response = Utils.httpGetRequest(url + "api/GetMainMenu");
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
                        lst_name.add(subArray.getJSONObject(i).getString("Name"));
                        lst_icon.add(AppConstants.menuIcon.get(subArray.getJSONObject(i).getString("Icon")));
                        lst_class.add(AppConstants.classMap.get(subArray.getJSONObject(i).getString("JavaClass")));
                    }
                    gridAdapter = new CustomGrid(Dashboard.this,lst_icon,lst_name);
                    grid.setFastScrollEnabled(true);
                    grid.setAdapter(gridAdapter);
                    grid.setExpanded(true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
        }
    }
}
