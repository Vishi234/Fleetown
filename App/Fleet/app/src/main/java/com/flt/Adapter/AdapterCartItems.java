package com.flt.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flt.Bean.BeanServiceRpt;
import com.flt.Common.AppPreferences;
import com.flt.Common.DataBaseHelper;
import com.flt.Common.Utils;
import com.flt.R;
import com.google.gson.Gson;

import java.text.DecimalFormat;

public class AdapterCartItems extends BaseAdapter {
    Context con;
    private LayoutInflater inflater = null;
    BeanServiceRpt data_list;
    int actualPrice,disPer,discountPrice = 0;
    DecimalFormat numFormat;
    public AdapterCartItems(Context con, BeanServiceRpt data) {
        this.con = con;
        this.data_list = data;
        numFormat = new DecimalFormat("#,##,###.##");
        numFormat.setMinimumFractionDigits(2);
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data_list.getResult().size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View arg1, ViewGroup parent) {
        View vi = arg1;
        if (arg1 == null)
            vi = inflater.inflate(R.layout.activity_cart_item_list, null);

        TextView tv_sername = (TextView) vi.findViewById(R.id.tv_sername);
        if(data_list.getResult().get(position).getName() != null && data_list.getResult().get(position).getName().length() != 0)
        {
            tv_sername.setText(data_list.getResult().get(position).getName());
        }
        TextView tv_discount = (TextView) vi.findViewById(R.id.tv_discount);
        actualPrice = Integer.parseInt(data_list.getResult().get(position).getPrice());
        if(data_list.getResult().get(position).getDiscount() != null && data_list.getResult().get(position).getDiscount().length() != 0)
        {
            disPer = Integer.parseInt(data_list.getResult().get(position).getDiscount());
            discountPrice =(actualPrice * disPer) / 100;
            tv_discount.setText(numFormat.format(actualPrice));
            tv_discount.setPaintFlags(tv_discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
        {
            tv_discount.setVisibility(View.GONE);
        }

        TextView tv_actualprice = (TextView) vi.findViewById(R.id.tv_actualprice);
        if(data_list.getResult().get(position).getPrice() != null && data_list.getResult().get(position).getPrice().length() > 0)
        {
            if(discountPrice != 0)
            {
                actualPrice = actualPrice - discountPrice;
                tv_actualprice.setText(numFormat.format(actualPrice));
            }
            else
            {
                actualPrice = Integer.parseInt(data_list.getResult().get(position).getPrice());
                tv_actualprice.setText(numFormat.format(actualPrice));
            }
        }
        return vi;
    }
}
