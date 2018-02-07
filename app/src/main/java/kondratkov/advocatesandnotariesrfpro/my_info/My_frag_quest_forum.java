package kondratkov.advocatesandnotariesrfpro.my_info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.List;

import kondratkov.advocatesandnotariesrfpro.Asked_forum;
import kondratkov.advocatesandnotariesrfpro.Asked_user;
import kondratkov.advocatesandnotariesrfpro.IN;
import kondratkov.advocatesandnotariesrfpro.R;
import kondratkov.advocatesandnotariesrfpro.api_classes.ClientQuestion;
import kondratkov.advocatesandnotariesrfpro.data_theme.AllQuestions;
import kondratkov.advocatesandnotariesrfpro.data_theme.AllQuestions_forum;
import kondratkov.advocatesandnotariesrfpro.data_theme.theme_data;
import kondratkov.advocatesandnotariesrfpro.data_theme.theme_data_forum;
public class My_frag_quest_forum extends Fragment {

    public ListView lv_f = null;
    public Spinner spinner_f = null;
    public IN in;
    public int sort = 0;

    public theme_data_forum t_db;
    private List<AllQuestions_forum> quest_list;
    public String date = "-1";
    public Context context_view;
    public int code;
    public ClientQuestion[] msArrayclientQuestion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_quest_frag_forum, container, false);

        in = new IN();

        in.set_place(1);
        in.set_Tip(false);
        context_view = in.get_context();

        lv_f = (ListView)view.findViewById(R.id.my_quest_frag_listView_f);

        lv_f.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                in.set_idt(msArrayclientQuestion[(int) id].Id);
                in.set_quest(1);
                in.set_place(1);
                in.set_Tip(false);
                Intent inte = new Intent(in.get_context(), Asked_forum.class);
                //inte.putExtra("id", in.get_idt());
                startActivity(inte);
            }
        });

        spinner_f = (Spinner)view.findViewById(R.id.my_quest_frag_spinner_f);
        onSpinnerS();

        return view;
    }

    public void onStart(){
        super.onStart();

        new UrlConnectionTask().execute();
    }

    public void onSpinnerS() {
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(in.get_context(), R.array.my_quest_array_sorting, R.layout.my_quest_item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_f.setAdapter(adapter);
        //docTip = spinner_sorting.getSelectedItemId();
        spinner_f.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switch (pos) {
                    case 0:
                        sort = 0;
                        //MyAdapterList mam = new MyAdapterList(in.get_activity(), msArrayclientQuestion);
                        //lv_f.setAdapter(mam);
                        break;
                    case 1:
                        sort = 1;
                        // MyAdapterList mam1 = new MyAdapterList(in.get_activity(), msArrayclientQuestion);
                        //lv_f.setAdapter(mam1);
                        break;
                    case 2:
                        sort = 2;
                        // MyAdapterList mam2 = new MyAdapterList(in.get_activity(), msArrayclientQuestion);
                        //lv_f.setAdapter(mam2);
                        break;
                }
            }
        });
    }

    public void start_activity(){
        int now_int = 0;
        for(int i = 0; i<msArrayclientQuestion.length; i++){
            if(msArrayclientQuestion[i].JuristId==0) {
                now_int++;
            }
        }
        ClientQuestion[] clientQuestion_now = new ClientQuestion[now_int];
        int u=0;
        for(int i = 0; i<msArrayclientQuestion.length; i++){
            if(msArrayclientQuestion[i].JuristId==0) {
                int d = now_int-1;
                int df = d-u;
                clientQuestion_now[df] = msArrayclientQuestion[i];
                u++;
            }
        }

        MyAdapterList mam = new MyAdapterList(in.get_activity(), clientQuestion_now);
        lv_f.setAdapter(mam);
    }

    class MyAdapterList extends ArrayAdapter {

        private ClientQuestion[] clientQuestion;
        private Activity context;

        //public String[] arrayTheme = getResources().getStringArray(R.array.ArraySpecialization);

        public MyAdapterList(Activity context, ClientQuestion[] clientQuestions) {
            super(context, R.layout.my_quest_item_list, clientQuestions);
            this.context = context;
            this.clientQuestion = clientQuestions;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.my_quest_item_list, parent, false);

            TextView tv_them = (TextView) rowView.findViewById(R.id.my_ans_item_tv_them);
            tv_them.setTextSize(in.get_font_2());
            TextView tv_text = (TextView) rowView.findViewById(R.id.my_ans_item_tv_text);
            tv_text.setTextSize(in.get_font_1());
            TextView tv_date = (TextView) rowView.findViewById(R.id.my_ans_item_tv_date);
            tv_date.setTextSize(in.get_font_3());
            TextView tv_nik = (TextView) rowView.findViewById(R.id.my_ans_item_tv_nik);
            tv_nik.setTextSize(in.get_font_2());
            TextView tv_read = (TextView) rowView.findViewById(R.id.my_ans_item_tv_read);
            tv_read.setTextSize(in.get_font_2());


            //tv_text.setText(list.get(position).get_text());
                /*CharSequence dateJSON = DateFormat.format("dd.MM.yyyy", Long.parseLong(list.get(position).getString("date")));
                tv_date.setText(String.valueOf(dateJSON));*/
            tv_them.setText(clientQuestion[position].Header);
            tv_text.setText(clientQuestion[position].Body);
            try{
                tv_date.setText(in.dateDisplay(clientQuestion[position].Date));
            }catch (Exception e){}
            //String("text"));
                /*CharSequence dateJSON = DateFormat.format("dd.MM.yyyy", Long.parseLong(list.get(position).getString("date")));
                tv_date.setText(String.valueOf(dateJSON));*/
            /*if(list.get(position).get_date().length()>15) {
                tv_date.setText(list.get(position).get_date().substring(0, 5) + " " + list.get(position).get_date().substring(11, 16));//String("date"));
            }
            else{
                tv_date.setText(list.get(position).get_date());
            }*/
            //tv_nik.setText(list.get(position).get_date());//"от "+list.get(position).get_name());//String("name"));

            if (clientQuestion[position].IsNotReadedAnswers > 0) {
                //tv_read.setBackgroundResource(R.color.read0);
                tv_read.setTextColor(getResources().getColor(R.color.read0));
                tv_nik.setTextColor(getResources().getColor(R.color.read0));
                tv_read.setText("новый");
            }
            /*else if(list.get(position).get_read()==0||list.get(position).get_read()==1||list.get(position).get_read()==4){
                //tv_read.setBackgroundResource(R.color.read1);
                tv_read.setTextColor(getResources().getColor(R.color.read1));
                tv_nik.setTextColor(getResources().getColor(R.color.read1));
                tv_read.setText("неотв.");
            }*/
            else {
                //tv_read.setBackgroundResource(R.color.read2);
                tv_read.setTextColor(getResources().getColor(R.color.read2));
                tv_nik.setTextColor(getResources().getColor(R.color.read2));
                tv_read.setText("просм.");
            }
            return rowView;
        }
    }
    class UrlConnectionTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String result = "";

            OkHttpClient client = new OkHttpClient();

            MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");

            //RequestBody formBody = RequestBody.create(JSON, json_signup);

            String s =in.get_token_type()+" "+in.get_token();
            String s1 ="http://"+in.get_url()+"/ClientQuestions/GetClientQuestionsWithMyAnswers" ;

            Request request = new Request.Builder()
                    .header("Authorization", in.get_token_type()+" "+in.get_token())
                    .url("http://"+in.get_url()+"/ClientQuestions/GetClientQuestionsWithMyAnswers")
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

            Gson gson = new Gson();
            if(result!=null) {
                msArrayclientQuestion = gson.fromJson(result, ClientQuestion[].class);
                //AddList();
                start_activity();
            }
            super.onPostExecute(result);
        }
    }
}
