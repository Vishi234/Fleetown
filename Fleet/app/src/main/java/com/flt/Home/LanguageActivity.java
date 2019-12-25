package com.flt.Home;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.flt.Common.AppPreferences;
import com.flt.Common.Utils;
import com.flt.MainActivity;
import com.flt.R;

public class LanguageActivity extends Activity {
    String language = "en", currentLanguage = "en";
    RadioGroup rg_language;
    Button btn_started;
    AppPreferences mAppPreferences;
    Utils utils;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        mAppPreferences = new AppPreferences(LanguageActivity.this);
        Init();
        if(mAppPreferences.getFirstTimeRunApp() != "")
        {
            utils = new Utils();
            utils.SetLocale(LanguageActivity.this,currentLanguage,language);
            Intent intent=new Intent(LanguageActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        rg_language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View vi= rg_language.findViewById(checkedId);
                switch (vi.getId())
                {
                    case(R.id.radio_english):
                    {
                        language="en";
                    }break;
                    case (R.id.radio_hindi):
                    {
                        language="hi";
                    }break;
                    case (R.id.radio_tamil):
                    {
                        language="ta_IN";
                    }break;
                    case (R.id.radio_telugu):
                    {
                        language="te_IN";
                    }break;
                    case (R.id.radio_malayalam):
                    {
                        language="ml_IN";
                    }break;
                    case (R.id.radio_marathi):
                    {
                        language="mr_IN";
                    }break;
                    case (R.id.radio_kannada):
                    {
                        language="kn_IN";
                    }break;
                    default:
                    {
                        language="en";
                    }
                }
            }
        });
        btn_started.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                utils = new Utils();
                utils.SetLocale(LanguageActivity.this,language,currentLanguage);
                mAppPreferences.setLanCode(language);
                mAppPreferences.setFirstTimeRunApp("1");
                Intent intent=new Intent(LanguageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void Init()
    {
        rg_language = findViewById(R.id.rg_language);
        btn_started= findViewById(R.id.btn_started);
        if(mAppPreferences.getLanCode()=="")
        {
            mAppPreferences.setLanCode("en");
        }
        else
        {
            currentLanguage = mAppPreferences.getLanCode();
        }
        SetChecked(currentLanguage);
    }
    public void SetChecked(String lanCode)
    {
        switch (lanCode)
        {
            case("en"):
            {
                rg_language.check(R.id.radio_english);
            }break;
            case ("hi"):
            {
                rg_language.check(R.id.radio_hindi);
            }break;
            case ("ta_IN"):
            {
                rg_language.check(R.id.radio_tamil);
            }break;
            case ("te_IN"):
            {
                rg_language.check(R.id.radio_telugu);
            }break;
            case ("ml_IN"):
            {
                rg_language.check(R.id.radio_malayalam);
            }break;
            case ("mr_IN"):
            {
                rg_language.check(R.id.radio_marathi);
            }break;
            case ("kn_IN"):
            {
                rg_language.check(R.id.radio_kannada);
            }break;
            default:
            {
                rg_language.check(R.id.radio_english);
            }
        }
    }
}
