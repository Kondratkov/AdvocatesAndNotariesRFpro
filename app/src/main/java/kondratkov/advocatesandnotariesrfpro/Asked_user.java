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
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kondratkov.advocatesandnotariesrfpro.api_classes.ClientQuestionAndComment;
import kondratkov.advocatesandnotariesrfpro.api_classes.Comment;
import kondratkov.advocatesandnotariesrfpro.data_theme.AllMess;
import kondratkov.advocatesandnotariesrfpro.data_theme.mess_data;

public class Asked_user extends Activity {

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
    public boolean startPositionList = true;
    public int numberColomn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asked_user);

        in = new IN();
        URL_START   = "http://"+in.get_url()+"/123.sendmes";
        URL_NEWMESS = "http://"+in.get_url()+"/123.sendmes";

        etTextQues = (EditText)findViewById(R.id.ask_et_mess);
        lv_mess = (ListView)findViewById(R.id.ask_listview_mess);
        arrayList = new ArrayList<Comment>();

        ask_tv_forum = (TextView)findViewById(R.id.ask_tv_forum);
        ask_tv_nik = (TextView)findViewById(R.id.ask_tv_nik);

        ask_tv_forum.setText("частный");
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
        mam = new MyAdapterMess(this, arrayList);

        if(bool_ulr_newmess == false) {
            int_my_mess = 0;
            lv_mess.setAdapter(mam);

        }
        else {
            int_my_mess = 1;
            lv_mess.setAdapter(mam);
            //etTextQues.setText("");
        }
        if(selection_list > arrayList.size()-2){
            lv_mess.setSelection(arrayList.size());
        }else{
            lv_mess.setSelectionFromTop(selection_list, topOffset);
        }

        if(startPositionList){
            startPositionList = false;
            numberColomn = arrayList.size();
            topOffset = arrayList.size()-1;
            lv_mess.setSelection(arrayList.size());
        }else if(numberColomn!=arrayList.size()){
            numberColomn = arrayList.size();
            topOffset = arrayList.size();
            lv_mess.setSelection(arrayList.size());
        }else{}
    }

    public void onClickMess(View v){
        switch (v.getId()){
            case R.id.ask_but_cancel:
                Asked_user.this.finish();
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

        private ArrayList<Comment> comments1;
        private Activity context;


        public MyAdapterMess(Activity context, ArrayList<Comment>comments) {
            super(context, R.layout.asked_item_user, comments);
            this.context = context;
            this.comments1= comments;

            Comment comment = new Comment();
            comment.AccountType = Comment.AccountTypes.Client;
            comment.AccountId = in.get_idt();
            comment.Date = MyApplication.getInstance().getClientQuestion().Date;
            comment.Message = MyApplication.getInstance().getClientQuestion().Body;

            try{
                comment.From = MyApplication.getInstance().getClientQuestion().Account.UserName;
            }catch (Exception e){}

            comments1.add(0, comment);
            //mas_view = new int[comments1.size()];
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = null;

            if (comments1.get(position).AccountType == Comment.AccountTypes.Client) {
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

                    if(in.getName_user().equals("")){
                        in.setName_user(comments1.get(position).From);
                    }

                tv_mess_date.setText(in.dateDisplay(comments1.get(position).Date));
                tv_mess.setText(comments1.get(position).Message);
                tv_nik_user_mess.setText(in.getName_user());
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
                tv_surmname_mess_jur.setText(comments1.get(position).From);
                tv_mess_jur.setText(comments1.get(position).Message);
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

            Request request = new Request.Builder()
                    .header("Authorization", in.get_token_type()+" "+in.get_token())
                    .url("http://"+in.get_url()+"/ClientQuestions/GetNewComment/"+in.get_idt())
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

                msArraycomments = gson.fromJson(result, Comment[].class);
                arrayList = new ArrayList<Comment>();
                for(int i = 0; i<msArraycomments.length; i++){
                    arrayList.add(msArraycomments[i]);
                }
                in.kost_push = true;
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
            if(in.get_quest() == 0){
                request = new Request.Builder()
                        .header("Authorization", in.get_token_type()+" "+in.get_token())
                        .url("http://"+in.get_url()+"/ClientQuestions/CreateAnswer/"+in.get_idt())
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, params[0]))
                        .build();
            }else{
                request= new Request.Builder()
                        .header("Authorization", in.get_token_type()+" "+in.get_token())
                        .url("http://"+in.get_url()+"/ClientQuestions/AddCommentOne/"+in.get_idt())
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, params[0]))
                        .build();
            }

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

                new UrlConnectionTask1().execute();
            }
            super.onPostExecute(result);
        }
    }

}


