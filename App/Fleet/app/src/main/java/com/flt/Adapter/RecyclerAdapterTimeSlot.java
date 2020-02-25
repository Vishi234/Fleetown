package com.flt.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flt.Bean.BeanParam;
import com.flt.OtherInformation;
import com.flt.R;

import java.util.ArrayList;

public class RecyclerAdapterTimeSlot extends RecyclerView.Adapter<RecyclerAdapterTimeSlot.ViewHolder> {
    ArrayList<String> list = null;
    BeanParam data_list = null;
    int row_index = -1;
    public RecyclerAdapterTimeSlot(BeanParam response_param, ArrayList<String> futureDateList) {
        list = futureDateList;
        data_list = response_param;
    }

    @NonNull
    @Override
    public RecyclerAdapterTimeSlot.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_time_slot_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterTimeSlot.ViewHolder holder, int position) {
        if(list != null)
        {
            holder.tv_date.setText(list.get(position));
            holder.tv_date.setVisibility(View.VISIBLE);
            holder.tv_time.setVisibility(View.GONE);
        }
        else
        {
            holder.tv_time.setText(data_list.getResult().get(position).getParamName());
            holder.tv_date.setVisibility(View.GONE);
            holder.tv_time.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();
            }
        });
        if(row_index == position)
        {
            holder.ll_slot.setBackgroundColor(Color.parseColor("#98d5ad72"));
        }
        else
        {
            holder.ll_slot.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public int getItemCount() {
        return ((list != null) ? list.size() : data_list.getResult().size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_date,tv_time;
        public LinearLayout ll_slot;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
            ll_slot = itemView.findViewById(R.id.ll_slot);
        }
    }
}
