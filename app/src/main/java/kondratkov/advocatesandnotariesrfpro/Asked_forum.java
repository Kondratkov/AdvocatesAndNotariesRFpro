package kondratkov.advocatesandnotariesrfpro;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kondratkov.advocatesandnotariesrfpro.api_classes.ClientQuestion;
import kondratkov.advocatesandnotariesrfpro.api_classes.ClientQuestionAndComment;
import kondratkov.advocatesandnotariesrfpro.api_classes.Comment;
import kondratkov.advocatesandnotariesrfpro.api_classes.QuestionAnswer;

public class Asked_forum extends Activity {

    private static final String DEBUG_TAG = "qwerty_forum_as";

    public ListView lv_mess;
    public EditText etTextQues;
    public TextView ask_tv_nik;
    public Button butQues;
    public JSONObject res = null;
    public int [] mas_view;

    public IN in;
    public Calendar date = Calendar.getInstance();
    public String URL_START, URL_NEWMESS, URL;
    public boolean bool_ulr_newmess = true;
    public int int_my_mess = 0;
    public Thread thread;
    TextView ask_tv_forum;
    public int code;

    public boolean bu = true;

    public ClientQuestionAndComment clientQuestions;
    public Comment[] msArraycomments;
    public ArrayList<Comment>arrayList;

    public int selection_list=100;
    public int topOffset=0;

    public Date myDate;

    public ClientQuestion clientQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asked_user);

        in = new IN();
        URL_START   = "http://"+in.get_url()+"/123.sendmes";
        URL_NEWMESS = "http://"+in.get_url()+"/123.sendmes";
        myDate= new Date();

        etTextQues = (EditText)findViewById(R.id.ask_et_mess);
        lv_mess = (ListView)findViewById(R.id.ask_listview_mess);
        arrayList = new ArrayList<Comment>();

        ask_tv_forum = (TextView)findViewById(R.id.ask_tv_forum);
        ask_tv_nik = (TextView)findViewById(R.id.ask_tv_nik);

        ask_tv_forum.setText("чат");
        //in.set_idt(getIntent().getIntExtra("id", 0));
    }

    public boolean bn = true;
    public void potock(boolean b){
        bn = b;
        bu = false;
        Runnable runnable = new Runnable() {
            public void run() {
                // Переносим сюда старый код
                long endTime = System.currentTimeMillis()
                        + 6* 1000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            //new AsyncTaskM().execute();
                            selection_list = lv_mess.getFirstVisiblePosition();
                            View v = lv_mess.getChildAt(0);
                            topOffset = (v == null) ? 0 : v.getTop();

                            new UrlConnectionTask1().execute();

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

    @Override
    protected void onStart() {
        super.onStart();

        if(in.getOnemay()==false){
            this.finish();
        }
        in.setpotok_main(false);
        bu = true;
        int_my_mess = 0;

        bool_ulr_newmess = false;



        potock(true);
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

    public void start_new_list(){
        stopProgressBar();
        MyAdapterMess mam= null;
        int id_jurist=0;


        ArrayList<QuestionAnswer>answers=new ArrayList<QuestionAnswer>();
        QuestionAnswer questionAnswerClient = new QuestionAnswer();
        questionAnswerClient.Body = clientQuestion.Body;
        questionAnswerClient.Date = clientQuestion.Date;
        questionAnswerClient.AccountType = QuestionAnswer.accountType.Client;
        try{
            questionAnswerClient.Account.Fio = clientQuestion.Account.UserName;
        }catch (Exception e){}
        answers.add(questionAnswerClient);

        for(int i=0; i<clientQuestion.Answers.length; i++){
            int iid = in.get_id_jur();
            try{
                id_jurist =clientQuestion.Answers[i].Account.Id;
            }catch (Exception e){id_jurist=0;}


            if(id_jurist==in.get_id_jur()){
                answers.add(clientQuestion.Answers[i]);
            }
        }
        mam = new MyAdapterMess(this, answers);


        if(bool_ulr_newmess == false) {
            int_my_mess = 0;
            lv_mess.setAdapter(mam);

        }
        else {
            int_my_mess = 1;
            lv_mess.setAdapter(mam);
            //etTextQues.setText("");
        }

        lv_mess.setSelectionFromTop(selection_list, topOffset);

    }


    public void onClickMess(View v){
        switch (v.getId()){
            case R.id.ask_but_cancel:
                Asked_forum.this.finish();
                break;
            case R.id.ask_ibut_mess:
                bool_ulr_newmess = true;

                in.set_text(String.valueOf(etTextQues.getText()));
                etTextQues.setText("");



                JSONObject json_st = new JSONObject();
                try {
                    json_st.put("MessageBody", in.get_text());//String.valueOf(in.get_id()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json_str = String.valueOf(json_st);

                new UrlConnectionTaskNewMess().execute(json_str);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etTextQues.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                break;
        }
    }

    public void stopProgressBar(){


    }

class MyAdapterMess extends ArrayAdapter {

    private ArrayList<QuestionAnswer> comments1;
    private Activity context;


    public MyAdapterMess(Activity context, ArrayList<QuestionAnswer>comments) {
        super(context, R.layout.asked_item_user, comments);
        this.context = context;
        this.comments1= comments;
        //mas_view = new int[comments1.size()];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = null;

        if (comments1.get(position).AccountType == QuestionAnswer.accountType.Client) {
            rowView = inflater.inflate(R.layout.asked_item_user, parent, false);

            TextView tv_mess = (TextView) rowView.findViewById(R.id.mess_user_tv);
            TextView tv_mess_date = (TextView) rowView.findViewById(R.id.mess_user_tv_date);
            TextView tv_nik_user_mess = (TextView) rowView.findViewById(R.id.mess_user_tv_nik);
            //ImageView icon_user_mess = (ImageView) rowView.findViewById(R.id.icon_user_mess);

                    /*dateJSON = DateFormat.format("dd.MM", Long.decode(list.get(position).getString("date")));

                    if(Float.parseFloat(String.valueOf(dateISNow))==Float.parseFloat(String.valueOf(dateJSON ))){
                        dateJSON = DateFormat.format("HH:mm", Long.decode(list.get(position).getString("date")));
                        tv_mess_date.setText(String.valueOf(dateJSON));
                    }
                    else{
                        dateJSON = DateFormat.format("dd.MM", Long.decode(list.get(position).getString("date")));
                        tv_mess_date.setText(String.valueOf(dateJSON));
                    }*/
            //tv_mess.setText(comments1.get(position).Account.Fio);
            tv_mess_date.setText(in.dateDisplay(comments1.get(position).Date));
            tv_mess.setText(comments1.get(position).Body);
            try{
                if(in.getName_user().equals("")){
                    in.setName_user(comments1.get(position).Account.Fio);
                }
                tv_nik_user_mess.setText(in.getName_user());
            }catch (Exception e){}

            //icon_user_mess.setBackgroundResource(Integer.parseInt(list.get(position).getString("iconuser")));
        } else {
            rowView = inflater.inflate(R.layout.asked_item_jur, parent, false);

            //ImageView icon_mess_jur = (ImageView)rowView.findViewById(R.id.icon_mess_jur);
            TextView tv_surmname_mess_jur = (TextView) rowView.findViewById(R.id.mess_jur_tv_surmname);
            TextView tv_mess_jur = (TextView) rowView.findViewById(R.id.mess_jur_tv);
            TextView tv_mess_jur_date = (TextView) rowView.findViewById(R.id.mess_jur_tv_date);
            Button but_icon = (Button) rowView.findViewById(R.id.mess_jur_but_icon);
            //mas_view[position] = but_icon.getId();

                   /* dateJSON = DateFormat.format("dd.MM", Long.decode(list.get(position).getString("date")));

                    if(Float.parseFloat(String.valueOf(dateISNow))==Float.parseFloat(String.valueOf(dateJSON ))){
                        dateJSON = DateFormat.format("HH:mm", Long.decode(list.get(position).getString("date")));
                        tv_mess_jur_date.setText(String.valueOf(dateJSON));
                    }
                    else{
                        dateJSON = DateFormat.format("dd.MM", Long.decode(list.get(position).getString("date")));
                        tv_mess_jur_date.setText(String.valueOf(dateJSON));
                    }*/

            tv_mess_jur_date.setText(in.dateDisplay(comments1.get(position).Date));
            tv_surmname_mess_jur.setText(in.get_FIO());
            tv_mess_jur.setText(comments1.get(position).Body);
            //icon_mess_jur.setBackgroundResource(Integer.parseInt(list.get(position).getString("iconjur")));

            but_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        return rowView;
    }

}


class UrlConnectionTask1 extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        String result = "";
        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");
        //RequestBody formBody = RequestBody.create(JSON, json_signup);
        Request request = new Request.Builder()
                .header("Authorization", in.get_token_type()+" "+in.get_token())
                .url("http://"+in.get_url()+"/ClientQuestions/GetClientQuestion/"+in.get_idt())
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
        if(result!=null && 200<=code && code<300){
            Gson gson = new Gson();
            int idl = in.get_id_jur();
            clientQuestion = gson.fromJson(result, ClientQuestion.class);

            start_new_list();
        }
        super.onPostExecute(result);
    }
}

    class UrlConnectionTaskNewMess extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String result = "";
            OkHttpClient client = new OkHttpClient();
            MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");

            Request request;

            request = new Request.Builder()
                        .header("Authorization", in.get_token_type()+" "+in.get_token())
                        .url("http://"+in.get_url()+"/ClientQuestions/CreateAnswer/"+in.get_idt())
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, params[0]))
                        .build();


            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                result = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result!=null && 200<=code && code<300){
                in.kost_push = true;
                //Gson gson = new Gson();]
                new UrlConnectionTask1().execute();
                    /*Gson gson = new GsonBuilder().setDateFormat("MM-dd-yy HH:mm").create();
                    Comment comment12 = gson.fromJson(result, Comment.class);
                    arrayList.add(comment12);
                    selection_list = arrayList.size()-2;
                    start_new_list();*/
            }
            super.onPostExecute(result);
        }
    }
}

