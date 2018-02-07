package kondratkov.advocatesandnotariesrfpro.my_info;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kondratkov.advocatesandnotariesrfpro.IN;
import kondratkov.advocatesandnotariesrfpro.R;
import kondratkov.advocatesandnotariesrfpro.account.JudicialAreas;
import kondratkov.advocatesandnotariesrfpro.account.Region;
import kondratkov.advocatesandnotariesrfpro.dop_dialog.Dialog_sud_ter;
import kondratkov.advocatesandnotariesrfpro.input.DatePickerFragment;
import kondratkov.advocatesandnotariesrfpro.input.SignUP;
import kondratkov.advocatesandnotariesrfpro.maps.Map_my_adres;

public class Profile_redactor extends Activity implements DatePickerFragment.DatePickedListener, Dialog_sud_ter.i_dialog_sudTer {

    private static final String DEBUG_TAG = "qwerty";

    public IN in;

    public  String URL_START ;
    public String json_prof_jurile = null;

    private int mYear;
    private int mMonth;
    private int mDay;

    String json_red_jur  ="{\"surname\":\"54\",\"name\":\"Р¤РµРґСЂ\",\"patronymic\":\"Р®СЂСЊРµРІРёС‡\",\"city\":\"2\",\"reg_num\":\"РђР»С‚Р°Р№234324\",\"qualific\":\"1,2,3,5\",\"stag\":\"1465468684532\",\"fsar\":\"true\",\"time_out\":\"false\",\"work_weekqnds\":\"false\",\"name_firm\":\"\",\"site\":\"www.yaeblanishe.oda\",\"coorX\":\"39.2494\",\"coorY\":\"51.7583\",\"adres\":\"РњРѕСЃРєРІР° СѓР».РҐРѕСЂРѕС€Р°СЏ Рґ.5\",\"phone\":\"_79204563214\",\"email\":\"qwerty@mil.ru\",\"password\":\"12345678\"}";
    String json_profile_jur ="{" +
            "\"idj\":\"12\"," +
            "\"password\":\"12345678\"," +
            "\"surname\":\"РљСЂРёРІРѕСЂРѕР¶РµРІ\"," +
            "\"name\":\"Р¤РµРґСЂ\"," +
            "\"patronymic\":\"Р®СЂСЊРµРІРёС‡\"," +
            "\"online\":\"true\"," +
            "\"rating\":\"3.7\"," +
            "\"qt_reply\":\"200\"," +
            "\"qt_thanks\":\"150\"," +
            "\"city\":\"2\"," +
            "\"reg_num\":\"РђР»С‚Р°Р№234324\"," +
            "\"qualific\":\"1,2,5\"," +
            "\"stag\":\"1465468684532\"," +
            "\"fsar\":\"true\"," +
            "\"time_out\":\"false\"," +
            "\"work_weekqnds\":\"false\"," +
            "\"name_firm\":\"\"," +
            "\"site\":\"www.yaeblanishe.oda\"," +
            "\"coorX\":\"39.2494\"," +
            "\"coorY\":\"51.7583\"," +
            "\"adres\":\"РњРѕСЃРєРІР° СѓР».РҐРѕСЂРѕС€Р°СЏ Рґ.5\"," +
            "\"phone\":\"_79204563214\"," +
            "\"email\":\"qwerty@mil.ru\"}";


    public EditText etPhoneSign, etEmailSign, etNameSign, etPatronymicSign, etSurnameSign, et_adres_sign, et_firm_sign, et_site_sign,  et_reg_num_sign, etPasswordSign, etPassword2Sign;
    public TextView tvPhoneSign, tvEmailSign, tvNameSign, tvPatronymicSign, tvSurnameSign, tv_adres_sign, tv_firm_sign,  et_stag_sign, tv_site_sign, tvRegionSign, tvCitySign, tv_qualification_sign, tv_work_weekqnds_sign, tv_reg_num_sign, tv_stag_sign, tv_time_out_sign, tv_fsar_sign, tvPasswordSign, tvPassword2Sign, tv_sud_ter;
    public CheckBox checkBox_time_out_sign, checkBox_fsar_sign, checkBox_work_weekqnds_sign, checkBoxLicen, checkBox_vziskanie_sign, checkBox_nastavnick_sign;
    public LinearLayout lila_qualification_sign, lila_fsar_sign;
    public ProgressBar progressBarRegionSign, progressBarCitySign;
    public int flagView = 0;
    public String strErrors = "";
    public String[] strCity = null;
    public String City = "";
    public String resul = "false";

    public EditText[]etArray;
    public TextView[]tvArray;
    public String[]strArray;
    public int position_arr= 0;
    public AlertDialog.Builder [] builderArray;
    public long sDate = 0;

    public String URL_ALL;
    public String phone_s = "";

    public CheckBox cb1_item, cb2_item, cb3_item, cb4_item, cb5_item,
            cb6_item, cb7_item, cb8_item, cb9_item, cb10_item,
            cb11_item, cb12_item, cb13_item, cb14_item, cb15_item,
            cb16_item, cb17_item, cb18_item, cb19_item, cb20_item;
    public CheckBox [] cb_array;
    public Boolean [] cb_array_bool;
    public int cb_kol = 0;

    public CheckBox checkBox_sign_1, checkBox_sign_2, checkBox_sign_3;

    public EditText et_uch_st_and_sign, et_status_ad_sign, et_dop_info_nagrad_sign, et_fsar_kogda_sign, et_fsar_gde_sign;

    public JSONObject jsonObjectJurProf = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_profile_redactor);

        in = new IN();
        URL_ALL = "http://"+in.get_url()+"/123.regjur";//adress d
        in.set_address("");

        etSurnameSign = (EditText) findViewById(R.id.etSurnameSign_red);
        etNameSign = (EditText) findViewById(R.id.etNameSign_red);
        etPatronymicSign = (EditText) findViewById(R.id.etPatronymicSign_red);
        et_reg_num_sign = (EditText) findViewById(R.id.et_reg_num_sign_red);

        et_adres_sign = (EditText) findViewById(R.id.et_adres_sign_red);
        et_site_sign = (EditText) findViewById(R.id.et_site_sign_red);
        et_firm_sign = (EditText) findViewById(R.id.et_firm_sign_red);
        etPhoneSign = (EditText) findViewById(R.id.etPhoneSign_red);
        etEmailSign = (EditText) findViewById(R.id.etEmailSign_red);
        etPasswordSign = (EditText) findViewById(R.id.etPasswordSign_red);
        etPassword2Sign = (EditText) findViewById(R.id.etPassword2Sign_red);

        et_uch_st_and_sign = (EditText) findViewById(R.id.et_uch_st_and_sign_red);
        et_status_ad_sign = (EditText) findViewById(R.id.et_status_ad_sign_red);
        et_dop_info_nagrad_sign = (EditText) findViewById(R.id.et_dop_info_nagrad_sign_red);
        et_fsar_kogda_sign = (EditText) findViewById(R.id.et_fsar_kogda_sign_red);
        et_fsar_gde_sign = (EditText) findViewById(R.id.et_fsar_gde_sign_red);

        checkBox_sign_1 = (CheckBox) findViewById(R.id.checkBox_sign_1_red);
        checkBox_sign_2 = (CheckBox) findViewById(R.id.checkBox_sign_2_red);
        checkBox_sign_3 = (CheckBox) findViewById(R.id.checkBox_sign_3_red);

        checkBox_vziskanie_sign = (CheckBox) findViewById(R.id.checkBox_vziskanie_sign_red);
        checkBox_nastavnick_sign = (CheckBox) findViewById(R.id.checkBox_nastavnick_sign_red);

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


                // etPhoneSign.setText(etPhoneSign.getText()+") ");
                //etPhoneSign.setSelection(s.length()+1);

                /*String formatted;
                String regex1 = "(\\+\\d)(\\d{3})";
                String regex2 = "(.+)(\\d{3})$";
                String regex3 = "(.+\\-)(\\d{2})$";

                // буду реализвывать ввод телефона в формате +х (ххх) ххх-хх-хх
                if (s.toString().matches(regex1)) {
                    formatted = String.valueOf(s).replaceFirst(regex1, "$1 ($2) ");
                    etPhoneSign.setText(formatted);
                    etPhoneSign.setSelection(formatted.length());
                } else if (s.toString().matches(regex2)) {
                    formatted = String.valueOf(s).replaceFirst(regex2, "$1$2-");
                    etPhoneSign.setText(formatted);
                    etPhoneSign.setSelection(formatted.length());
                } else if (s.toString().matches(regex3) && s.length() < 18) {
                    formatted = String.valueOf(s).replaceFirst(regex3, "$1$2-");
                    etPhoneSign.setText(formatted);
                    //etPhoneSign.setSelection(formatted.length());
                }*/
            }
        });

        //AlertDialog.Builder []
        builderArray = new AlertDialog.Builder[15];

        tvSurnameSign = (TextView) findViewById(R.id.tvSurnameSign_red);
        tvNameSign = (TextView) findViewById(R.id.tvNameSign_red);
        tvPatronymicSign = (TextView) findViewById(R.id.tvPatronymicSign_red);
        tv_reg_num_sign = (TextView) findViewById(R.id.tv_reg_num_sign_red);
        tv_stag_sign = (TextView) findViewById(R.id.tv_stag_sign_red);
        tv_adres_sign = (TextView) findViewById(R.id.tv_adres_sign_red);
        tv_site_sign = (TextView) findViewById(R.id.tv_site_sign_red);
        tv_firm_sign = (TextView) findViewById(R.id.tv_firm_sign_red);
        tvPhoneSign = (TextView) findViewById(R.id.tvPhoneSign_red);
        tvEmailSign = (TextView) findViewById(R.id.tvEmailSign_red);
        tvPasswordSign = (TextView) findViewById(R.id.tvPasswordSign_red);
        tvPassword2Sign = (TextView) findViewById(R.id.tvPassword2Sign_red);

        tv_sud_ter = (TextView) findViewById(R.id.tvSud_terSign_red);


        tvArray = new TextView[]{tvSurnameSign, tvNameSign, tvPatronymicSign,
                tv_adres_sign, tv_reg_num_sign, tv_site_sign,
                tv_firm_sign, tvPhoneSign, tvEmailSign, tvPasswordSign, tvPassword2Sign };

        tvRegionSign = (TextView) findViewById(R.id.tvRegionSign_red);
        tvCitySign = (TextView) findViewById(R.id.tvCitySign_red);
        et_stag_sign = (TextView) findViewById(R.id.et_stag_sign_red);


        progressBarRegionSign = (ProgressBar)findViewById(R.id.progressBarRegionSign_red);
        progressBarCitySign = (ProgressBar)findViewById(R.id.progressBarCitySign_red);

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

        checkBox_time_out_sign = (CheckBox)findViewById(R.id.checkBox_time_out_sign_red);
        checkBox_fsar_sign = (CheckBox)findViewById(R.id.checkBox_fsar_sign_red);
        checkBox_work_weekqnds_sign = (CheckBox)findViewById(R.id.checkBox_work_weekqnds_sign_red);

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

        cb1_item = (CheckBox)findViewById(R.id.cb1_item_red);
        cb2_item = (CheckBox)findViewById(R.id.cb2_item_red);
        cb3_item = (CheckBox)findViewById(R.id.cb3_item_red);
        cb4_item = (CheckBox)findViewById(R.id.cb4_item_red);
        cb5_item = (CheckBox)findViewById(R.id.cb5_item_red);
        cb6_item = (CheckBox)findViewById(R.id.cb6_item_red);
        cb7_item = (CheckBox)findViewById(R.id.cb7_item_red);
        cb8_item = (CheckBox)findViewById(R.id.cb8_item_red);
        cb9_item = (CheckBox)findViewById(R.id.cb9_item_red);
        cb10_item = (CheckBox)findViewById(R.id.cb10_item_red);
        cb11_item = (CheckBox)findViewById(R.id.cb11_item_red);
        cb12_item = (CheckBox)findViewById(R.id.cb12_item_red);
        cb13_item = (CheckBox)findViewById(R.id.cb13_item_red);
        cb14_item = (CheckBox)findViewById(R.id.cb14_item_red);
        cb15_item = (CheckBox)findViewById(R.id.cb15_item_red);
        cb16_item = (CheckBox)findViewById(R.id.cb16_item_red);
        cb17_item = (CheckBox)findViewById(R.id.cb17_item_red);
        cb18_item = (CheckBox)findViewById(R.id.cb18_item_red);
        cb19_item = (CheckBox)findViewById(R.id.cb19_item_red);
        cb20_item = (CheckBox)findViewById(R.id.cb20_item_red);

        cb_array = new CheckBox[]{cb1_item, cb2_item, cb3_item, cb4_item, cb5_item,
                cb6_item, cb7_item, cb8_item, cb9_item, cb10_item, cb11_item,
                cb12_item, cb13_item, cb14_item, cb15_item, cb16_item, cb17_item,
                cb18_item, cb19_item, cb20_item};
        cb_array_bool = new Boolean[]{false, false, false, false, false, false,
                false, false, false, false, false, false, false,
                false, false, false, false, false, false, false};

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

        lila_qualification_sign = (LinearLayout)findViewById(R.id.lila__qualification_sign_red);
        lila_fsar_sign = (LinearLayout)findViewById(R.id.lila_fsar_sign_red);

        checkBox_fsar_sign.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lila_fsar_sign.setLayoutParams(
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0));
                }else{
                    lila_fsar_sign.setLayoutParams(
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(4), (float) 0));
                    et_fsar_kogda_sign.setText("");
                    et_fsar_gde_sign.setText("");
                }
            }
        });

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();

        //new Profile_redactor.AsyncTaskProfileJur().execute();
    }
    // обновляем дату для вывода в TextView

    @Override
    protected void onStart() {
        super.onStart();
        etArray[3].setText(in.get_address());
        //new AsyncTaskEx().execute();
    }

    public void onDatePicked(Calendar date) {
        // выводим выбранную дату в текстовой метке 990
        mYear = date.get(Calendar.YEAR);
        mMonth = date.get(Calendar.MONTH) + 1;
        mDay = date.get(Calendar.DAY_OF_MONTH);
        sDate = date.getTimeInMillis();
        updateDisplay();
    }

    public void updateDisplay() {
        et_stag_sign.setText(new StringBuilder().append(mDay).append(".")
                .append(mMonth).append(".").append(mYear));
    }

    public int dpToPx(int dp){
        DisplayMetrics displayMetrics = Profile_redactor.this.getResources().getDisplayMetrics();
        int px =Math.round(dp *(displayMetrics.xdpi /DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    //--------------------------------------------------------------region
    public int City_num = 0;
    public String City_string = "";
    private void openDialogRegion(){

        final Dialog dialog = new Dialog(Profile_redactor.this);
        dialog.setTitle("region");
        dialog.setContentView(R.layout.input_dialog_reg_city);

        ListView lv = (ListView)dialog.findViewById(R.id.input_dialog_listview);
        Button btnDismiss = (Button) dialog.getWindow().findViewById(
                R.id.input_dialog_but_close);

        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Profile_redactor.DialogAdapterJsonList mam = new Profile_redactor.DialogAdapterJsonList(Profile_redactor.this, getDialogList(City_string));//
        lv.setAdapter(mam);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                try {
                    JSONObject jo = new JSONObject(City_string);
                    JSONArray jsonArray = jo.getJSONArray("array");
                    City = jsonArray.getJSONObject(position).getString("id");
                    if(City_num == 0){
                        City_num = jsonArray.getJSONObject(position).getInt("id");
                        tvRegionSign.setText(jsonArray.getJSONObject(position).getString("name"));
                        tvCitySign.setText("");
                        et_adres_sign.setText("");
                        Log.d("qwerty", "citi num 0");
                    }
                    else{
                        tvCitySign.setText(jsonArray.getJSONObject(position).getString("name"));
                        //if(et_adres_sign.equals("")){
                        et_adres_sign.setText(jsonArray.getJSONObject(position).getString("name")+", ");
                        //}
                        Log.d("qwerty", "citi num 1");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public List<JSONObject> getDialogList(String s) {
        List listMyMess = new ArrayList<>();

        try {
            JSONObject jo = new JSONObject(s);
            JSONArray jsonArray = jo.getJSONArray("array");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject arrayElement = jsonArray.getJSONObject(i);
                listMyMess.add(arrayElement);
            }

        } catch (JSONException e) {
            // TODO Auto-generatedcatchblock
            e.printStackTrace();
        }
        return listMyMess;
    }

    @Override
    public void iv_onSudTer(JudicialAreas judicialAreas, Region region) {

    }

    class DialogAdapterJsonList extends ArrayAdapter {

        private List<JSONObject> list = null;
        private Activity context;

        public DialogAdapterJsonList(Activity context, List<JSONObject> list) {
            super(context, R.layout.input_item_reg, list);
            this.context = context;
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.input_item_reg, parent, false);
            TextView tv = (TextView) rowView.findViewById(R.id.tv_item);
            try {
                tv.setText(list.get(position).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return rowView;
        }
    }
//___|||__|____|___|_______|____|_____|_______|____|__region

    public boolean bool_but_qualification = true;
    public void onClick(View v){
        switch (v.getId()){
            case R.id.input_dialog_but_close_red:
                Profile_redactor.this.finish();
                break;
            case R.id.but_registr_red:
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

                    }
                    else if(i == etArray.length){

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

                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("idj",in.get_id_jur());
                                jsonObject.put("surname",etSurnameSign.getText());
                                jsonObject.put("name",etNameSign.getText());
                                jsonObject.put("patronymic",etPatronymicSign.getText());
                                jsonObject.put("city",City);
                                jsonObject.put("reg_num",et_reg_num_sign.getText());
                                jsonObject.put("qualific",spec);
                                jsonObject.put("bup",bup);
                                jsonObject.put("vziskanie",checkBox_vziskanie_sign.isChecked());
                                jsonObject.put("nastavnick",checkBox_nastavnick_sign.isChecked());
                                jsonObject.put("status_ad",et_status_ad_sign.getText());
                                jsonObject.put("uch_st_and",et_uch_st_and_sign.getText());
                                jsonObject.put("dop_info_nagrad",et_dop_info_nagrad_sign.getText());
                                jsonObject.put("fsar_kogda",et_fsar_kogda_sign.getText());
                                jsonObject.put("fsar_gde",et_fsar_gde_sign.getText());
                                jsonObject.put("stag",String.valueOf(sDate));
                                jsonObject.put("fsar",checkBox_fsar_sign.isChecked());
                                jsonObject.put("time_out", checkBox_time_out_sign.isChecked());
                                jsonObject.put("work_weekqnds", checkBox_work_weekqnds_sign.isChecked());
                                jsonObject.put("name_firm", et_firm_sign.getText());
                                jsonObject.put("site", et_site_sign.getText());
                                jsonObject.put("coorX", in.get_coor_x());
                                jsonObject.put("coorY", in.get_coor_y());
                                jsonObject.put("adres", et_adres_sign.getText());
                                jsonObject.put("phone", etPhoneSign.getText());
                                jsonObject.put("email", etEmailSign.getText());
                                jsonObject.put("password", in.get_password_jur());
                                jsonObject.put("sudter", in.get_id_sud_ter());
                                jsonObject.put("tip_who", 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }//checkBox_time_out_sign, checkBox_fsar_sign, checkBox_work_weekqnds_sign;
                            json_red_jur = String.valueOf(jsonObject);

                            //new Profile_redactor.AsyncTaskSign().execute();

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

            case R.id.but_qualification_sign_red:
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
            case R.id.but_stag_red:
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getFragmentManager(), "datePicker");
                break;

            case R.id.butSud_terSign_red:
                Dialog_sud_ter ds = new Dialog_sud_ter(Profile_redactor.this, in.get_url());
                ds.openDialogSudTer();
                break;

            case R.id.ibut_geo_jur_red:
                in.set_address(String.valueOf(et_adres_sign.getText()));
                Intent intent = new Intent(Profile_redactor.this, Map_my_adres.class);
                startActivity(intent);
                break;


            case R.id.butRegionSign_red:
                City_num = 0;
                progressBarRegionSign.setVisibility(ProgressBar.VISIBLE);

                break;

            case R.id.butCitySign_red:
                if(City_num == 0){
                    Toast.makeText(Profile_redactor.this,
                            "Не выбран регион!",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    progressBarCitySign.setVisibility(ProgressBar.VISIBLE);

                }
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        String s = strErrors;
        if(id == 33){
            Log.d("qwerty", "builderArray33");
            AlertDialog.Builder builder33 = new AlertDialog.Builder(this);
            builder33.setMessage("Запрос на изменение профиля отправлен, после проверки Ваших данных, будут изменены данные профиля!")
                    .setCancelable(false)

                    .setNegativeButton("закрыть",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Profile_redactor.this.finish();
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

    class AsyncTaskSign1 extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... params) {
            URL_ALL = "http://"+in.get_url()+"/123.updjur";
            Log.d("qwerty11", "Что ушло на рег "+ json_red_jur);
            return ServerSendData.sendRegData(URL_ALL, json_red_jur);
        }

        protected void onPostExecute(String result) {
            JSONObject object = null;
            Log.d("qwerty11", "OTV "+result);
            showDialog(33);
            try {
                object = new JSONObject(result);
                String status = object.getString("status");

                if(object.getString("status").equals("OK")){
                    showDialog(33);
                }else if(object.getString("status").equals("Error: already exists phone number")){
                    Log.d("qwerty", "OTV "+result);
                    showDialog(34);
                }else if(object.getString("status").equals("Error: already exists email")){
                    showDialog(35);
                }else if(object.getString("status").equals("Error: already exists regnum")){
                    showDialog(36);
                }
                //tvGet.setText(status);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class AsyncTaskProfileJur1 extends AsyncTask<Void, Integer, String> {
        // фоновая работа
        @Override
        protected String doInBackground(Void... params) {
            URL_START = "http://"+in.get_url()+"/123.profjur";

            jsonObjectJurProf = new JSONObject();
            try {
                jsonObjectJurProf.put("idj", in.get_id_jur());
                jsonObjectJurProf.put("password", in.get_password_jur());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            json_prof_jurile = String.valueOf(jsonObjectJurProf);
            return ServerSendData.sendRegData(URL_START, json_prof_jurile);
        }

        // выполняется после doInBackground, имеет доступ к UI
        protected void onPostExecute(String result) {
            if(result!=null){
                Log.d("qwerty", "ответ сервера "+ result);

                try {
                    JSONObject jo = new JSONObject(result);
                    /*
                                jsonObject.put("city",City);
                                jsonObject.put("reg_num",et_reg_num_sign.getText());
                                jsonObject.put("qualific",spec);
                                jsonObject.put("bup",bup);
                                jsonObject.put("vziskanie",checkBox_vziskanie_sign.isChecked());
                                jsonObject.put("nastavnick",checkBox_nastavnick_sign.isChecked());
                                jsonObject.put("status_ad",et_status_ad_sign.getText());
                                jsonObject.put("uch_st_and",et_uch_st_and_sign.getText());
                                jsonObject.put("dop_info_nagrad",et_dop_info_nagrad_sign.getText());
                                jsonObject.put("fsar_kogda",et_fsar_kogda_sign.getText());
                                jsonObject.put("fsar_gde",et_fsar_gde_sign.getText());
                                jsonObject.put("stag",String.valueOf(sDate));
                                jsonObject.put("fsar",checkBox_fsar_sign.isChecked());
                                jsonObject.put("time_out", checkBox_time_out_sign.isChecked());
                                jsonObject.put("work_weekqnds", checkBox_work_weekqnds_sign.isChecked());
                                jsonObject.put("name_firm", et_firm_sign.getText());
                                jsonObject.put("site", et_site_sign.getText());
                                jsonObject.put("coorX", in.get_coor_x());
                                jsonObject.put("coorY", in.get_coor_y());
                                jsonObject.put("adres", et_adres_sign.getText());
                                jsonObject.put("phone", etPhoneSign.getText());
                                jsonObject.put("email", etEmailSign.getText());
                                jsonObject.put("password", etPasswordSign.getText());
                                jsonObject.put("sudter", in.get_id_sud_ter());
                                jsonObject.put("tip_who", 1);*/
                    etSurnameSign.setText(jo.getString("surname"));
                    etNameSign.setText(jo.getString("name"));
                    etPatronymicSign.setText(jo.getString("patronymic"));
                    tvCitySign.setText(jo.getString("city"));
                    tv_sud_ter.setText(jo.getString("sudter"));
                    et_adres_sign.setText(jo.getString("adres"));
                    et_reg_num_sign.setText(jo.getString("reg_num"));
                    et_stag_sign.setText(jo.getString("stag"));
                    //"bup"
                    et_status_ad_sign.setText("status_ad");
                    et_dop_info_nagrad_sign.setText(jo.getString("dop_info_nagrad"));
                    et_uch_st_and_sign.setText(jo.getString("uch_st_and"));
                    checkBox_nastavnick_sign.setChecked(jo.getBoolean("nastavnick"));
                    checkBox_time_out_sign.setChecked(jo.getBoolean("time_out"));
                    checkBox_vziskanie_sign.setChecked(jo.getBoolean("vziskanie"));
                    checkBox_work_weekqnds_sign.setChecked(jo.getBoolean("work_weekqnds"));
                    if(jo.getBoolean("fsar")){
                        et_fsar_gde_sign.setText(jo.getString("fsar_gde"));
                        et_fsar_kogda_sign.setText(jo.getString("fsar_kogda"));
                    }
                    et_site_sign.setText(jo.getString( "site"));
                    et_firm_sign.setText(jo.getString("name_firm"));
                    etPhoneSign.setText(jo.getString("phone"));
                    etEmailSign.setText(jo.getString("email"));
                    etPasswordSign.setText(in.getPassword());
                    etPassword2Sign.setText(in.getPassword());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{

                Toast.makeText(Profile_redactor.this,
                        "Неудалось связаться с сервером!",
                        Toast.LENGTH_LONG).show();
                Profile_redactor.this.finish();
            }
        }
    }
    public static class ServerSendData {

        //public static IN iny = new IN();
        public static String sendRegData(String stringUrl, String json_user_str) {

            //String log = new AdIn().get_id_user(); iny.get_id_user();
            //String pas = iny.get_password_user();

            String result =  null;
            try {

                // Log.d(DEBUG_TAG, "z3 - "+String.valueOf(iny.getURL()));
                URL url = new URL(stringUrl);//new AdIn().getURL());//
                URLConnection connection = url.openConnection();

                HttpURLConnection httpConnection = (HttpURLConnection) connection;

                httpConnection.setDoOutput(true);
                httpConnection.setChunkedStreamingMode(0);
                OutputStream out = new BufferedOutputStream(httpConnection.getOutputStream());

                out.write(json_user_str.getBytes());

                out.flush();
                out.close();

                InputStream in = new BufferedInputStream(httpConnection.getInputStream());
                //readStream(in);
                //finally {
                //    httpConnection.disconnect();
                // }

                int responseCode = 0;
                responseCode = httpConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream is = httpConnection.getInputStream();

                    BufferedReader r = new BufferedReader(new InputStreamReader(in));

                    result = r.readLine();

                } else {
                }
            } catch (MalformedURLException e) {
            } catch (IOException e1) {
            }

            return result;
        }
    }
}
