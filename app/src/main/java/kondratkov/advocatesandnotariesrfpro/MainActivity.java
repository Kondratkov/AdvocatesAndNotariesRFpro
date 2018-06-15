package kondratkov.advocatesandnotariesrfpro;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
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
import java.util.ArrayList;
import java.util.List;

import kondratkov.advocatesandnotariesrfpro.api_classes.BaseJuristAccount;
import kondratkov.advocatesandnotariesrfpro.input.LogIN;
import kondratkov.advocatesandnotariesrfpro.my_info.My_frag_quest_chat;
import kondratkov.advocatesandnotariesrfpro.my_info.My_frag_quest_forum;
import kondratkov.advocatesandnotariesrfpro.my_info.My_frag_quest_private;
import kondratkov.advocatesandnotariesrfpro.my_info.My_profile;

public class MainActivity extends FragmentActivity implements View.OnTouchListener{

    private static final String DEBUG_TAG = "qwerty";
    private MyAdapter mAdapter;
    private ViewPager mPager;

    public TextView tv_1, tv_2;
    public ImageView iv_1, iv_2;
    public IN in;

    public  static final int RequestPermissionCode  = 1 ;

    public SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_1 = (TextView)findViewById(R.id.my_quest_tv_1_main);
        tv_2 = (TextView)findViewById(R.id.my_quest_tv_2_main);

        iv_1 = (ImageView)findViewById(R.id.my_quest_iv_1_main);
        iv_2 = (ImageView)findViewById(R.id.my_quest_iv_2_main);

        onPermissions();

        in = new IN();
        in.set_context(this);
        new UrlConnectionTask().execute();

        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager_main);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(0); // выводим one экран

        mPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View v, float pos) {
                final float invt = Math.abs(Math.abs(pos) - 1);
                v.setAlpha(invt);
                int alpha = (int)(pos*215);
                tv_1.setTextColor(Color.argb(alpha + 40, 255, 255, 255));
                iv_1.setBackgroundColor(Color.argb(alpha + 40, 254, 179, 42));

                tv_2.setTextColor(Color.argb((int) (215 - alpha) + 40, 255, 255, 255));
                iv_2.setBackgroundColor(Color.argb((int) (215 - alpha) + 40, 254, 179, 42));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        in.setpotok_main(true);

        if(in.getOnemay()==false){
            Intent intent = new Intent(MainActivity.this, LogIN.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
        else{
            in = new IN();
            in.setpotok_main(true);//zapusk potoka

            if(in.get_start_service()==false){
                startService(new Intent(this, Service_mess.class));
                in.set_start_service(true);
            }
            in.set_context(MainActivity.this);
            in.set_activity(MainActivity.this);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("qwerty", "STOP SERVIS");
        stopService(new Intent(this, Service_mess.class));
    }

    public void onPermissions(){
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, RequestPermissionCode);
    }

    public void onClick(View v){
        Intent intent = null;
        switch (v.getId()){

            case R.id.main_but_menu:
                intent = new Intent(MainActivity.this, Sidebar.class);
                startActivity(intent);
                break;

            case R.id.my_quest_tv_1_main:
                mPager.setCurrentItem(0);
                break;

            case R.id.my_quest_tv_2_main:
                mPager.setCurrentItem(1);
                break;
        }
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Log.d("qwerty", "Fragment 1");
                    //return new My_frag_quest_private();
                    return new My_frag_quest_chat();

                case 1:
                    Log.d("qwerty", "Fragment 2");
                    return new My_frag_quest_private();//forum();
                default:
                    return null;
            }
        }
    }

    int code;
    class UrlConnectionTask extends AsyncTask<String, Void, String> {

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
                BaseJuristAccount juristAccount = gson.fromJson(result, BaseJuristAccount.class);
                in.set_id_jur(juristAccount.Id);
                //start_activity();
            }else{

            }
            super.onPostExecute(result);
        }
    }

    public float xX = 0;
    public float xNew = 0;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case (MotionEvent.ACTION_UP):
                if(xNew > 300){
                    Intent intent = new Intent(MainActivity.this, Sidebar.class);
                    startActivity(intent);
                }
                Log.d("qwerty", "ACTION_UP");
                break;
            case (MotionEvent.ACTION_DOWN):
                xX = event.getX();
                Log.d("qwerty", "ACTION_DOWN xX "+xX);
                break;
            case (MotionEvent.ACTION_MOVE):
                Log.d("qwerty", "ACTION_MOVE ");
                int historySize = event.getHistorySize();
                for (int i = 0; i <historySize; i++) {
                    float x = event.getHistoricalX(i);
                    processMovement(x);
                }
                float x = event.getX();
                processMovement(x);
        }
        return false;
    }
    private void processMovement(float _x) {
        // Todo: Обработка движения.
        xNew = xX -_x;
        Log.d("qwerty", "xNew " + xNew);
    }
}


/*import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.util.List;

import kondratkov.advocatesandnotariesrfpro.input.LogIN;

public class MainActivity extends Activity implements View.OnTouchListener{

    IN in;
    private static final String DEBUG_TAG = "qwerty";
    public String json_t = "";
    public String json_str = "";

    public List<JSONObject> listT = null;
    public JSONArray jsonArrayT = null;
    public JSONObject jsonObjectT = null;

    public ProgressBar main_progressBar;

    public ListView lv_forum;
    public JSONArray jsonArray_forum = null;
    public JSONObject jsonObject_forum = null;
    public List<JSONObject> list_forum  = null;
    MyAdapterJsonList mam = null;

    public Thread thread;

    public SharedPreferences sPref;

    public boolean bool_start_potoc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        in = new IN();
        main_progressBar= (ProgressBar)findViewById(R.id.main_progressBar);
        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        OneStart();
        //MainActivity.this.finish();
    }

    @Override

    protected void onStart() {
        super.onStart();
        Log.d("qwerty", "Start MainActivity");
        in.setChoice_of_menus(0);
        lv_forum = (ListView)findViewById(R.id.main_listView);
        lv_forum.setOnTouchListener(this);
        if(in.getOnemay()==false){
            Intent intent = new Intent(MainActivity.this, LogIN.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
        else{
            in = new IN();
            JSONObject json_st = new JSONObject();
            try {
                json_st.put("idu", in.get_id_usr());//String.valueOf(in.get_id()));
                json_st.put("idj", in.get_id_jur());
                json_st.put("password", in.get_password_jur());
                json_st.put("tip_who", "1");//String.valueOf(adin.get_mess_id()));
                in.set_place(1);
                json_st.put("place", in.get_place());
                json_st.put("date", 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            json_str = String.valueOf(json_st);
            potock(true);

            if(in.get_start_service()==false){
                startService(new Intent(this, Service_mess.class));
                in.set_start_service(true);
            }
            //new AsyncTaskT().execute();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        potock(false);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        potock(false);
        stopService(new Intent(this, Service_mess.class));
    }

    public void start_activity(String sf) {
        json_t = sf;
        try {
            jsonObject_forum = new JSONObject(sf);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        list_forum = getQuestList(jsonObject_forum);
        mam = new MyAdapterJsonList(MainActivity.this, list_forum);//
        lv_forum.setAdapter(mam);
        lv_forum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                try {
                    JSONObject jsonObject = new JSONObject(json_t);
                    JSONObject j = new JSONObject(String.valueOf(jsonObject.getJSONObject("mymess")));
                    in.set_idt(j.getJSONArray("array").getJSONObject(position).getInt("idt"));
                    in.set_id_usr(j.getJSONArray("array").getJSONObject(position).getInt("idusr"));
                    // in.set_id_jur(j.getJSONArray("array").getJSONObject(position).getString("idj"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent inte = new Intent(MainActivity.this, Asked_forum.class);
                //inte.putExtra("id", in.get_idt());
                startActivity(inte);
            }
        });
        //lv_forum.setSelection(in_position);
    }

    public void stopProgressBar(){
        main_progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    public boolean bn = true;
    public void potock(boolean b){
        bn = b;
        Runnable runnable = new Runnable() {
            public void run() {
                // Переносим сюда старый код
                long endTime = System.currentTimeMillis()
                        + 15* 1000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            Log.d("qwerty", "поток майн");
                            if(in.getOnemay()){
                                main_progressBar.setVisibility(ProgressBar.VISIBLE);
                                if(bool_start_potoc){
                                    bool_start_potoc = false;
                                    new AsyncTaskMain().execute();
                                }

                            }
                            if(bn){wait(endTime - System.currentTimeMillis());
                                endTime = System.currentTimeMillis() + 2 * 1000;}
                            else{
                                wait(endTime - System.currentTimeMillis());
                                endTime = System.currentTimeMillis()-10;
                            }
                        } catch (Exception e) {Log.d("qwerty", "ошибка потока");
                        }
                    }
                }
                Log.d("qwerty", "theard");
            }
        };
        thread = new Thread(runnable);
        thread.start();
    }

    public void OneStart(){
        if(sPref.getBoolean("one_start", true)){
            Log.d("qwerty", "вошли в превый раз " );
            SharedPreferences.Editor ed = sPref.edit();
            ed.putBoolean("one_start", false);
            ed.putInt("pref_sh", 16);
            ed.putBoolean("pref_setting_push_1", true);
            ed.putBoolean("pref_setting_push_2", true);
            ed.putBoolean("pref_setting_push_3", false);
            ed.commit();

            Log.d("qwerty", "размер шрифта "+ sPref.getInt("pref_sh", 16));
            in.set_font_1(sPref.getInt("pref_sh", 16));
            in.set_font_2(sPref.getInt("pref_sh", 16)-3);
            in.set_font_3(sPref.getInt("pref_sh", 16)-6);

        } else {
            Log.d("qwerty", "размер шрифта второй " + sPref.getInt("pref_sh", 0));
            in.set_font_1(sPref.getInt("pref_sh", 0));
            in.set_font_2(sPref.getInt("pref_sh", 0)-3);
            in.set_font_3(sPref.getInt("pref_sh", 0)-6);

        }
    }

    public void onClick (View v){
        switch (v.getId()){
            case R.id.main_but_menu:
                Intent intent1 = new Intent(MainActivity.this, Sidebar.class);
                startActivity(intent1);
                break;
        }
    }

    public List<JSONObject> getQuestList(JSONObject jsonObjectList) {
        List listMyMess = new ArrayList<>();
        List listMyMessN = new ArrayList<>();
        List listMyMessSort = new ArrayList<>();

        try {
            //json_jur = getString(R.string.mess_json);
            JSONObject results = jsonObjectList.getJSONObject("mymess");

            jsonArray_forum = results.getJSONArray("array");

            for (int i = 0; i < jsonArray_forum.length(); i++) {
                JSONObject arrayElement = jsonArray_forum.getJSONObject(i);
                listMyMess.add(arrayElement);
            }

        } catch (JSONException e) {
            // TODO Auto-generatedcatchblock
            e.printStackTrace();
        }
        lv_forum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {

                Intent inte = new Intent(MainActivity.this, Asked_user.class);
                startActivity(inte);
            }
        });
        return listMyMess;
    }

    class MyAdapterJsonList extends ArrayAdapter {

        private List<JSONObject> list = null;
        private Activity context;

        public String[] arrayTheme = getResources().getStringArray(R.array.ArrayTipQuest);

        public MyAdapterJsonList(Activity context, List<JSONObject> list) {
            super(context, R.layout.forum_item, list);
            this.context = context;
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.forum_item, parent, false);
            TextView tv_forum_theme = (TextView) rowView.findViewById(R.id.forum_tv_theme);
            tv_forum_theme.setTextSize(in.get_font_2());
            TextView tv_forum_quest = (TextView) rowView.findViewById(R.id.forum_tv_quest);
            tv_forum_quest.setTextSize(in.get_font_1());
            TextView tv_forum_kol_otv = (TextView) rowView.findViewById(R.id.forum_tv_kol_otv);
            TextView tv_forum_quest_date = (TextView) rowView.findViewById(R.id.forum_tv_quest_date);
            tv_forum_quest_date .setTextSize(in.get_font_3());
            TextView tv_forum_quest_nik = (TextView) rowView.findViewById(R.id.forum_tv_quest_nik);
            tv_forum_quest_nik.setTextSize(in.get_font_3());

            if(position%2 == 0){
                rowView.setBackgroundColor(Color.argb(15, 255, 255, 255));
            }else {
                rowView.setBackgroundColor(Color.argb(45,1,1,1));
            }

            try {
                String s = arrayTheme[Integer.parseInt(list.get(position).getString("theme"))];
                tv_forum_theme.setText(s);
                tv_forum_quest.setText(list.get(position).getString("text"));



tv_forum_quest_date.setText(list.get(position).getString("date"));
        tv_forum_kol_otv.setText(list.get(position).getString("count"));
        if(Integer.parseInt(list.get(position).getString("count"))==0)
        {
        tv_forum_kol_otv.setBackgroundResource(R.color.count2);
        }
        else{
        tv_forum_kol_otv.setBackgroundResource(R.color.count1);
        }
        tv_forum_quest_nik.setText(list.get(position).getString("name"));

        } catch (JSONException e) {
        // TODO Auto-generatedcatchblock
        e.printStackTrace();
        }

        return rowView;
        }
        }

class AsyncTaskT extends AsyncTask<Void, Integer, String> {
    String url = "";
    @Override
    protected String doInBackground(Void... params) {
        url = "http://"+in.get_url()+"/123.gettheme";//URL_START;
        Log.d(DEBUG_TAG, "j main Activity- " + String.valueOf(json_str));

        return ServerSendData.sendRegData(url, json_str);
    }

    protected void onPostExecute(String result) {
        json_t = result;
        start_activity(result);
        //potock(true);
    }
}
public static class ServerSendData {
    //public static IN iny = new IN();
    public static String sendRegData(String urls, String json) {

        String result =  null;
        try {
            java.net.URL url = new URL(urls);//new AdIn().getURL());//

            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;

            httpConnection.setDoOutput(true);

            httpConnection.setChunkedStreamingMode(0);
            OutputStream out = new BufferedOutputStream(httpConnection.getOutputStream());
            // writeStream(out);
            out.write(json.getBytes());

            out.flush();
            out.close();

            InputStream in = new BufferedInputStream(httpConnection.getInputStream());

            int responseCode = 0;
            responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = httpConnection.getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(in));

                result = r.readLine();
                Log.d("res1 main", "" + result + "");

            } else {
            }
        } catch (MalformedURLException e) {
        } catch (IOException e1) {
        }

        return result;
    }
}

//potock---------------------------------------------------------------------
class AsyncTaskMain extends AsyncTask<Void, Integer, String> {
    // С„РѕРЅРѕРІР°СЏ СЂР°Р±РѕС‚Р°
    String url = "";
    @Override
    protected String doInBackground(Void... params) {

        url = "http://"+in.get_url()+"/123.gettheme";//URL_START;
        Log.d("qwerty", "что отправил ААААА?? "+json_str);
        return ServerSendMain.sendRegMain(url, json_str);
    }

    // РІС‹РїРѕР»РЅСЏРµС‚СЃСЏ РїРѕСЃР»Рµ doInBackground, РёРјРµРµС‚ РґРѕСЃС‚СѓРї Рє UI
    protected void onPostExecute(String result) {
        Log.d("qwerty", "onPostExecute " + result);
        bool_start_potoc = true;
        if (result != null) {
            try {
                JSONObject js = new JSONObject(result);
                if (js.getString("status").equals("OK")) {
                    start_activity(result);
                    stopProgressBar();
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}
public static class ServerSendMain {

    public static String sendRegMain(String urls, String json) {

        String result =  null;
        try {
            java.net.URL url = new URL(urls);//new AdIn().getURL());//
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;

            httpConnection.setDoOutput(true);
            httpConnection.setChunkedStreamingMode(0);
            OutputStream out = new BufferedOutputStream(httpConnection.getOutputStream());
            out.write(json.getBytes());
            out.flush();
            out.close();

            InputStream in = new BufferedInputStream(httpConnection.getInputStream());
            int responseCode = 0;
            responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = httpConnection.getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                result = r.readLine();
                Log.d("res1 asked m", "" + result + "");
            } else {
            }
        } catch (MalformedURLException e) {
        } catch (IOException e1) {
        }
        return result;
    }
}
    //potock_____________________________________________________________________

    public float xX = 0;
    public float xNew = 0;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case (MotionEvent.ACTION_UP):
                if(xNew > 300){
                    Intent intent = new Intent(MainActivity.this, Sidebar.class);
                    startActivity(intent);
                }
                Log.d("qwerty", "ACTION_UP");
                break;
            case (MotionEvent.ACTION_DOWN):
                xX = event.getX();
                Log.d("qwerty", "ACTION_DOWN xX "+xX);
                break;
            case (MotionEvent.ACTION_MOVE):
                Log.d("qwerty", "ACTION_MOVE ");
                int historySize = event.getHistorySize();
                for (int i = 0; i <historySize; i++) {
                    float x = event.getHistoricalX(i);
                    processMovement(x);
                }
                float x = event.getX();
                processMovement(x);
        }
        return false;
    }
    private void processMovement(float _x) {
        // Todo: Обработка движения.
        xNew = xX -_x;
        Log.d("qwerty", "xNew " + xNew);
    }
}*/