package com.flt;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flt.Common.AppConstants;
import com.flt.Common.AppPreferences;
import com.flt.Common.Common;
import com.flt.Common.Utils;

public class LanguageActivity extends Activity {String language = "en", currentLanguage = "en",languageName="English";
    RadioGroup rg_language;
    Button btn_started;
    TextView button_back;
    AppPreferences mAppPreferences;
    String className="";
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        Common.StatusBarColor(getWindow(),this,R.color.colorPrimaryDark);
        mAppPreferences = new AppPreferences(LanguageActivity.this);
        Init();
        if(mAppPreferences.getFirstTimeRunApp() != "")
        {
            if(getIntent().getExtras()!= null)
            {
                className = getIntent().getExtras().getString("className");
            }
            if(className == "" || className == null)
            {
                Intent intent=new Intent(LanguageActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Utils.initializeHashmap(getResources());
                button_back.setVisibility(View.VISIBLE);
                btn_started.setText(R.string.change_lan_btn);
            }
        }
        else
        {
            btn_started.setText(R.string.continue_btn);
            button_back.setVisibility(View.GONE);
        }
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isNetworkAvailable(LanguageActivity.this))
                {
                    Intent intent = new Intent(LanguageActivity.this, AppConstants.classMap.get(className));
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(LanguageActivity.this, Utils.GetString(R.string.nointernet,LanguageActivity.this), Toast.LENGTH_SHORT).show();
                }
            }
        });
        rg_language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View vi= rg_language.findViewById(checkedId);
                switch (vi.getId())
                {
                    case(R.id.radio_english):
                    {
                        language="en";
                        languageName="English";
                    }break;
                    case (R.id.radio_hindi):
                    {
                        language="hi";
                        languageName="Hindi";
                    }break;
                    case (R.id.radio_tamil):
                    {
                        language="ta_IN";
                        languageName="Tamil";
                    }break;
                    case (R.id.radio_telugu):
                    {
                        language="te_IN";
                        languageName="Telugu";
                    }break;
                    case (R.id.radio_malayalam):
                    {
                        language="ml_IN";
                        languageName="Malayalam";
                    }break;
                    case (R.id.radio_marathi):
                    {
                        language="mr_IN";
                        languageName="Marathi";
                    }break;
                    case (R.id.radio_kannada):
                    {
                        language="kn_IN";
                        languageName="Kannan";
                    }break;
                    default:
                    {
                        language="en";
                        languageName="English";
                    }
                }
            }
        });
        btn_started.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                if(Utils.isNetworkAvailable(LanguageActivity.this))
                {
                    Utils.SetLocale(LanguageActivity.this,language,currentLanguage);
                    mAppPreferences.setLanCode(language);
                    mAppPreferences.setLanName(languageName);
                    if(className == "")
                    {
                        mAppPreferences.setFirstTimeRunApp("1");
                        Intent intent=new Intent(LanguageActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(LanguageActivity.this, AppConstants.classMap.get(className));
                        startActivity(intent);
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(LanguageActivity.this, Utils.GetString(R.string.nointernet,LanguageActivity.this), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void Init()
    {
        rg_language = findViewById(R.id.rg_language);
        btn_started= findViewById(R.id.btn_started);
        button_back=findViewById(R.id.button_back);
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
