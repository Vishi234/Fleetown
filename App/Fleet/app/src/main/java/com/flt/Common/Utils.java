package com.flt.Common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.flt.Common.AppConstants;
import com.flt.Dashboard;
import com.flt.Login;
import com.flt.R;
import com.flt.ServiceList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Utils {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void SetLocale(Context con, String localeName, String currentLanguage) {
        Locale myLocale;
        if (!localeName.equalsIgnoreCase(currentLanguage)) {
            myLocale = new Locale(localeName);
            Resources res = con.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.setLocale(myLocale);
            res.updateConfiguration(conf, dm);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static String CurrentDateTime() {
        String Mon = "";
        String currentdate;
        final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int a = cal.get(Calendar.HOUR_OF_DAY);
        int b = cal.get(Calendar.MINUTE);
        int c = cal.get(Calendar.SECOND);
        String cur_day = "";
        if (day < 10)
            cur_day = "0" + day;
        else
            cur_day = String.valueOf(day);
        if (month == 0) {
            Mon = "Jan";
        } else if (month == 1) {
            Mon = "Feb";
        } else if (month == 2) {
            Mon = "Mar";
        } else if (month == 3) {
            Mon = "Apr";
        } else if (month == 4) {
            Mon = "May";
        } else if (month == 5) {
            Mon = "Jun";
        } else if (month == 6) {
            Mon = "Jul";
        } else if (month == 7) {
            Mon = "Aug";
        } else if (month == 8) {
            Mon = "Sep";
        } else if (month == 9) {
            Mon = "Oct";
        } else if (month == 10) {
            Mon = "Nov";
        } else if (month == 11) {
            Mon = "Dec";
        }
        String cur_hour = "";
        if (a < 10)
            cur_hour = "0" + a;
        else
            cur_hour = String.valueOf(a);
        String cur_min = "";
        if (b < 10)
            cur_min = "0" + b;
        else
            cur_min = String.valueOf(b);
        String cur_sec = "";
        if (c < 10)
            cur_sec = "0" + c;
        else
            cur_sec = String.valueOf(c);
        currentdate = cur_day + "-" + Mon + "-" + year + " " + cur_hour + ":"
                + cur_min + ":" + cur_sec;
        return currentdate;
    }
    public static String CurrentDate() {
        String Mon = "";
        String currentdate;
        final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int a = cal.get(Calendar.HOUR_OF_DAY);
        int b = cal.get(Calendar.MINUTE);
        int c = cal.get(Calendar.SECOND);
        String cur_day = "";
        if (day < 10)
            cur_day = "0" + day;
        else
            cur_day = String.valueOf(day);
        if (month == 0) {
            Mon = "Jan";
        } else if (month == 1) {
            Mon = "Feb";
        } else if (month == 2) {
            Mon = "Mar";
        } else if (month == 3) {
            Mon = "Apr";
        } else if (month == 4) {
            Mon = "May";
        } else if (month == 5) {
            Mon = "Jun";
        } else if (month == 6) {
            Mon = "Jul";
        } else if (month == 7) {
            Mon = "Aug";
        } else if (month == 8) {
            Mon = "Sep";
        } else if (month == 9) {
            Mon = "Oct";
        } else if (month == 10) {
            Mon = "Nov";
        } else if (month == 11) {
            Mon = "Dec";
        }
        currentdate = cur_day + "-" + Mon + "-" + year;
        return currentdate;
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr.getActiveNetworkInfo() != null
                && connMgr.getActiveNetworkInfo().isAvailable()
                && connMgr.getActiveNetworkInfo().isConnected())
            return true;
        else {
            return false;
        }
    }

    public static void initializeHashmap(Resources resources) {
        AppConstants.classMap.put("Login",Login.class);
        AppConstants.classMap.put("Dashboard",Dashboard.class);
        AppConstants.classMap.put("ScheduledService",ServiceList.class);
        AppConstants.classMap.put("AcService", ServiceList.class);
        AppConstants.classMap.put("PaintingService", ServiceList.class);
        AppConstants.classMap.put("WheelCareService", ServiceList.class);
        AppConstants.classMap.put("CarCareService", ServiceList.class);
        AppConstants.classMap.put("CustomerService", ServiceList.class);

        AppConstants.menuIcon.put("scheduledicon", R.drawable.ic_scheduled);
        AppConstants.menuIcon.put("acserviceicon",R.drawable.ic_car_ac);
        AppConstants.menuIcon.put("paintingicon",R.drawable.ic_car_painting);
        AppConstants.menuIcon.put("wheelcareicon",R.drawable.ic_car_wheel);
        AppConstants.menuIcon.put("carcareicon",R.drawable.ic_car_care);
        AppConstants.menuIcon.put("customerserviceicon",R.drawable.ic_customer_service);
    }
    // Custom method to get assets folder image as bitmap
    public Bitmap getBitmapFromAssets(String fileName, Context con) {
        AssetManager am = con.getAssets();
        InputStream is = null;
        try {
            is = am.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

    public void addItemsOnSpinner(Spinner spinner, ArrayList<String> list, Context con) {
        /*final int mSelected = spinner.getSelectedItemPosition();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(con,	R.layout.spinner_text, list){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                //((TextView) v).setTypeface(Utils.typeFace(AddTicket.this));
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                //v.setTypeface(Utils.typeFace(AddTicket.this));
                v.setPadding(10,15,10,15);
                //v.setTextColor(getResources().getColor(R.color.input_textcolor));
				*//*if(position == mSelected){
					// Set spinner selected popup item's text color
					v.setTextColor(getResources().getColor(R.color.orange));
				}*//*
                return v;
            }
        };
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(dataAdapter);*/
    }

    public static String httpPostRequest(Context context, String url, List<NameValuePair> parameterList) {
        // System.out.println("url:: " + url);
        String str = "";
        InputStream is = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost;
        if (url.contains("https") || url.contains("http")) {
            httppost = new HttpPost(url);
        } else {
            httppost = new HttpPost("http://" + url);
        }

        try {
            httppost.setEntity(new UrlEncodedFormEntity(parameterList));
            HttpResponse httpResponse = httpclient.execute(httppost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            str = sb.toString();
            System.out.println("response::" + str);
        } catch (Exception e) {
            //e.printStackTrace();
            e.getMessage();
            //e.getMessage();
            //DataBaseHelper dbHelper = new DataBaseHelper(context);
            //AppPreferences mAppPreferences = new AppPreferences(context);
            //dbHelper.open();
            //dbHelper.insertLog("errorlog="+e.getMessage()+"-date="+date()+"-userId="+mAppPreferences.getUserId());
            //dbHelper.close();
        }
        return str;
    }
    public static String httpGetRequest(String url) throws IOException {
        final String REQUEST_METHOD = "GET";
        final int READ_TIMEOUT = 15000;
        final int CONNECTION_TIMEOUT = 15000;
        String result="";
        String inputLine;

        try
        {
            //Create a URL object holding our url
            URL myUrl = new URL(url);

            //Create a connection
            HttpURLConnection connection =(HttpURLConnection)
                    myUrl.openConnection();

            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            //Connect to our url
            connection.connect();

            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());

            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            //Check if the line we are reading is not null
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();

            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String GetString(int resId, Context con)
    {
        return con.getResources().getString(resId);
    }
    public static ArrayList<String> GetFutureDateList(int noOfDays)
    {
        ArrayList<String> futureDate = new ArrayList<String>();
        SimpleDateFormat dateParser = new SimpleDateFormat("dd-MMM-yyyy");
        Date myDate;
        String currentDate = CurrentDate();
        String newFormattedDate = "";
        try {
            for(int i = 0; i < noOfDays; i++)
            {
                myDate = dateParser.parse(currentDate);
                Calendar c = Calendar.getInstance();
                c.setTime(myDate);
                c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + i);
                Date newDate = c.getTime();
                newFormattedDate = dateParser.format(newDate);//01/21/2015
                futureDate.add(newFormattedDate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return futureDate;
    }
}