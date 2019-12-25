package com.flt.Driver;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.flt.Common.Operator;
import com.flt.R;

import java.util.ArrayList;

public class DriverRegisrationActivity extends Activity {
    TextView tv_terms;
    EditText et_type;
    Spinner sp_vehicle_type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);
        Init();
        tv_terms.setText(Html.fromHtml("By clicking on Signup button, you are accepting our <a href=\"http://www.xamarin.com\">Terms & Condition</a> & <a href=\"http://www.xamarin.com\">Privacy Policy</a>"));
        tv_terms.setMovementMethod(LinkMovementMethod.getInstance());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.driver_vehicle_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sp_vehicle_type.setAdapter(adapter);
    }
    public void Init()
    {
        tv_terms=(TextView)findViewById(R.id.tv_terms);
        //et_type=findViewById(R.id.et_type);
        sp_vehicle_type=findViewById(R.id.sp_vehicle_type);
    }
}
