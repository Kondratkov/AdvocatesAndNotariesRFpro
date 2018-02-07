package kondratkov.advocatesandnotariesrfpro.input;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

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

import kondratkov.advocatesandnotariesrfpro.IN;
import kondratkov.advocatesandnotariesrfpro.MainActivity;
import kondratkov.advocatesandnotariesrfpro.MapsActivity;
import kondratkov.advocatesandnotariesrfpro.MyApplication;
import kondratkov.advocatesandnotariesrfpro.R;
import kondratkov.advocatesandnotariesrfpro.account.Address;
import kondratkov.advocatesandnotariesrfpro.account.City;
import kondratkov.advocatesandnotariesrfpro.account.ClientAccount;
import kondratkov.advocatesandnotariesrfpro.account.JuristAccount;
import kondratkov.advocatesandnotariesrfpro.account.JuristSpecialization;
import kondratkov.advocatesandnotariesrfpro.account.JuristSpecializationSector;
import kondratkov.advocatesandnotariesrfpro.account.Region;
import kondratkov.advocatesandnotariesrfpro.api_classes.BaseJuristAccount;

public class LogIN extends Activity {

    private static final String DEBUG_TAG = "qwerty";
    public SharedPreferences sPref;

    public EditText etPhoneEmali, etPasswl;
    public TextView tvPhoneEmali, tvPasswl, tv_log_in_error;
    public CheckBox cb_save;
    public int flagView = 0;
    public boolean bool_log_in_server;

    IN in;
    public String json_login = "phone_and_emali, password ";

    public Button butInputL;

    public  String URL_ALL;//adress d

    public Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_log_in);

        dialog = new Dialog(LogIN.this);
        in = new IN();
        URL_ALL = "http://"+in.get_url()+"/123.authjur";//adress d

        etPhoneEmali = (EditText) findViewById(R.id.etPhoneEmail);
        etPasswl = (EditText) findViewById(R.id.etPasswl);
        tvPhoneEmali = (TextView) findViewById(R.id.tvPhoneEmail);
        tvPasswl = (TextView) findViewById(R.id.tvPasswl);
        cb_save = (CheckBox)findViewById(R.id.log_in_cb_save);

        tv_log_in_error = (TextView) findViewById(R.id.tv_log_in_error);
        butInputL= (Button)findViewById(R.id.butInputL);

        etPhoneEmali.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    flagView = 1;
                    onTouchL();
                }
                return false;
            }
        });

        etPasswl.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    flagView = 2;
                    onTouchL();
                }
                return false;
            }
        });

        sPref = getPreferences(MODE_PRIVATE);
        etPhoneEmali.setText(sPref.getString("login", ""));
        etPasswl.setText(sPref.getString("password", ""));
    }

    @Override
    protected void onStart() {
        super.onStart();
        in.setOnemay(false);
        Log.d("qwerty", "Start LogIN");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("qwerty", "Stop LogIN");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("qwerty", "Destroy LogIN");
    }

    public void onTouchL() {

        if (flagView == 1) {
        } else {
        }

        if (etPhoneEmali.getText().toString().equals("")) {
            if (flagView == 1) {
                tvPhoneEmali.setText(R.string.log_in_mail);
                etPhoneEmali.setHint("");
            } else {
                tvPhoneEmali.setText("");
                etPhoneEmali.setHint(R.string.log_in_mail);
            }
        } else {
            tvPhoneEmali.setText(R.string.log_in_mail);
        }

        if (etPasswl.getText().toString().equals("")) {
            if (flagView == 2) {
                tvPasswl.setText(R.string.log_in_pass);
                etPasswl.setHint("");
            } else {
                tvPasswl.setText("");
                etPasswl.setHint(R.string.log_in_pass);
            }
        } else {
            tvPasswl.setText(R.string.log_in_pass);
        }

    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.butInputL:
                //view.setEnabled(false);
                if (etPhoneEmali.getText().toString().equals("")) {
                    tv_log_in_error.setText("Заполните поле E-mail или телефон!");
                    Log.d("qwerty","t1");
                    bool_log_in_server = false;
                } else if (etPasswl.getText().toString().equals("")) {
                    tv_log_in_error.setText("Заполните поле пароль!");
                    Log.d("qwerty", "t2");
                    bool_log_in_server = false;
                } else {
                    Log.d("qwerty", "t3");
                    tv_log_in_error.setText("");
                    bool_log_in_server = true;
                }
                if (bool_log_in_server) {
                    Log.d("qwerty", "setEnabled = 1???????!");
                    flagView = 0;
                    onTouchL();

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("phone_and_emali", etPhoneEmali.getText());
                        jsonObject.put("password", etPasswl.getText());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    json_login = String.valueOf(jsonObject);
                    //!!!! - делаем запрос на avthor
                    Dialog_loc();
                }

               /*if(String.valueOf(in.getEmail_reg()).equalsIgnoreCase(String.valueOf(etPhoneEmail.getText()))&&
                        String.valueOf(in.getPassword()).equalsIgnoreCase(String.valueOf(etPasswl.getText())))
                {
                    in.setOnemay(true);
                    LogIN.this.finish();
                }
                else
                {
                    in.setOnemay(false);
                }/**/
                break;

            case R.id.forget_password:

                dialog_password_help();
                /*String SpSpS;
                Region region = new Region();
                region.Name = "QWERT";
                SpSpS = new Gson().toJson(region);
                City city = new City();
                city.Name = "qweqrt";
                SpSpS = new Gson().toJson(city);
                Address address = new Address();
                address.Region = region;
                address.City = city;
                address.Street = "asdf";
                SpSpS = new Gson().toJson(address);
                SpSpS = "sdf";
                SpSpS = "sdf";
                SpSpS = "sdf";

                 ClientAccount clientAccount = new ClientAccount();
                JuristAccount juristAccount = new JuristAccount();
                JuristSpecialization juristSpecialization = new JuristSpecialization();
                JuristSpecializationSector juristSpecializationSector = new JuristSpecializationSector();
                JuristSpecialization[] juristSpecializations = new JuristSpecialization[3];
                for(int i =0; i<3; i++){
                    juristSpecializationSector.SectorName = "sdfsdf";
                    juristSpecialization.Specialization = juristSpecializationSector;
                    juristSpecializations[i]= juristSpecialization;
                }
                juristAccount.Name = "!!!!!!!!!!";
                juristAccount.Specializations = juristSpecializations;
                clientAccount.AccountProfile = juristAccount;
                Gson gson = new Gson();
                String ss = gson.toJson(clientAccount);
                int i = 3;*/
                // /Intent inSid = new Intent(LogIN.this, MapsActivity.class);
                //startActivity(inSid);
                //LogIN.this.finish();
                break;

            case R.id.butReg:
                in.set_coor_x(-5051);
                in.set_coor_y(-5051);
                in.set_address("");

                new UrlConnectionTaskJuristSpecializationSectors().execute();
                //LogIN.this.finish();
                break;
        }
    }

    public void iditeNaHui(String s ){
        Log.d("qwerty", " ___ "+s);
        if(s == null){
            tv_log_in_error.setText("Нет сети!");
        }
        else{
            try {
                JSONObject json= new JSONObject(s);//"{\"status\":\"Ok\"," + "\"id\":\"13\"}");//in.get_reply_server());

                if (json.getString("status").equals("Error: Unknown login")) {
                    tv_log_in_error.setText("Неверный e-mail или номер телефона!");
                } else if (json.getString("status").equals("Error: Bad password")) {
                    tv_log_in_error.setText("Неверный пароль!");
                } else if (json.getString("status").equals("OK")) {
                    tv_log_in_error.setText("");
                    in.set_id_jur(json.getInt("idj"));
                    in.set_name_jur(json.getString("name"));
                    Log.d("qwerty", "setEnabled = c2");
                    in.set_password_jur(String.valueOf(etPasswl.getText()));
                    in.setOnemay(true);

                    if(cb_save.isChecked()){
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putString("login", String.valueOf(etPhoneEmali.getText()));
                        ed.commit();
                        SharedPreferences.Editor ed1 = sPref.edit();
                        ed1.putString("password", String.valueOf(etPasswl.getText()));
                        ed1.commit();
                    }

                    Intent intent = new Intent(LogIN.this, MainActivity.class);
                    startActivity(intent);
                    LogIN.this.finish();
                } else {
                    tv_log_in_error.setText("Ошибка!");
                }

                //in.set_id_user(String.valueOf(etPhoneEmali.getText()));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("qwerty", "setEnabled = catch");
            }
        }
    }

    public void start_power(String result, int code){
        if(code==400){
            tv_log_in_error.setText("Неверный e-mail или пароль!!");
        }
        else if(code>=200&&300>code){
            tv_log_in_error.setText("");

            try {
                JSONObject json = new JSONObject(result);
                in.set_token(json.getString("access_token"));
                in.set_token_type(json.getString("token_type"));
                Log.d("qwertyTOKEN", in.get_token());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            in.setOnemay(true);

            if(cb_save.isChecked()){
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("login", String.valueOf(etPhoneEmali.getText()));
                ed.commit();
                SharedPreferences.Editor ed1 = sPref.edit();
                ed1.putString("password", String.valueOf(etPasswl.getText()));
                ed1.commit();
            }

            new UrlConnectionTaskAddProfile().execute();

        } else {
            tv_log_in_error.setText("Ошибка!");
        }
    }

    private void Dialog_loc() {
        dialog.setTitle("загрузка...");
        dialog.setContentView(R.layout.input_dialog_window);
        dialog.setCancelable(true);

        new UrlConnectionTask().execute("grant_type=password&username="+String.valueOf(etPhoneEmali.getText())+
                "&password="+String.valueOf(etPasswl.getText()));

        //new AsyncTaskExample().execute();

        //Button btnDismiss = (Button) dialog.getWindow().findViewById(
        // R.id.btn_cancelB);

        // btnDismiss.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        //      dialog.dismiss();
        //     }
        //});
//
        dialog.show();
    }


    public void  putAdvocateProfileData(){
        MyApplication.getInstance().getBaseJuristAccount().IsOnline = true;
        MyApplication.getInstance().getBaseJuristAccount().AccountType = BaseJuristAccount.AccountTypes.Jurist;

        Gson gson = new Gson();
        new UrlConnectionTaskPubAdvocate().execute(gson.toJson(MyApplication.getInstance().getBaseJuristAccount()));
    }




    public void dialog_password_help(){
        final Dialog dialog = new Dialog(LogIN.this);
        dialog.setTitle("");
        dialog.setContentView(R.layout.input_password_help);

        Button btnClose = (Button) dialog.getWindow().findViewById(
                R.id.button_help_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final Button btnYes = (Button) dialog.getWindow().findViewById(
                R.id.button_help_yes);

        final EditText et = (EditText) dialog.getWindow().findViewById(R.id.etPhoneEmail_help);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et.getText().length()<3){
                    Toast.makeText(LogIN.this,
                            "Для напоминания пароля нужно ввести e-mail!",
                            Toast.LENGTH_LONG).show();
                }else{
                    JSONObject json_st = new JSONObject();
                    try {
                        json_st.put("email", et.getText());//String.valueOf(in.get_id()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String json_str = String.valueOf(json_st);
                    new UrlConnectionTaskForgotPassword().execute(json_str);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }


    class UrlConnectionTask extends AsyncTask<String, Void, String> {

        int code;

        @Override
        protected String doInBackground(String... params) {

            String result = "";
            OkHttpClient client = new OkHttpClient();

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            RequestBody formBody = RequestBody.create(JSON, params[0]);

            Request request = new Request.Builder()
                    .header("Content-Type", "x-www-form-urlencoded")
                    //.url("http://192.168.0.100/token")
                    //.url("http://195.128.124.172/token")
                    .url("http://app.mmka.info/token")
                    //.url("http://vsundupey.vds.serverdale.com/token")
                    .post(formBody)
                    .build();


            try {
                Response response = client.newCall(request).execute();

                code = response.code();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    Log.d("DEBUG", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                result = response.body().string();
                code = response.code();
                Log.d("DEBUG", result);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result==null){
                tv_log_in_error.setText("Нет сети!");
            }else if(result!=null && code>=200 && code<300){
                dialog.dismiss();
                start_power(result, code);
                super.onPostExecute(result);
            }else if(result!=null && code>=300 && code<=500){
                dialog.dismiss();
                tv_log_in_error.setText("Неправильный пароль или логин! Если вы зарегистрировались недавно, возможно Вашу регистрацию еще не подтвердили, проверьте почту!");
            }else{
                dialog.dismiss();
                tv_log_in_error.setText("Нет сети!");
            }

        }
    }

    class UrlConnectionTaskAddProfile extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String result = "";

            OkHttpClient client = new OkHttpClient();

            MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");

            //RequestBody formBody = RequestBody.create(JSON, json_signup);

            Request request = new Request.Builder()
                    .header("Authorization", in.get_token_type()+" "+in.get_token())
                    .url("http://"+in.get_url()+"/account/GetProfile")
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

            if(result!=null && code>=200 && code<300){
                Gson gson = new Gson();
                MyApplication.getInstance().setBaseJuristAccount(gson.fromJson(result, BaseJuristAccount.class));
                in.set_id_jur(MyApplication.getInstance().getBaseJuristAccount().Id);
                in.set_FIO(MyApplication.getInstance().getBaseJuristAccount().Fio);

                putAdvocateProfileData();

            }else{

            }


            super.onPostExecute(result);
        }
    }

    int code;
    class UrlConnectionTask11 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String result = "";

            OkHttpClient client = new OkHttpClient();

            MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");

            //RequestBody formBody = RequestBody.create(JSON, json_signup);

            Request request = new Request.Builder()
                    .header("Authorization", in.get_token_type()+" "+in.get_token())
                    .url("http://"+in.get_url()+"/account/GetProfile")
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

            if(result!=null ){
                Gson gson = new Gson();
                try{
                    BaseJuristAccount juristAccount = gson.fromJson(result, BaseJuristAccount.class);
                    in.set_id_jur(juristAccount.Id);
                    in.set_FIO(juristAccount.Fio);

                }catch (Exception e){

                }

            }else{

            }
            super.onPostExecute(result);
        }
    }

    class UrlConnectionTaskForgotPassword extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String result = "";

            OkHttpClient client = new OkHttpClient();
            MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");
            String ss = "http://"+in.get_url()+"/Account/ForgotPassword";

            Request request = new Request.Builder()
                    //.header("Authorization", in.get_token_type()+" "+in.get_token())
                    .url("http://"+in.get_url()+"/Account/ForgotPasswordJurist")
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
            if(code>=200&&code<300&&result!=null){
                Toast.makeText(LogIN.this,
                        "Запрос на напоминание пароля принят! Ответ будет направлен на Вашу почту!",
                        Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }
    }

    class UrlConnectionTaskJuristSpecializationSectors extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String result = "";

            OkHttpClient client = new OkHttpClient();
            MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");
            String ss = "http://"+in.get_url()+"/JuristSpecializationSectors/GetJuristSpecializationSectors";

            Request request = new Request.Builder()
                    //.header("Authorization", in.get_token_type()+" "+in.get_token())
                    .url("http://app.mmka.info/api/JuristSpecializationSectors/GetJuristSpecializationSectors")//"http://"+in.get_url()+"/JuristSpecializationSectors/GetJuristSpecializationSectors")
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
            if(code>=200&&code<300&&result!=null){

                try{
                    Gson gson = new Gson();
                    in.set_list_specialization(gson.fromJson(result, JuristSpecialization[].class));
                }catch (Exception e){}

                if(in.get_list_specialization().length!=0){

                    String s = in.get_list_specialization()[2].SectorName;
                    Intent intentReg1 = new Intent(LogIN.this, SignUP.class);
                    //Intent intentReg1 = new Intent(LogIN.this, Asked_user.class);
                    startActivity(intentReg1);
                }
                else {
                    Toast.makeText(LogIN.this,
                            "Нет связи с сервером!",
                            Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(LogIN.this,
                        "Нет связи с сервером!",
                        Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }
    }

    class UrlConnectionTaskPubAdvocate extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String result = "";
            OkHttpClient client = new OkHttpClient();
            MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");

            String s1 = params[0];
            String s2 ="http://"+in.get_url()+"/JuristAccounts/PutJuristAccount/"+MyApplication.getInstance().getBaseJuristAccount().Id;
            String s3 = in.get_token_type()+" "+in.get_token();

            //RequestBody formBody = RequestBody.create(JSON, json_signup);
            Request request = new Request.Builder()
                    .header("Authorization", in.get_token_type()+" "+in.get_token())
                    .url("http://"+in.get_url()+"/JuristAccounts/PutJuristAccount/"+MyApplication.getInstance().getBaseJuristAccount().Id)
                    .put(RequestBody.create(MEDIA_TYPE_MARKDOWN, params[0]))
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
            //Gson gson = new Gson();
            if(result!=null && 200<=code && code<300){
                Intent intent = new Intent(LogIN.this, MainActivity.class);
                startActivity(intent);
                LogIN.this.finish();
            }
            super.onPostExecute(result);
        }
    }
/*

Gson gson = new Gson();
                try{
                    JuristAccount juristAccount = gson.fromJson(result, JuristAccount.class);
                    in.set_id_jur(juristAccount.Id);
                }catch (Exception e){

                }



    class AsyncTaskExample extends AsyncTask<Void, Integer, String> {
        // фоновая работа

        @Override
        protected String doInBackground(Void... params) {
            String url = "http://"+in.get_url()+"/123.authjur";//adress d
            Log.d(DEBUG_TAG, "ЗАПРОС НА ВХОД " + json_login);
            return ServerSendData.sendRegData(url, json_login);
        }

        // выполняется после doInBackground, имеет доступ к UI
        protected void onPostExecute(String result) {
            Log.d(DEBUG_TAG, "ОТВЕТ НА ВХОД " + result);
            //new AdIn().startMain();
            tv_log_in_error.setText("eeee");
            dialog.dismiss();
            iditeNaHui(result);


            //Log.d(DEBUG_TAG, "zetta3 "+ new LogIN().etPhoneEmali.getText());
            //new LogIN().iditeNaHui();

            // status будет содержать login_busy
        }
    }
    public static class ServerSendData {

        public static String sendRegData(String stringUrl, String json_user_str) {

            String result = null;
            try {
                URL url = new URL(stringUrl);
                URLConnection connection = url.openConnection();
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setDoOutput(true);

                httpConnection.setChunkedStreamingMode(0);
                OutputStream out = new BufferedOutputStream(httpConnection.getOutputStream());
                out.write(json_user_str.getBytes());

                out.flush();
                out.close();

                InputStream in = new BufferedInputStream(httpConnection.getInputStream());
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
*/}

