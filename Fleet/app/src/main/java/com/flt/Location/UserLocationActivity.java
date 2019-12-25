package com.flt.Location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.flt.Common.PlaceAdapter;
import com.flt.Dashboard.Dashboard;
import com.flt.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserLocationActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    LinearLayout ll_use_gps;
    private ProgressBar progressBar;
    ListView user_loc_list;
    LinearLayout ll_user_loc, ll_user_gps_base;
    EditText txtTextSearch;
    PlaceAdapter placeAdapter;
    private Location mylocation;
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;
    protected Geocoder geocoder;
    LocationRequest locationRequest;
    List<Address> addressList;
    LocationSettingsRequest.Builder builder;
    int locationflag=0;
    ProgressDialog pd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Init();

        ll_use_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CheckPermission();
                new EnableGps(UserLocationActivity.this).execute();
            }
        });
        txtTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new LocationTask(
                        UserLocationActivity.this,
                        s.toString(),
                        progressBar,
                        user_loc_list,
                        ll_user_loc,
                        ll_user_gps_base,
                        placeAdapter
                ).execute();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        user_loc_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new GetAddress(UserLocationActivity.this , user_loc_list.getItemAtPosition(position).toString()).execute();
            }
        });
    }
    public  void Init() {
        ll_use_gps = (LinearLayout) findViewById(R.id.ll_user_gps);
        txtTextSearch = (EditText) findViewById(R.id.txtTextSearch);
        ll_user_loc = (LinearLayout) findViewById(R.id.ll_user_loc);
        ll_user_gps_base = (LinearLayout) findViewById(R.id.ll_user_gps_base);
        user_loc_list = (ListView) findViewById(R.id.user_loc_list);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        geocoder = new Geocoder(this);
        SetupGoogleClient();

    }
    private synchronized void SetupGoogleClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }
    private void CheckPermission(int flag){
        int permissionLocation = ContextCompat.checkSelfPermission(UserLocationActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }else{
            GetMyLocation(flag);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(UserLocationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            GetMyLocation(1);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
            Double latitude=mylocation.getLatitude();
            Double longitude=mylocation.getLongitude();
            Geocoder geocoder = new Geocoder(UserLocationActivity.this);
            addressList = null;
            try {
                addressList = geocoder.getFromLocation(latitude, longitude, 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addressList!=null)
            {
                Address address = addressList.get(0);
                if(locationflag == 0)
                {
                    if (pd !=null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Intent intent=new Intent(UserLocationActivity.this, Dashboard.class);
                    startActivity(intent);
                    locationflag = 1;
                    finish();

                }
            }
            //latitudeTextView.setText("Latitude : "+latitude);
            //longitudeTextView.setText("Longitude : "+longitude);
            //Or Do whatever you want with your location
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        GetMyLocation(1);
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }
    }

    public void GetMyLocation(int flag)
    {
        if(googleApiClient!=null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(UserLocationActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                }
                if(flag==1)
                {    FetchLocation(locationRequest,builder);

                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    public void FetchLocation(LocationRequest locationRequest, LocationSettingsRequest.Builder builder)
    {
        LocationServices.FusedLocationApi
                .requestLocationUpdates(googleApiClient, locationRequest, UserLocationActivity.this);
        PendingResult result =
                LocationServices.SettingsApi
                        .checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback() {

            @Override
            public void onResult(@NonNull Result result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied.
                        // You can initialize location requests here.
                        int permissionLocation = ContextCompat
                                .checkSelfPermission(UserLocationActivity.this,
                                        Manifest.permission.ACCESS_FINE_LOCATION);
                        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                            mylocation = LocationServices.FusedLocationApi
                                    .getLastLocation(googleApiClient);
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied.
                        // But could be fixed by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            // Ask to turn on GPS automatically
                            status.startResolutionForResult(UserLocationActivity.this,
                                    REQUEST_CHECK_SETTINGS_GPS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        //finish();
                        break;
                }
            }
        });
    }
    public class LocationTask extends AsyncTask<Void,Void,Void>
    {
        Context con;
        String searchText="";
        private ProgressBar progressBar;
        ListView user_loc_list;
        LinearLayout ll_user_loc,ll_user_gps_base;
        PlaceAdapter placeAdapter=null;
        public LocationTask(Context con,String searchText,ProgressBar progressBar,ListView user_loc_list,LinearLayout ll_user_loc, LinearLayout ll_user_gps_base, PlaceAdapter placeAdapter)
        {
            this.con = con;
            this.searchText = searchText;
            this.progressBar = progressBar;
            this.user_loc_list = user_loc_list;
            this.ll_user_loc = ll_user_loc;
            this.ll_user_gps_base = ll_user_gps_base;
            this.placeAdapter = placeAdapter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (searchText != "")
            {
                ll_user_gps_base.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                user_loc_list.setVisibility(View.GONE);
                ll_user_loc.setVisibility(View.VISIBLE);
            }
            else
            {
                ll_user_loc.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                user_loc_list.setVisibility(View.GONE);
                ll_user_gps_base.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (placeAdapter != null)
            {
                progressBar.setVisibility(View.GONE);
                user_loc_list.setVisibility(View.VISIBLE);
                ll_user_loc.setVisibility(View.VISIBLE);
                user_loc_list.setAdapter(placeAdapter);
            }
            else
            {
                ll_user_loc.setVisibility(View.GONE);
                ll_user_gps_base.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (searchText != "")
            {
                placeAdapter = new PlaceAdapter(con, new PlaceApi().AutoComplete(searchText));
            }
            else
            {
                ll_user_loc.setVisibility(View.GONE);
                ll_user_gps_base.setVisibility(View.VISIBLE);
            }
            return null;
        }
    }

    public class EnableGps extends AsyncTask<Void,Void,Void>
    {
        Context con;
        public EnableGps(Context con)
        {
            this.con = con;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(con, null, "Getting your location...");
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            FetchLocation(locationRequest,builder);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            CheckPermission(2);
            return null;
        }
    }
    public class GetAddress extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog pd;
        Context con;
        String address="";
        public GetAddress(Context con,String add)
        {
            this.con = con;
            this.address = add;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(con, null, "Please Wait...");
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(con, Dashboard.class);
            startActivity(intent);
            finish();
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Toast.makeText(con, address, Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
