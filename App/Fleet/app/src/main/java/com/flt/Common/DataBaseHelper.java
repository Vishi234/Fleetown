package com.flt.Common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.flt.Bean.BeanServiceList;
import com.flt.Bean.BeanServiceRpt;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String db_name = "DATABASE_IMAINTAIN";
    SQLiteDatabase sqLiteDB = null;
    public static final int version = 27;
    Context context;
    public DataBaseHelper(Context con) {
        super(con, db_name, null, version);
        this.context = con;
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL( SQLConstants.txnUserCartItems); // 0.5
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TXN_USER_CART_ITEMS");
    }
    public DataBaseHelper open() throws SQLException {
        sqLiteDB = getWritableDatabase();
        return this;
    }
    public void close() {
        sqLiteDB.close();
    }

    public void insertCartItems(String userId,String categoryId,String productId,String productName,String productImage,String price,String discount,String itemAddedTime)
    {
        ContentValues values = new ContentValues();
        values.put("USER_ID",userId);
        values.put("CATEGORY_ID",categoryId);
        values.put("PRODUCT_ID",productId);
        values.put("PRODUCT_NAME",productName);
        values.put("PRODUCT_IMAGE",productImage);
        values.put("PRICE",price);
        values.put("DISCOUNT",discount);
        values.put("ITEM_ADDED_DATETIME",itemAddedTime);
        sqLiteDB.insert("TXN_USER_CART_ITEMS", null, values);
    }
    public BeanServiceRpt getCartItems(String txn_id)
    {
        BeanServiceList serviceList;
        ArrayList<BeanServiceList> arrayList = new ArrayList<BeanServiceList>();
        BeanServiceRpt serviceRpt = null;
        Cursor c = null;
        if(txn_id != null && txn_id != "")
        {
            c = sqLiteDB.rawQuery("Select * from TXN_USER_CART_ITEMS WHERE PRODUCT_ID="+txn_id,null);
        }
        else
        {
            c = sqLiteDB.rawQuery("Select * from TXN_USER_CART_ITEMS",null);
        }
        if(c != null)
        {
            arrayList.clear();
            serviceRpt = new BeanServiceRpt();
            while (c.moveToNext())
            {
                serviceList = new BeanServiceList();
                serviceList.setId(c.getString(c.getColumnIndex("PRODUCT_ID")));
                serviceList.setParentId(c.getString(c.getColumnIndex("CATEGORY_ID")));
                serviceList.setName(c.getString(c.getColumnIndex("PRODUCT_NAME")));
                serviceList.setImage(c.getString(c.getColumnIndex("PRODUCT_IMAGE")));
                serviceList.setPrice(c.getString(c.getColumnIndex("PRICE")));
                serviceList.setDiscount(c.getString(c.getColumnIndex("DISCOUNT")));
                serviceList.setItemAddedTime(c.getString(c.getColumnIndex("ITEM_ADDED_DATETIME")));
                serviceList.setUserId(c.getString(c.getColumnIndex("USER_ID")));
                arrayList.add(serviceList);
            }
            serviceRpt.setResult(arrayList);
        }
        return serviceRpt;
    }
    public void removeCartItem(String txnId) {
        sqLiteDB.delete("TXN_USER_CART_ITEMS", "PRODUCT_ID =" + txnId, null);
    }
}
