package com.flt.Common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.flt.R;

import java.util.ArrayList;
import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private static final String TAG = "CommonSpinnerAdapter";
    private List<String> items;
    Context context;
    public CustomSpinnerAdapter(Context context,List<String> items ) {
        super(context,0,items);
        this.context = context;
        this.items = items;
    }

    @Override
    public void add(String value) {
        items.add(value);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int index) {
        return items.get(index);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_custom_spinner_items,parent,false);
        }
        TextView tv_location_name=(TextView)convertView.findViewById(R.id.itemname);
        tv_location_name.setText(items.get(position));
        return convertView;
    }
}
