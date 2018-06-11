package kondratkov.advocatesandnotariesrfpro.input;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kondratkov.advocatesandnotariesrfpro.IN;
import kondratkov.advocatesandnotariesrfpro.R;
import kondratkov.advocatesandnotariesrfpro.account.Address;
import kondratkov.advocatesandnotariesrfpro.account.Bup;
import kondratkov.advocatesandnotariesrfpro.account.City;
import kondratkov.advocatesandnotariesrfpro.account.ClientAccount;
import kondratkov.advocatesandnotariesrfpro.account.JudicialAreas;
import kondratkov.advocatesandnotariesrfpro.account.JuristAccount;
import kondratkov.advocatesandnotariesrfpro.account.JuristOrganisation;
import kondratkov.advocatesandnotariesrfpro.account.JuristSpecialization;
import kondratkov.advocatesandnotariesrfpro.account.JuristSpecializationSector;
import kondratkov.advocatesandnotariesrfpro.account.Region;
import kondratkov.advocatesandnotariesrfpro.dop_dialog.Dialog_region;
import kondratkov.advocatesandnotariesrfpro.dop_dialog.Dialog_sud_ter;
import kondratkov.advocatesandnotariesrfpro.dop_dialog.Dialog_sud_ter_new;
import kondratkov.advocatesandnotariesrfpro.dop_dialog.Dop_dialog_address;
import kondratkov.advocatesandnotariesrfpro.maps.Map_address_new;
import kondratkov.advocatesandnotariesrfpro.maps.Map_my_adres;

import static java.sql.Types.NULL;

public class SignUP extends Activity implements DatePickerFragment.DatePickedListener, Dialog_sud_ter.i_dialog_sudTer, Dialog_region.i_dialog_region,
        Dop_dialog_address.i_dialog_address, Map_address_new.i_dialog_map_address, Dialog_sud_ter_new.i_dialog_sudTer {

    private static final String DEBUG_TAG = "qwerty";

    public IN in;

    private int mYear;
    private int mMonth;
    private int mDay;

    public EditText etPhoneSign, etEmailSign, etNameSign, etPatronymicSign, etSurnameSign, et_adres_sign, et_firm_sign, et_site_sign,  et_reg_num_sign, etPasswordSign, etPassword2Sign;
    public TextView tvPhoneSign, tvEmailSign, tvNameSign, tvPatronymicSign, tvSurnameSign, tv_adres_sign, tv_firm_sign,  et_stag_sign, tv_site_sign, tvRegionSign, tvCitySign, tv_qualification_sign, tv_work_weekqnds_sign, tv_reg_num_sign, tv_stag_sign, tv_time_out_sign, tv_fsar_sign, tvPasswordSign, tvPassword2Sign, tv_sud_ter;
    public CheckBox checkBox_time_out_sign, checkBox_fsar_sign, checkBox_work_weekqnds_sign, checkBoxLicen, checkBox_vziskanie_sign, checkBox_nastavnick_sign;
    public LinearLayout lila_qualification_sign, lila_fsar_sign;
    public ProgressBar  progressBarRegionSign, progressBarCitySign;
    public String JSON_REGISTER="";
    public int flagView = 0;
    public String strErrors = "";
    public String[] strCity = null;
    public String City = "";
    public String resul = "false";
    public double LATITUDE = -1;
    public double LONGITUDE =-1;
    public boolean new_address = false;


    public EditText[]etArray;
    public TextView[]tvArray;
    public String[]strArray;
    public int position_arr= 0;
    public AlertDialog.Builder [] builderArray;
    public long sDate = 0;
    public Date date_stag = null;

    public String URL_ALL;
    public String phone_s = "";

    public CheckBox cb1_item, cb2_item, cb3_item, cb4_item, cb5_item,
            cb6_item, cb7_item, cb8_item, cb9_item, cb10_item,
            cb11_item, cb12_item, cb13_item, cb14_item, cb15_item,
            cb16_item, cb17_item, cb18_item, cb19_item, cb20_item;

    public CheckBox [] cb_array;
    public Boolean [] cb_array_bool;
    public int [] cb_array_int;
    public int cb_kol = 0;

    public Button b1, b2, b3, b4, b5, b6, b7, b8, b9 ,b10;
    public int br1=0, br2=0, br3=0, br4=0, br5=0;

    public CheckBox checkBox_sign_1, checkBox_sign_2, checkBox_sign_3;

    public EditText et_uch_st_and_sign, et_uch_st_and_eg_sign, et_status_ad_sign, et_dop_info_nagrad_sign, et_fsar_kogda_sign, et_fsar_gde_sign;

    public Region region;
    public City city;
    public Address addressTHIS = null;
    public JudicialAreas judicialAreas;
    public Dialog dialog;

    public int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_sign_up);

        in = new IN();
        URL_ALL = "http://"+in.get_url()+"/123.regjur";//adress d
        in.set_address("");
        in.set_context(SignUP.this);
        addressTHIS = new Address();
        dialog = new Dialog(SignUP.this);

        etSurnameSign = (EditText) findViewById(R.id.etSurnameSign);
        etNameSign = (EditText) findViewById(R.id.etNameSign);
        etPatronymicSign = (EditText) findViewById(R.id.etPatronymicSign);
        et_reg_num_sign = (EditText) findViewById(R.id.et_reg_num_sign);

        et_adres_sign = (EditText) findViewById(R.id.et_adres_sign);
        et_site_sign = (EditText) findViewById(R.id.et_site_sign);
        et_firm_sign = (EditText) findViewById(R.id.et_firm_sign);
        etPhoneSign = (EditText) findViewById(R.id.etPhoneSign);
        etEmailSign = (EditText) findViewById(R.id.etEmailSign);
        etPasswordSign = (EditText) findViewById(R.id.etPasswordSign);
        etPassword2Sign = (EditText) findViewById(R.id.etPassword2Sign);

        et_uch_st_and_sign = (EditText) findViewById(R.id.et_uch_st_and_sign);
        et_uch_st_and_eg_sign = (EditText) findViewById(R.id.et_uch_st_and_eg_sign);
        et_status_ad_sign = (EditText) findViewById(R.id.et_status_ad_sign);
        et_dop_info_nagrad_sign = (EditText) findViewById(R.id.et_dop_info_nagrad_sign);
        et_fsar_kogda_sign = (EditText) findViewById(R.id.et_fsar_kogda_sign);
        et_fsar_gde_sign = (EditText) findViewById(R.id.et_fsar_gde_sign);

        checkBox_sign_1 = (CheckBox) findViewById(R.id.checkBox_sign_1);
        checkBox_sign_2 = (CheckBox) findViewById(R.id.checkBox_sign_2);
        checkBox_sign_3 = (CheckBox) findViewById(R.id.checkBox_sign_3);

        //checkBox_vziskanie_sign = (CheckBox) findViewById(R.id.checkBox_vziskanie_sign);
        //checkBox_nastavnick_sign = (CheckBox) findViewById(R.id.checkBox_nastavnick_sign);

        etArray = new EditText[]{etSurnameSign, etNameSign, etPatronymicSign,
                et_adres_sign, et_reg_num_sign, et_site_sign,
                et_firm_sign, etPhoneSign, etEmailSign, etPasswordSign, etPassword2Sign };

        etPhoneSign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                phone_s = "+7df98df435+dsf7";

                Log.d("qwerty", "s.length " + s.length() + " __ " + etPhoneSign.length() + " " + phone_s);
                if (s.length() == 0 || s.length() == 1) {
                    etPhoneSign.setText("+7");
                    etPhoneSign.setSelection(s.length() + 1);
                }
                if (s.length() == 3 && String.valueOf(s.charAt(2)).equals(" ") == false) {
                    String sd = String.valueOf(s.charAt(2));
                    etPhoneSign.setText("+7 " + sd);
                    etPhoneSign.setSelection(s.length() + 1);
                }
                if (s.length() == 7 && String.valueOf(s.charAt(6)).equals(" ") == false) {
                    String sd = String.valueOf(s).substring(0, 6);
                    etPhoneSign.setText(sd + " " + String.valueOf(etPhoneSign.getText().charAt(6)));
                    etPhoneSign.setSelection(s.length() + 1);
                }
                if (s.length() == 11 && String.valueOf(s.charAt(10)).equals(" ") == false) {
                    String sd = String.valueOf(s).substring(0, 10);
                    etPhoneSign.setText(sd + " " + String.valueOf(etPhoneSign.getText().charAt(10)));
                    etPhoneSign.setSelection(s.length() + 1);
                }
                if (s.length() == 14 && String.valueOf(s.charAt(13)).equals("-") == false && String.valueOf(s.charAt(13)).equals(" ") == false) {
                    String sd = String.valueOf(s).substring(0, 13);
                    etPhoneSign.setText(sd + "-" + String.valueOf(etPhoneSign.getText().charAt(13)));
                    etPhoneSign.setSelection(s.length() + 1);
                }
                if (s.length() == 17) {
                    String sd = String.valueOf(s).substring(0, 16);
                    etPhoneSign.setText(sd);
                    etPhoneSign.setSelection(s.length() - 1);
                }
            }
        });

        builderArray = new AlertDialog.Builder[15];

        tvSurnameSign = (TextView) findViewById(R.id.tvSurnameSign);
        tvNameSign = (TextView) findViewById(R.id.tvNameSign);
        tvPatronymicSign = (TextView) findViewById(R.id.tvPatronymicSign);
        tv_reg_num_sign = (TextView) findViewById(R.id.tv_reg_num_sign);
        tv_stag_sign = (TextView) findViewById(R.id.tv_stag_sign);
        tv_adres_sign = (TextView) findViewById(R.id.tv_adres_sign);
        tv_site_sign = (TextView) findViewById(R.id.tv_site_sign);
        tv_firm_sign = (TextView) findViewById(R.id.tv_firm_sign);
        tvPhoneSign = (TextView) findViewById(R.id.tvPhoneSign);
        tvEmailSign = (TextView) findViewById(R.id.tvEmailSign);
        tvPasswordSign = (TextView) findViewById(R.id.tvPasswordSign);
        tvPassword2Sign = (TextView) findViewById(R.id.tvPassword2Sign);

        tv_sud_ter = (TextView) findViewById(R.id.tvSud_terSign);


        tvArray = new TextView[]{tvSurnameSign, tvNameSign, tvPatronymicSign,
                tv_adres_sign, tv_reg_num_sign, tv_site_sign,
                tv_firm_sign, tvPhoneSign, tvEmailSign, tvPasswordSign, tvPassword2Sign };

        tvRegionSign = (TextView) findViewById(R.id.tvRegionSign);
        tvCitySign = (TextView) findViewById(R.id.tvCitySign);
        et_stag_sign = (TextView) findViewById(R.id.et_stag_sign);


        progressBarRegionSign = (ProgressBar)findViewById(R.id.progressBarRegionSign);
        progressBarCitySign = (ProgressBar)findViewById(R.id.progressBarCitySign);

        strArray = new String[]{
                getResources().getString(R.string.surname_sign),
                getResources().getString(R.string.name_sign),
                getResources().getString(R.string.patronymic_sign),
                getResources().getString(R.string.adres_sign),
                getResources().getString(R.string.reg_num_sign),
                getResources().getString(R.string.site_sign),
                getResources().getString(R.string.name_firm_sign),
                getResources().getString(R.string.phone_sign),
                getResources().getString(R.string.email_sign),
                getResources().getString(R.string.password_sign),
                getResources().getString(R.string.password_two_sign)};

        //checkBox_time_out_sign = (CheckBox)findViewById(R.id.checkBox_time_out_sign);
        // checkBox_fsar_sign = (CheckBox)findViewById(R.id.checkBox_fsar_sign);
        // checkBox_work_weekqnds_sign = (CheckBox)findViewById(R.id.checkBox_work_weekqnds_sign);
        checkBoxLicen = (CheckBox)findViewById(R.id.checkBoxLicen);

        for(int i =0; i<etArray.length; i++){
            etArray[i].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // TODO Auto-generated method stub

                    for(int j = 0; j<etArray.length; j++){
                        if(v == etArray[j]){
                            position_arr = j;
                        }
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        //Array[3].getId();
                        for(int j = 0; j<etArray.length; j++){
                            if (etArray[j].getText().toString().equals("")) {
                                if (position_arr == j) {
                                    tvArray[j].setText(strArray[j]);
                                    etArray[j].setHint("");
                                } else {
                                    tvArray[j].setText("");
                                    etArray[j].setHint(strArray[j]);
                                }
                            } else {
                                tvArray[j].setText(strArray[j]);
                            }
                        }
                    }
                    return false;
                }
            });
        }

        ///заполнение списка специализации адвоката
        cb_array = new CheckBox[in.get_list_specialization().length];
        cb_array_bool = new Boolean[in.get_list_specialization().length];
        cb_array_int = new int [in.get_list_specialization().length];

        lila_qualification_sign = (LinearLayout)findViewById(R.id.lila__qualification_sign);

        for(int i =0; i<in.get_list_specialization().length; i++){

            final View view = getLayoutInflater().inflate(R.layout.view_specialization, null);

            TextView textViewSpecialization = (TextView) view.findViewById(R.id.text_specialization);
            CheckBox checkBoxSpecialization = (CheckBox) view.findViewById(R.id.cb_specialization);

            textViewSpecialization.setText(in.get_list_specialization()[i].SectorName);
            cb_array[i] = checkBoxSpecialization;
            cb_array_bool[i]=false;
            cb_array_int[i] = in.get_list_specialization()[i].Id;

            lila_qualification_sign.addView(view);
        }


        for(int i = 0; i< cb_array.length; i++){
            cb_array[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    cb_kol = 0;
                    if(isChecked ==true){
                        for(int j = 0; j<cb_array_bool.length; j++){
                            if(cb_array_bool[j]==true){
                                cb_kol = cb_kol+1;
                                Log.d("qwerty", "1");
                            }
                            if(cb_kol>2){
                                buttonView.setChecked(false);
                                cb_kol = cb_kol-1;
                                Log.d("qwerty", "2");
                            }
                        }
                    }
                    else {
                        if(cb_kol!=0){
                            cb_kol = cb_kol-1;
                            Log.d("qwerty", "3");
                        }
                    }
                    Log.d("qwerty", "" );
                    Log.d("qwerty", "kol "+cb_kol );

                    for(int j = 0; j<cb_array.length; j++){
                        cb_array_bool[j] = cb_array[j].isChecked();
                        Log.d("qwerty", "bool "+cb_array[j].isChecked() );
                    }
                }
            });
        }

        for(int i = 0; i< cb_array.length; i++){
            cb_array[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    cb_kol = 0;
                    boolean odin_raz = true;
                    if(isChecked ==true){
                        for(int j = 0; j<cb_array_bool.length; j++){
                            if(cb_array_bool[j]==true){
                                cb_kol = cb_kol+1;
                                Log.d("qwerty", "1");
                            }if(cb_kol>2){
                                if(odin_raz) {
                                    Toast.makeText(SignUP.this,
                                            "Если выбранно больше трех специализаций, ваш рейтинг станет меньше!",
                                            Toast.LENGTH_LONG).show();
                                    odin_raz =false;
                                }
                            }
                        }
                    }
                    else {
                        if(cb_kol!=0){
                            cb_kol = cb_kol-1;
                        }
                    }

                    for(int j = 0; j<cb_array.length; j++){
                        cb_array_bool[j] = cb_array[j].isChecked();
                    }
                }
            });
        }

        lila_fsar_sign = (LinearLayout)findViewById(R.id.lila_fsar_sign);

        b1 = (Button)findViewById(R.id.button_1);
        b2 = (Button)findViewById(R.id.button_2);
        b3 = (Button)findViewById(R.id.button_3);
        b4 = (Button)findViewById(R.id.button_4);
        b5 = (Button)findViewById(R.id.button_5);
        b6 = (Button)findViewById(R.id.button_6);
        b7 = (Button)findViewById(R.id.button_7);
        b8 = (Button)findViewById(R.id.button_8);
        b9 = (Button)findViewById(R.id.button_9);
        b10 = (Button)findViewById(R.id.button_10);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br1 = 1;
                boolean_button_refresh();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br1 = 2;
                boolean_button_refresh();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br2 = 1;
                boolean_button_refresh();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br2 = 2;
                boolean_button_refresh();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br3 = 1;
                boolean_button_refresh();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br3 = 2;
                boolean_button_refresh();
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br4 = 1;
                boolean_button_refresh();
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br4 = 2;
                boolean_button_refresh();
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br5 = 1;
                boolean_button_refresh();
            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br5 = 2;
                boolean_button_refresh();
            }
        });

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);

        //updateDisplay();
    }
    // обновляем дату для вывода в TextView

    @Override
    protected void onStart() {
        super.onStart();
        etArray[3].setText(in.get_address());
        if(new_address){
            new_address=false;
            iv_onAddressToMap();
        }
    }

    public void onDatePicked(Calendar date) {
        // выводим выбранную дату в текстовой метке 990
        mYear = date.get(Calendar.YEAR);
        mMonth = date.get(Calendar.MONTH) + 1;
        mDay = date.get(Calendar.DAY_OF_MONTH);
        sDate = date.getTimeInMillis();
        date_stag = new Date();
        date_stag.setTime(sDate);
        updateDisplay();
    }

    public void updateDisplay() {
        et_stag_sign.setText(new StringBuilder().append(mDay).append(".")
                .append(mMonth).append(".").append(mYear));
    }

    public int dpToPx(int dp){
        DisplayMetrics displayMetrics = SignUP.this.getResources().getDisplayMetrics();
        int px =Math.round(dp *(displayMetrics.xdpi /DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public void boolean_button_refresh(){
        if(br1 ==1){
            b1.setBackgroundResource(R.color.color_button_yes);
            b2.setBackgroundResource(R.color.color_button_no);
        }else if(br1 ==2){
            b2.setBackgroundResource(R.color.color_button_yes);
            b1.setBackgroundResource(R.color.color_button_no);
        }

        if(br2 ==1){
            b3.setBackgroundResource(R.color.color_button_yes);
            b4.setBackgroundResource(R.color.color_button_no);
        }else if(br2 ==2){
            b4.setBackgroundResource(R.color.color_button_yes);
            b3.setBackgroundResource(R.color.color_button_no);
        }

        if(br3 ==1){
            b5.setBackgroundResource(R.color.color_button_yes);
            b6.setBackgroundResource(R.color.color_button_no);
        }else if(br3 ==2){
            b6.setBackgroundResource(R.color.color_button_yes);
            b5.setBackgroundResource(R.color.color_button_no);
        }

        if(br4 ==1){
            b7.setBackgroundResource(R.color.color_button_yes);
            b8.setBackgroundResource(R.color.color_button_no);
        }else if(br4 ==2){
            b8.setBackgroundResource(R.color.color_button_yes);
            b7.setBackgroundResource(R.color.color_button_no);
        }

        if(br5 ==1){
            b9.setBackgroundResource(R.color.color_button_yes);
            b10.setBackgroundResource(R.color.color_button_no);
            lila_fsar_sign.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0));
        }else if(br5 ==2){
            b10.setBackgroundResource(R.color.color_button_yes);
            b9.setBackgroundResource(R.color.color_button_no);
            lila_fsar_sign.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(4), (float) 0));
            et_fsar_kogda_sign.setText("");
            et_fsar_gde_sign.setText("");
        }
    }


    //--------------------------------------------------------------region
    @Override
    public void iv_onSudTer(JudicialAreas judicialAreas, Region region) {
        this.judicialAreas=judicialAreas;
        tv_sud_ter.setText(judicialAreas.Name);
    }

    @Override
    public void iv_onRegion(City city, Region region) {
        //in.city = city;
        this.city = city;
        tvRegionSign.setText(region.Name);
        tvCitySign.setText(city.Name);
        addressTHIS.Region = region.Name;
        addressTHIS.City = city.Name;

        in.set_region(region);
        in.set_city(city);
    }

    @Override
    public void iv_dialog_address(Address address) {
        addressTHIS = address;
        in.setAddress(address);
        String SLOT = new Gson().toJson(address);
        et_adres_sign.setText(address.Region+" "+address.City+" "+address.Street+" "+address.StreetNumber+" "+address.BuildingNumber);
    }

    public void iv_onAddressToMap() {
        addressTHIS.Street = in.getAddress().Street;
        addressTHIS.StreetNumber = in.getAddress().StreetNumber;
        LATITUDE = in.get_coor_x();
        LONGITUDE = in.get_coor_y();
        et_adres_sign.setText(addressTHIS.Region+" "+addressTHIS.City+" "+addressTHIS.Street+" "+addressTHIS.StreetNumber+" "+addressTHIS.BuildingNumber);
    }

//___|||__|____|___|_______|____|_____|_______|____|__region

    public boolean bool_but_qualification = true;
    public void onClick(View v){
        switch (v.getId()){
            case R.id.but_registr:
                for(int i = 0; i<etArray.length+1; i++){
                    Log.d("qwerty", "Проверка едитов "+ i);
                    if(i ==6){
                    }
                    else if(i ==7){
                        if(tvCitySign.getText().length()==0) {
                            strErrors = "Не выбран город!";
                            showDialog(14);
                            i = etArray.length + 2;
                        }
                    }
                    else if (i==8){//phone
                        if (etPhoneSign.getText().toString().length() == 0 ||
                                String.valueOf(etPhoneSign.getText().toString().charAt(0)) == " " ||
                                etPhoneSign.getText().toString().length() != 16
                                ) {
                            etPhoneSign.setHintTextColor(getResources().getColor(R.color.error_text));
                            etPhoneSign.setBackgroundResource(R.drawable.apperror_edit_text_holo_light);
                            tvPhoneSign.setTextColor(getResources().getColor(R.color.error_text));


                            strErrors = getString(R.string.error_sign_phone_one);
                            showDialog(i);

                            i= etArray.length+2;
                        } else {
                            etPhoneSign.setHintTextColor(getResources().getColor(R.color.tip_color));
                            etPhoneSign.setBackgroundResource(R.drawable.reges_edit_text_holo_light);
                            tvPhoneSign.setTextColor(getResources().getColor(R.color.tip_color));
                        }

                    }
                    else if(i==10){//password
                        if (etPasswordSign.getText().toString().length() == 0 ||
                                String.valueOf(etPasswordSign.getText().toString().charAt(0)) == " " ||
                                String.valueOf(etPasswordSign.getText()).equals(String.valueOf(etPassword2Sign.getText()))==false||
                                //etPasswordSign.getText().toString()!= etPassword2Sign.getText().toString()||
                                //etPasswordSign.getText().toString().equalsIgnoreCase(etPassword2Sign.getText().toString())||
                                etPasswordSign.getText().toString().length() < 6) {
                            etPasswordSign.setHintTextColor(getResources().getColor(R.color.error_text));
                            etPasswordSign.setBackgroundResource(R.drawable.apperror_edit_text_holo_light);
                            tvPasswordSign.setTextColor(getResources().getColor(R.color.error_text));

                            etPassword2Sign.setHintTextColor(getResources().getColor(R.color.error_text));
                            etPassword2Sign.setBackgroundResource(R.drawable.apperror_edit_text_holo_light);
                            tvPassword2Sign.setTextColor(getResources().getColor(R.color.error_text));

                            if (etPasswordSign.getText().toString().length() == 0 ||
                                    String.valueOf(etPasswordSign.getText().toString().charAt(0)) == " ") {
                                strErrors = getString(R.string.error_sign_one) + " '" + getString(R.string.password_sign) + "' " + getString(R.string.error_sign_two);
                                showDialog(10);
                            } else if (etPasswordSign.getText().toString().length() < 6) {
                                strErrors = getString(R.string.error_password_lengh);
                                showDialog(12);
                            } else {
                                strErrors = getString(R.string.error_password_no_two) + " " + etPasswordSign.getText().toString() + " " + etPassword2Sign.getText().toString();
                                showDialog(13);
                            }
                            i= etArray.length+2;
                        } else {
                            etPasswordSign.setHintTextColor(getResources().getColor(R.color.tip_color));
                            etPasswordSign.setBackgroundResource(R.drawable.reges_edit_text_holo_light);
                            tvPasswordSign.setTextColor(getResources().getColor(R.color.tip_color));

                            etPassword2Sign.setHintTextColor(getResources().getColor(R.color.tip_color));
                            etPassword2Sign.setBackgroundResource(R.drawable.reges_edit_text_holo_light);
                            tvPassword2Sign.setTextColor(getResources().getColor(R.color.tip_color));
                        }

                    }else if(br1==0){
                        showDialog(41);
                    }else if(br2==0){
                        showDialog(42);
                    }else if(br3==0){
                        showDialog(43);
                    }else if(br4==0){
                        showDialog(44);
                    }else if(br5==0){
                        showDialog(45);
                    }else if(et_stag_sign.getText().length()==0){
                        Toast.makeText(SignUP.this,
                                "Для регистрации необходимо указать Ваш стаж!",
                                Toast.LENGTH_LONG).show();
                    }
                    else if(i == etArray.length){
                        if(checkBoxLicen.isChecked()){
                            String bup="";
                            for(int f=0; f<3; f++){
                                if(f==0){bup="1";}
                                if(f==1){
                                    if(bup.length()==0){
                                        bup=bup+"2";
                                    }else{
                                        bup=bup+",2";
                                    }
                                }
                                if(f==2){
                                    if(bup.length()==0){
                                        bup=bup+"3";
                                    }else{
                                        bup=bup+",3";
                                    }
                                }
                            }

                            String spec="";
                            for(int f=0; f<cb_array.length; f++){
                                if(cb_array[f].isChecked()){
                                    if(spec.length()==0){
                                        spec=String.valueOf(f);
                                    }else{
                                        spec=spec+","+String.valueOf(f);
                                    }
                                }
                            }

                            Gson gson = new Gson();
// заполнение специализации
                            int length_cb = 0;
                            int [] spec_mass = new int[34];
                            for(int f=0; f<cb_array.length; f++){
                                if(cb_array[f].isChecked()){
                                    spec_mass[length_cb]=f;
                                    length_cb ++;
                                }
                            }
                            Object[] js = new Object[length_cb];

                            JuristSpecialization[] juristSpecializations = new JuristSpecialization[length_cb];

                            for(int f=0; f<length_cb; f++) {
                                JuristSpecialization juristSpecialization = new JuristSpecialization();
                                juristSpecialization.Id = in.get_list_specialization()[spec_mass[f]].Id;
                                juristSpecialization.SectorName = in.get_list_specialization()[spec_mass[f]].SectorName;

                                //JuristSpecializationSector juristSpecializationSector = new JuristSpecializationSector();
                                //juristSpecializationSector.Id
                                //juristSpecializationSector.SectorName = String.valueOf(getResources().getStringArray(R.array.arraySpecializationNew)[spec_mass[f]]);
                                //juristSpecialization.Specialization = juristSpecializationSector;
                                juristSpecializations[f] = juristSpecialization;
                                //String sf = gson.toJson(juristSpecializationSector);
                                js[f] = juristSpecialization;
                                juristSpecializations[f]=juristSpecialization;
                                String sf1 = gson.toJson(juristSpecializations);
                                int idd=0;
                            }

                            String sss = gson.toJson(juristSpecializations);

//заполнение БЮП
                            CheckBox[] checkBoxesBUP = {checkBox_sign_1,checkBox_sign_2,checkBox_sign_3};
                            int length_bup = 0;
                            int [] spec_bup = new int[3];
                            for(int f=0; f<3; f++){
                                if(checkBoxesBUP[f].isChecked()){
                                    spec_bup[length_bup]=f;
                                    length_bup ++;
                                }
                            }

                            Bup bup1 = new Bup();
                            for(int f=0; f<length_bup; f++) {
                                switch (spec_bup[f]){
                                    case 0:bup1.Upk51 = true;
                                        break;
                                    case 1:bup1.Gk50 = true;
                                        break;
                                    case 2:bup1.Fz324 = true;
                                        break;
                                }
                            }
//заполнение булевских элементов по кнопкам да и нет
                            boolean bool1, bool2, bool3, bool4, bool5;
                            if(br1==1)bool1=true;
                            else bool1=false;
                            if(br2==1)bool2=true;
                            else bool2=false;
                            if(br3==1)bool3=true;
                            else bool3=false;
                            if(br4==1)bool4=true;
                            else bool4=false;
                            if(br5==1)bool5=true;
                            else bool5=false;

//заполнение данных адвоката
                            JuristAccount baseAccount = new JuristAccount();

                            baseAccount.CanFastComing = bool1;// Срочный выезд
                            baseAccount.Tutorship = bool2;// Наставничество (имеет ли помощников/стажеров) да/нет
                            baseAccount.HasRecoveries = bool3;// Наличие взысканий
                            baseAccount.WorkInOffDays = bool4;// Работа в выходные дни

                            String dateInString = "Friday, Jun 7, 2013 12:10:56 PM";
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
                            dateInString = sdf.format(date_stag.getTime());
                            baseAccount.ExperienceLevel = dateInString;

                            baseAccount.Address = addressTHIS;

                            baseAccount.Specializations = juristSpecializations;

                            baseAccount.NumberInApReestr = String.valueOf(et_reg_num_sign.getText());//String.valueOf(.getText());String.valueOf(.getText());

                            baseAccount.Name = String.valueOf(etNameSign.getText());

                            baseAccount.Surename = String.valueOf(etSurnameSign.getText());

                            baseAccount.Patronymic = String.valueOf(etPatronymicSign.getText());

                            baseAccount.Email = String.valueOf(etEmailSign.getText());

                            baseAccount.Phone = String.valueOf(etPhoneSign.getText());

                            baseAccount.Site= String.valueOf(et_site_sign.getText());

                            JuristOrganisation juristOrganisation=new JuristOrganisation();
                            juristOrganisation.OrganisationName =String.valueOf(et_firm_sign.getText());
                            baseAccount.JuristOrganisation = juristOrganisation;

                            baseAccount.JudicialArea = this.judicialAreas;

                            baseAccount.Bups = bup1;

                            baseAccount.JuristStatusAssignedBy = String.valueOf(et_status_ad_sign.getText());

                            baseAccount.ExtendedInfo = String.valueOf(et_dop_info_nagrad_sign.getText());

                            baseAccount.PhdInfo = String.valueOf(et_uch_st_and_sign.getText());;

                            baseAccount.LanguagesInfo = String.valueOf(et_uch_st_and_eg_sign.getText());

                            baseAccount.IsFsarMember = bool5;
                            //baseAccount.FsarMamberDate = ;

                            baseAccount.FsarRegionalOffice = String.valueOf(et_fsar_gde_sign.getText());
                            LATITUDE = in.get_coor_x();
                            LONGITUDE = in.get_coor_y();
                            baseAccount.Latitude = LATITUDE;
                            baseAccount.Longitude = LONGITUDE;




                            ClientAccount clientAccount = new ClientAccount();
                            clientAccount.UserName = String.valueOf(etEmailSign.getText());
                            clientAccount.Password = String.valueOf(etPasswordSign.getText());
                            clientAccount.ConfirmPassword = String.valueOf(etPasswordSign.getText());
                            clientAccount.AccountType = ClientAccount.AccountTypes.Jurist;
                            clientAccount.AccountProfile = gson.toJson(baseAccount);
                            JSON_REGISTER = gson.toJson(clientAccount);
                            String ss = JSON_REGISTER;
                            Log.d("sdf", "sdf");
                            Dialog_loc();
                        }
                        else{
                            Toast.makeText(SignUP.this,
                                    "Для регистрации необходимо принять лицензионное соглашение!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        if (etArray[i].getText().toString().length() == 0 ||
                                String.valueOf(etArray[i].getText().toString().charAt(0)) == " ") {
                            etArray[i].setHintTextColor(getResources().getColor(R.color.error_text));
                            etArray[i].setBackgroundResource(R.drawable.apperror_edit_text_holo_light);
                            tvArray[i].setTextColor(getResources().getColor(R.color.error_text));

                            strErrors = getString(R.string.error_sign_one) + " '" + strArray[i] + "' " + getString(R.string.error_sign_two);
                            showDialog(i);

                            i= etArray.length+3;
                        } else {
                            etArray[i].setHintTextColor(getResources().getColor(R.color.tip_color));
                            etArray[i].setBackgroundResource(R.drawable.reges_edit_text_holo_light);
                            tvArray[i].setTextColor(getResources().getColor(R.color.tip_color));
                        }
                        if(i==4)i=6;
                    }
                }
                break;

            case R.id.but_qualification_sign:
                if(bool_but_qualification){
                    lila_qualification_sign.setLayoutParams(
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(4), (float) 0));
                    bool_but_qualification= false;
                }else{
                    lila_qualification_sign.setLayoutParams(
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0));
                    bool_but_qualification = true;
                }

                break;
            case R.id.but_stag:
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getFragmentManager(), "datePicker");
                break;

            case R.id.butSud_terSign:
                //Dialog_sud_ter ds = new Dialog_sud_ter(SignUP.this, in.get_url());
                Dialog_sud_ter_new ds = new Dialog_sud_ter_new(SignUP.this, in.get_url());
                ds.openDialogSudTer();
                break;

            case R.id.ibut_geo_jur:
                if(tvRegionSign.getText().length()==0){
                    Toast.makeText(SignUP.this,
                            "Не выбран регион!",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    in.set_address(String.valueOf(et_adres_sign.getText()));
                    Intent intent = new Intent(SignUP.this, Map_address_new.class);
                    Gson gg = new Gson();
                    intent.putExtra("ADDRESS", gg.toJson(addressTHIS));
                    new_address = true;
                    startActivity(intent);
                }

                break;

            case R.id.but_ls:
                openDialog();
                break;

            case R.id.butSignAddress:
                if(tvRegionSign.getText().length()==0){
                    Toast.makeText(SignUP.this,
                            "Не выбран регион!",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Gson gson = new Gson();
                    String s = gson.toJson(this.addressTHIS);
                    Dop_dialog_address dialog_address = new Dop_dialog_address(SignUP.this, in.get_url(), this.addressTHIS);
                    dialog_address.openDialogAddress();
                    //progressBarCitySign.setVisibility(ProgressBar.VISIBLE);
                    //new AsyncTaskDialog().execute();
                }
                break;

            case R.id.butRegionSign:
                //City_num = 0;
                //progressBarRegionSign.setVisibility(ProgressBar.VISIBLE);
                //new AsyncTaskDialog().execute();
                Dialog_region dialog_region = new Dialog_region(SignUP.this, in.get_url());
                dialog_region.openDialogRegion();
                break;

            case R.id.butCitySign:
                if(tvRegionSign.getText().length()==0){
                    Toast.makeText(SignUP.this,
                            "Не выбран регион!",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Dialog_region dialog_region1 = new Dialog_region(SignUP.this, in.get_url());
                    dialog_region1.openDialogRegion();
                    //progressBarCitySign.setVisibility(ProgressBar.VISIBLE);
                    //new AsyncTaskDialog().execute();
                }
                break;
        }
    }
    private void Dialog_loc() {
        dialog.setTitle("передача...");
        dialog.setContentView(R.layout.input_dialog_window);
        dialog.setCancelable(true);
        //new AsyncTaskExample().execute();
        new UrlConnectionTask().execute(JSON_REGISTER);
        dialog.show();
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(SignUP.this);
        dialog.setTitle("Лицензионное соглашение");
        dialog.setContentView(R.layout.input_dialog_license_agreement);

        Button btnNO = (Button) dialog.getWindow().findViewById(
                R.id.dl_but_no);

        btnNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btnYes = (Button) dialog.getWindow().findViewById(
                R.id.dl_but_yes);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxLicen.setChecked(true);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        String s = strErrors;
        if(id == 33){
            Log.d("qwerty", "builderArray33");
            AlertDialog.Builder builder33 = new AlertDialog.Builder(this);
            builder33.setMessage("Запрос на регистрацию прошел успешно, после проверки Ваших данных вам будет выслано письмо по адресу "+etEmailSign.getText()+" с дальнейшими указаниями!")
                    .setCancelable(false)

                    .setNegativeButton("закрыть",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    SignUP.this.finish();
                                }
                            });
            return builder33.create();
        }
        if(id == 34){
            Log.d("qwerty", "builderArray34");
            AlertDialog.Builder builder33 = new AlertDialog.Builder(this);
            builder33.setMessage("Ошибка, телефон уже используеться, пожалуйста введите новый телефон!")
                    .setCancelable(false)

                    .setNegativeButton("закрыть",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            return builder33.create();
        }
        if(id == 35){
            Log.d("qwerty", "builderArray35");
            AlertDialog.Builder builder33 = new AlertDialog.Builder(this);
            builder33.setMessage("Ошибка, e-mail уже используеться, пожалуйста введите новый e-mail!")
                    .setCancelable(false)

                    .setNegativeButton("закрыть",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            return builder33.create();
        }
        if(id == 41){
            AlertDialog.Builder builder41 = new AlertDialog.Builder(this);
            builder41.setMessage("Ошибка! Не выбранно возможность срочного выезда")
                    .setCancelable(false)

                    .setNegativeButton("закрыть",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            return builder41.create();
        }
        if(id == 42){
            AlertDialog.Builder builder42 = new AlertDialog.Builder(this);
            builder42.setMessage("Ошибка! Не выбранно наставничество!")
                    .setCancelable(false)

                    .setNegativeButton("закрыть",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            return builder42.create();
        }
        if(id == 43){
            AlertDialog.Builder builder43 = new AlertDialog.Builder(this);
            builder43.setMessage("Ошибка, Не выбранно наличие взысканий!")
                    .setCancelable(false)

                    .setNegativeButton("закрыть",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            return builder43.create();
        }
        if(id == 44){
            AlertDialog.Builder builder44 = new AlertDialog.Builder(this);
            builder44.setMessage("Ошибка! Не выбранно работа в выходные")
                    .setCancelable(false)

                    .setNegativeButton("закрыть",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            return builder44.create();
        }
        if(id == 45){
            AlertDialog.Builder builder45 = new AlertDialog.Builder(this);
            builder45.setMessage("Ошибка! Не выбранно членстов в ФСАР")
                    .setCancelable(false)

                    .setNegativeButton("закрыть",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            return builder45.create();
        }

        if(id == 36){
            Log.d("qwerty", "builderArray36");
            AlertDialog.Builder builder33 = new AlertDialog.Builder(this);
            builder33.setMessage("Ошибка, регистрационный номер уже используеться!")
                    .setCancelable(false)

                    .setNegativeButton("закрыть",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            return builder33.create();
        }

        else{
            Log.d("qwerty", "&&&&&&&& "+ id);
            for(int i =0;i<15; i++){
                if(i == id){
                    builderArray[i]= new AlertDialog.Builder(this);
                    builderArray[i].setMessage(s)
                            .setCancelable(false)

                            .setNegativeButton(R.string.project_close_dialog,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                        }
                                    });
                    Log.d("qwerty", "!");
                    return builderArray[i].create();
                }
            }
            return null;
        }
    }

    @Override
    public void dialogMapAddress(Address address) {
        addressTHIS = address;
        in.setAddress(address);
        String SLOT = new Gson().toJson(address);
        et_adres_sign.setText(address.Region+" "+address.City+" "+address.Street+" "+address.StreetNumber+" "+address.BuildingNumber);
    }


    class UrlConnectionTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String result = "";

            OkHttpClient client = new OkHttpClient();

            MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");
            String s1 = "http://"+in.get_url()+"/account/register";
            String s2 = params[0];
            String s3 = "sdf";


            Request request = new Request.Builder()
                    .url("http://"+in.get_url()+"/account/register")
                    .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, params[0]))
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                result = response.body().string();
                code = response.code();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (code>=200 && code<300 && result!=null) {
                showDialog(33);
            }else{
                Toast.makeText(SignUP.this,
                        "Сервер не отвечает!",
                        Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }
    }

}
