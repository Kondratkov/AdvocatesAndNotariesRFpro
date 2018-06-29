package kondratkov.advocatesandnotariesrfpro.my_info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

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

import kondratkov.advocatesandnotariesrfpro.Asked_forum;
import kondratkov.advocatesandnotariesrfpro.Asked_user;
import kondratkov.advocatesandnotariesrfpro.IN;
import kondratkov.advocatesandnotariesrfpro.R;
import kondratkov.advocatesandnotariesrfpro.Service_mess;
import kondratkov.advocatesandnotariesrfpro.api_classes.ClientQuestion;
import kondratkov.advocatesandnotariesrfpro.input.LogIN;

public class My_frag_quest_chat extends Fragment {

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

    public ClientQuestion[] msArrayclientQuestion;

    public Thread thread;

    public SharedPreferences sPref;

    public boolean bool_start_potoc = true;
    public boolean bu = true;

    public int code;
    public int selection_list=100;
    public int topOffset=0;

    MyAdapterJsonList mam = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_frag_quest_chat, container, false);

        in = new IN();
        main_progressBar= (ProgressBar)view.findViewById(R.id.progressBar_chat);
        //sPref = PreferenceManager.getDefaultSharedPreferences(this);
        lv_forum = (ListView)view.findViewById(R.id.my_quest_frag_chat);
        in.set_Tip(false);
        in.set_place(1);
        try{
            sPref = PreferenceManager.getDefaultSharedPreferences(in.get_activity());
        }catch (Exception e){

        }

        OneStart();
        on_Start();
        return view;
    }


    public void on_Start() {
        super.onStart();

        in.setChoice_of_menus(0);
            in = new IN();


            potock(true);
    }

    public void start_activity() {
        mam = new MyAdapterJsonList(in.get_activity(), msArrayclientQuestion);//

        lv_forum.setAdapter(mam);
        lv_forum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {

                in.set_idt(msArrayclientQuestion[position].Id);
                in.set_clientQuestion(msArrayclientQuestion[position]);
                in.setName_user(msArrayclientQuestion[position].Account.Fio);
                in.set_quest(0);
                in.set_Tip(false);
                in.set_place(1);
                Intent inte = new Intent(in.get_activity(), Asked_forum.class);
                //inte.putExtra("id", in.get_idt());
                startActivity(inte);
            }
        });


        if(selection_list > msArrayclientQuestion.length-2){
            lv_forum.setSelection(msArrayclientQuestion.length);
        }else{
            lv_forum.setSelectionFromTop(selection_list, topOffset);
        }

        stopProgressBar();
    }

    public void stopProgressBar(){
        main_progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    public boolean bn = true;
    public void potock(boolean b){
        bn = b;
        bu = false;
        Runnable runnable = new Runnable() {
            public void run() {
                // Переносим сюда старый код
                long endTime = System.currentTimeMillis()
                        + 1* 1000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            //new AsyncTaskM().execute();
                            Log.d("122","1q2");
                            selection_list = lv_forum.getFirstVisiblePosition();
                            View v = lv_forum.getChildAt(0);
                            topOffset = (v == null) ? 0 : v.getTop();

                            new UrlConnectionTaskPotock1().execute();

                            if(bn){wait(endTime - System.currentTimeMillis());
                                endTime = System.currentTimeMillis() + 6 * 1000;}
                            else{
                                wait(endTime - System.currentTimeMillis());
                                endTime = System.currentTimeMillis()-10;
                            }
                        } catch (Exception e) {Log.d("qwerty", "поток asked остановлен");
                        }
                    }
                }
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

    class MyAdapterJsonList extends ArrayAdapter {

        private ClientQuestion[]clientQuestions = null;
        private Activity context;


        public MyAdapterJsonList(Activity context, ClientQuestion[]clientQuestions1) {
            super(context, R.layout.forum_item, clientQuestions1);
            this.context = context;
            this.clientQuestions = clientQuestions1;
            stopProgressBar();
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
                rowView.setBackgroundColor(Color.argb(5, 255, 255, 255));
            }else {
                rowView.setBackgroundColor(Color.argb(45,1,1,1));
            }

            //String d = clientQuestions[position].Account.UserName;
            //String dd = clientQuestions[position].Account.Fio;
            tv_forum_quest_nik.setText(clientQuestions[position].Account.Fio);

            try{
                tv_forum_quest_date.setText(in.dateDisplay(clientQuestions[position].Date));
            }catch (Exception e){}


            //String s = arrayTheme[Integer.parseInt(list.get(position).getString("theme"))];
            tv_forum_theme.setText(clientQuestions[position].Header);
            tv_forum_quest.setText(clientQuestions[position].Body);

                /*CharSequence dateJSON = DateFormat.format("HH:mm dd.MM.yyyy", Long.decode(list.get(position).getString("date")));
                tv_forum_quest_date.setText(String.valueOf(dateJSON));*/

            //tv_forum_quest_date.setText(list.get(position).getString("date"));
            tv_forum_kol_otv.setText(String.valueOf(clientQuestions[position].AllAnswersCount));
            if(clientQuestions[position].AllAnswersCount==0)
            {
                tv_forum_kol_otv.setBackgroundResource(R.color.count2);
            }
            else{
                tv_forum_kol_otv.setBackgroundResource(R.color.count1);
            }
            //tv_forum_quest_nik.setText(clientQuestions[position].);
            return rowView;
        }
    }


    //potock---------------------------------------------------------------------
    class UrlConnectionTaskPotock1 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String result = "";

            OkHttpClient client = new OkHttpClient();

            MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");

            //RequestBody formBody = RequestBody.create(JSON, json_signup);

            String s ="http://"+in.get_url()+"/ClientQuestions/GetClientQuestions";
            String s2 = in.get_token_type()+" "+in.get_token();

            Request request = new Request.Builder()
                    .header("Authorization", in.get_token_type()+" "+in.get_token())
                    .url("http://"+in.get_url()+"/ClientQuestions/GetClientQuestions")
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
            Gson gson = new Gson();
            if(result!=null && 200<=code && code<300) {
                try {
                    ClientQuestion[] clientQuestions_get = gson.fromJson(result, ClientQuestion[].class);
                    int size = clientQuestions_get.length;
                    msArrayclientQuestion = new ClientQuestion[size];
                    for (int i = 0; i < size; i++) {
                        msArrayclientQuestion[size - 1 - i] = clientQuestions_get[i];
                    }
                }catch (Exception e){}
                //AddList();
                start_activity();
            }
            super.onPostExecute(result);
        }
    }
}
