package com.flt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flt.Bean.BeanServiceList;
import com.flt.Bean.BeanServiceRpt;
import com.flt.Common.AppPreferences;
import com.flt.Common.Common;
import com.flt.Common.DataBaseHelper;
import com.flt.Common.Utils;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class AdapterServiceList extends BaseAdapter {
    Context con;
    private LayoutInflater inflater = null;
    BeanServiceRpt data_list;
    DataBaseHelper db;
    TextView cart_badge;
    int count=0;
    AppPreferences mapAppPreferences;
    int discountPrice,disPer ,actualPrice=0;
    DecimalFormat numFormat;
    public AdapterServiceList(Context con, String data, TextView cartbadge) {
        this.con = con;
        Gson g = new Gson();
        numFormat = new DecimalFormat("#,##,###.##");
        numFormat.setMinimumFractionDigits(2);
        this.data_list = g.fromJson(data, BeanServiceRpt.class);
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.cart_badge = cartbadge;
        db = new DataBaseHelper(con);
        db.open();
        mapAppPreferences = new AppPreferences(con);
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
            vi = inflater.inflate(R.layout.activity_custom_service_list, null);

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


        LinearLayout linear_layout = vi.findViewById(R.id.linear_layout);
        Button btn_get= vi.findViewById(R.id.btn_get);
        if(db.getCartItems(data_list.getResult().get(position).getId()).getResult().size() == 0)
        {
            linear_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            btn_get.setText("Add");
        }
        else
        {
            linear_layout.setBackgroundColor(Color.parseColor("#98d5ad72"));
            btn_get.setText("Added");
        }
        btn_get.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(btn_get.getText().equals("Add"))
                {
                    linear_layout.setBackgroundColor(Color.parseColor("#98d5ad72"));
                    btn_get.setText("Added");
                    db.insertCartItems(
                            mapAppPreferences.getUserId(),
                            data_list.getResult().get(position).getParentId(),
                            data_list.getResult().get(position).getId(),
                            data_list.getResult().get(position).getName(),
                            data_list.getResult().get(position).getImage(),
                            data_list.getResult().get(position).getPrice(),
                            data_list.getResult().get(position).getDiscount(),
                            Utils.CurrentDateTime()
                    );
                    cart_badge.setText(String.valueOf(db.getCartItems("").getResult().size()));
                }
                else
                {
                    linear_layout.setBackgroundColor(Color.parseColor("#ffffff"));
                    btn_get.setText("Add");
                    db.removeCartItem(data_list.getResult().get(position).getId());
                    cart_badge.setText(String.valueOf(db.getCartItems("").getResult().size()));
                }
                if(db.getCartItems("").getResult().size() == 0)
                {
                    cart_badge.setVisibility(View.GONE);
                }
                else
                {
                    cart_badge.setVisibility(View.VISIBLE);
                }
            }
        });
        return vi;
    }
}
