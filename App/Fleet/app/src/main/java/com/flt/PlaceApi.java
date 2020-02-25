package com.flt;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class PlaceApi {
    public List<String> AutoComplete(String input)
    {
        List<String> arrayList= new ArrayList<>();
        StringBuilder jsonResult=new StringBuilder();
        String html="";
        InputStream is;
        try
        {
            StringBuilder sb=new StringBuilder("https://maps.googleapis.com/maps/api/place/autocomplete/json?");
            sb.append("input="+input);
            sb.append("&key=AIzaSyBb1-CCxWk4r7byFIduqkNOc9QPPxSdcyA");
            sb.append("&region=in");
            URL url=new URL(sb.toString());
            HttpsURLConnection con=(HttpsURLConnection)url.openConnection();

            SSLContext sc;
            sc=SSLContext.getInstance("TLS");
            sc.init(null,null,new java.security.SecureRandom());
            con.setSSLSocketFactory(sc.getSocketFactory());

            con.setReadTimeout(7000);
            con.setConnectTimeout(7000);
            con.setRequestMethod("POST");
            con.setDoInput(true);

            con.connect();
            is=con.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                html += inputLine;
            }

            JSONObject jsonObject = new JSONObject(html);
            JSONArray prediction = jsonObject.getJSONArray("predictions");
            for (int i = 0; i < prediction.length(); i++)
            {
                arrayList.add(prediction.getJSONObject(i).getString("description"));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return  arrayList;
    }
}
