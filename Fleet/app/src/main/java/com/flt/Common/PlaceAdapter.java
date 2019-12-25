package com.flt.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.flt.Dashboard.Dashboard;
import com.flt.R;

import java.util.List;

public class PlaceAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> places;

    public PlaceAdapter(Context context,List<String> places)
    {
        super(context,0,places);
        this.context=context;
        this.places=places;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_location_list_view,parent,false);
        }
        TextView tv_location_name=(TextView)convertView.findViewById(R.id.locationName);
        tv_location_name.setText(places.get(position));
        return convertView;
    }
}

