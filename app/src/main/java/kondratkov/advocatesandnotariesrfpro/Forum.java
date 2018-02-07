package kondratkov.advocatesandnotariesrfpro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import kondratkov.advocatesandnotariesrfpro.api_classes.ClientQuestion;
import kondratkov.advocatesandnotariesrfpro.my_info.My_frag_quest_chat;

public class Forum extends Activity {
    public Spinner spinner_sorting;
    String json_quest =
            "{\"mymess\":{\"array\":[" +
                    "{\"idt\":\"13\",\"reply\":\"1\",\"theme\":\"3\",\"text\":\" вот и ладно\",\"date\":\"1432452584532\"}," +
                    "{\"idt\":\"12\",\"reply\":\"0\",\"theme\":\"1\",\"text\":\"Отили нет это нужно или не нужно\",\"date\":\"1451452684532\"}," +
                    "{\"idt\":\"14\",\"reply\":\"2\",\"theme\":\"3\",\"text\":\"Отвт выадлжовыа лоаывлдо ывллывсоалвоал о ооапн пн неангаа нгенгеа нгеанге ае аап апроаг аеап аоаге агеа е ае аеа ное анаеаоаноеане ае аопеапоаоеаео ае аноеаоеапоаро аоне аноеа аапеа е ае аеае ае ае ае аеаоаоеаоа о а\",\"date\":\"1451552784532\"}," +
                    "{\"idt\":\"15\",\"reply\":\"2\",\"theme\":\"1\",\"text\":\"Ну ладно, а что если?\",\"date\":\"1423456884532\"}," +
                    "{\"idt\":\"16\",\"reply\":\"0\",\"theme\":\"3\",\"text\":\"выафыв Ну ладно, а что если?\",\"date\":\"1465468684532\"}," +
                    "{\"id\":\"17\",\"reply\":\"4\",\"theme\":\"1\",\"text\":\"выаНу ладно, а что fdfd fdfdf df df df df df если?\",\"date\":\"1423468884532\"}," +
                    "{\"idt\":\"18\",\"reply\":\"6\",\"theme\":\"1\",\"text\":\"Нртмиявпу ладно, а что если?\",\"date\":\"1467846884532\"}," +
                    "{\"idt\":\"19\",\"reply\":\"5\",\"theme\":\"5\",\"text\":\"Нуукчспвап ладно, а что если?\",\"date\":\"1452446884532\"}," +
                    "{\"idt\":\"20\",\"reply\":\"6\",\"theme\":\"1\",\"text\":\"Нуукаич ладно, а что если?\",\"date\":\"1478946884532\"}," +
                    "{\"idt\":\"21\",\"reply\":\"7\",\"theme\":\"1\",\"text\":\"Нувапп ладно, а что если?\",\"date\":\"1439766884532\"}," +
                    "{\"idt\":\"22\",\"reply\":\"1\",\"theme\":\"0\",\"text\":\"Нчыапу ладно, а что если?\",\"date\":\"1562446884532\"}," +
                    "{\"idt\":\"23\",\"reply\":\"0\",\"theme\":\"1\",\"text\":\"Ныпу имладно, а что если?\",\"date\":\"1879746884532\"}," +
                    "{\"idt\":\"24\",\"reply\":\"0\",\"theme\":\"1\",\"text\":\"Нываппу ладно, а что если?\",\"date\":\"1346446884532\"}," +
                    "{\"idt\":\"25\",\"reply\":\"1\",\"theme\":\"2\",\"text\":\"Нывпу ладно, а что если?\",\"date\":\"1242446884532\"}," +
                    "{\"idt\":\"26\",\"reply\":\"2\",\"theme\":\"1\",\"text\":\"Да нет, что вы.  давоыда олдыоа ывадло оыва лдодоыва шо\",\"date\":\"1434552984532\"}]}}";




    private long docTip =1;

    public int sort = 0 ;
    public int sort_spin = 0;


    public FrameLayout forum_frameProg;
    public ProgressBar forum_progressBar;

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

    MyAdapterJsonList mam = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum);

        spinner_sorting = (Spinner)findViewById(R.id.spinner_sort_forum);
        lv_forum = (ListView)findViewById(R.id.lv_forum);
        in = new IN();
        //onSpinnerS();



        //list_forum = getQuestList(jsonObject_forum);
        //MyAdapterJsonList mam = new MyAdapterJsonList(this, list_forum);//getJurList(jsonObjectJurList));
        //lv_forum.setAdapter(mam);

        //lv_forum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //@Override
        //public void onItemClick(AdapterView<?> parent, View v, int position,
        //  long id) {


        //Forum.this.finish();
        // }
        //});
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(in.getOnemay()==false){
            Forum.this.finish();
        }
        in.setChoice_of_menus(4);

        forum_frameProg = (FrameLayout)findViewById(R.id.forum_frameProg);
        forum_progressBar = (ProgressBar)findViewById(R.id.forum_progressBar);

        forum_frameProg.setBackgroundResource(R.color.frameOn);
        forum_frameProg.setClickable(true);
        forum_progressBar.setVisibility(ProgressBar.VISIBLE);

        potock(true);
        //new AsyncTaskMess().execute();
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
    }

    public void start_activity() {
        mam = new MyAdapterJsonList(Forum.this, msArrayclientQuestion);//

        lv_forum.setAdapter(mam);
        lv_forum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {

                in.set_idt(msArrayclientQuestion[position].Id);
                Intent inte = new Intent(in.get_activity(), Asked_forum.class);
                //inte.putExtra("id", in.get_idt());
                startActivity(inte);
            }
        });


        if(selection_list > msArrayclientQuestion.length-2){
            lv_forum.setSelection( msArrayclientQuestion.length);
        }else{
            lv_forum.setSelection(selection_list);
        }

        stopProgressBar();
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

    public void stopProgressBar(){
        forum_frameProg.setBackgroundResource(R.color.frameOff);
        forum_frameProg.setClickable(false);
        forum_progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    public void onClickForum(View v) {
        switch (v.getId()) {
            case R.id.but_cancel_forum:
                Forum.this.finish();
                break;
            case  R.id.but_menu_forum:
                Intent intent134 = new Intent(Forum.this, Sidebar.class);//DocCreation.class);
                startActivity(intent134);
                break;
        }
    }

    class MyAdapterJsonList extends ArrayAdapter {

        private ClientQuestion[]clientQuestions = null;
        private Activity context;

        public String[] arrayTheme = getResources().getStringArray(R.array.ArrayTipQuest);

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
                rowView.setBackgroundColor(Color.argb(15, 255, 255, 255));
            }else {
                rowView.setBackgroundColor(Color.argb(45,1,1,1));
            }

            //String s = arrayTheme[Integer.parseInt(list.get(position).getString("theme"))];
            tv_forum_theme.setText(clientQuestions[position].Header);
            tv_forum_quest.setText(clientQuestions[position].Body);

                /*CharSequence dateJSON = DateFormat.format("HH:mm dd.MM.yyyy", Long.decode(list.get(position).getString("date")));
                tv_forum_quest_date.setText(String.valueOf(dateJSON));*/

            //tv_forum_quest_date.setText(list.get(position).getString("date"));
            tv_forum_kol_otv.setText(String.valueOf(clientQuestions[position].AnswersCount));
            if(clientQuestions[position].AnswersCount==0)
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

                try{
                    ClientQuestion[] clientQuestions_get= gson.fromJson(result, ClientQuestion[].class);

                    int size = clientQuestions_get.length;
                    msArrayclientQuestion = new ClientQuestion[size];
                    for(int i=0; i<size; i++){
                        msArrayclientQuestion[size-1-i] = clientQuestions_get[i];
                    }
                }catch (Exception e){}
                //AddList();
                start_activity();
            }
            super.onPostExecute(result);
        }
    }


}





