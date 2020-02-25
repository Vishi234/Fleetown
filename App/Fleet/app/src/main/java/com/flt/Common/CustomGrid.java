package com.flt.Common;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flt.R;

public class CustomGrid extends BaseAdapter {
    private Context mContext;
    ArrayList<Integer> Image;
    ArrayList<String> captionName;
    public CustomGrid(Context c,ArrayList<Integer> Image,ArrayList<String> captionName) {
        this.Image = Image;
        this.captionName = captionName;
        mContext = c;
    }

    @Override
    public int getCount() {
        return Image.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent)throws OutOfMemoryError {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate( R.layout.griditem, null);
            ImageView iv = (ImageView) grid.findViewById(R.id.grid_image);
            TextView name = (TextView) grid.findViewById(R.id.name);
            String s = captionName.get(position)/*.replaceAll(" ","<br/>")*/;
            name.setText( Html.fromHtml(s));
            iv.setImageDrawable(mContext.getDrawable(Image.get(position)));
        } else {
            grid = (View) convertView;
        }
        return grid;
    }
    public void clear() {
        notifyDataSetChanged();
    }
}