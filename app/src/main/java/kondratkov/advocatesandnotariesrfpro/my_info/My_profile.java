package kondratkov.advocatesandnotariesrfpro.my_info;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

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
import kondratkov.advocatesandnotariesrfpro.R;
import kondratkov.advocatesandnotariesrfpro.Sidebar;
import kondratkov.advocatesandnotariesrfpro.account.GetProfileClientJurist;
import kondratkov.advocatesandnotariesrfpro.account.JuristAccount;
import kondratkov.advocatesandnotariesrfpro.api_classes.BaseJuristAccount;

public class My_profile extends Activity {

    public Boolean bool_jur_data = false;
    public LinearLayout lila_prof_jur_data;

    public View lm[] = new View[6];//-ff

    public String [] data_prof_v = null;//getResources().getStringArray(R.array.array_profile);
    public String [] data_profile = new String[5];

    String json_prof_jurile =
            "";

    public CharSequence date_year_today = DateFormat.format("yyyy", System.currentTimeMillis());
    public CharSequence dateJSON = null;
    public int int_year_today = 0;
    public int int_year_json = 0;

    public TextView tv_city, tv_online, tv_fio, tv_rating, tv_phone, tv_email,tv_reply, tv_thanks, tv_adres, tv_stag, tv_kvalif, tv_site, tv_firm, tv_srv, tv_fsar, tv_job, tv_1, tv_2, tv_3;

    public ImageView image_profile_jur_icon;
    //public

    public JSONObject jsonObjectJurProf = null;
    public IN in = null;

    private static final String DEBUG_TAG = "qwerty";
    public  String URL_START ;

    public int code;

    public FrameLayout prof_frameProg;
    public ProgressBar prof_progressBar;

    public RatingBar ratingBar;

    public BaseJuristAccount juristAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_my_profile);
        in = new IN();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(in.getOnemay()==false){
            this.finish();
        }
        in.setChoice_of_menus(1);
        URL_START = "http://"+in.get_url()+"/123.profjur";

        jsonObjectJurProf = new JSONObject();
        try {
            jsonObjectJurProf.put("idj", in.get_id_jur());
            jsonObjectJurProf.put("password", in.get_password_jur());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        json_prof_jurile = String.valueOf(jsonObjectJurProf);
        Log.d("qwerty", "123 - " + String.valueOf(jsonObjectJurProf));

        prof_frameProg = (FrameLayout)findViewById(R.id.prof_frameProg);
        prof_progressBar = (ProgressBar)findViewById(R.id.prof_progressBar);

        image_profile_jur_icon  = (ImageView)findViewById(R.id.prof_jur_image_icon);
        image_profile_jur_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPhotoProfile();
            }
        });


        prof_frameProg.setBackgroundResource(R.color.frameOn);
        prof_frameProg.setClickable(true);
        prof_progressBar.setVisibility(ProgressBar.VISIBLE);
        new UrlConnectionTask().execute();
        //new AsyncTaskProfileJur().execute();
    }

    private void setPhotoProfile(){
        Intent intent = new Intent(My_profile.this, My_photo_redaction.class);
        startActivity(intent);
    }

    public void start_activity(){
        json_prof_jurile ="";

        int_year_today = Integer.parseInt(String.valueOf(date_year_today));
        lila_prof_jur_data = (LinearLayout)findViewById(R.id.jur_prof_lila_data);

        tv_fio    = (TextView)findViewById(R.id.prof_jur_tv_fio);
        tv_rating = (TextView)findViewById(R.id.prof_jur_tv_rating );
        tv_phone  = (TextView)findViewById(R.id.jur_prof_tv_phone );
        tv_email  = (TextView)findViewById(R.id.jur_prof_tv_email );
        tv_reply  = (TextView)findViewById(R.id.jur_prof_tv_reply );
        tv_thanks = (TextView)findViewById(R.id.jur_prof_tv_thanks );
        tv_adres  = (TextView)findViewById(R.id.jur_prof_tv_adres );
        tv_kvalif = (TextView)findViewById(R.id.jur_prof_tv_kvalif);
        tv_stag   = (TextView)findViewById(R.id.jur_prof_tv_stag );
        tv_site   = (TextView)findViewById(R.id.jur_prof_tv_site );
        tv_firm   = (TextView)findViewById(R.id.jur_prof_tv_firm );
        tv_srv    = (TextView)findViewById(R.id.jur_prof_tv_srv );
        tv_fsar   = (TextView)findViewById(R.id.jur_prof_tv_fsar );
        tv_job    = (TextView)findViewById(R.id.jur_prof_tv_job );
        tv_city   = (TextView)findViewById(R.id.prof_jur_tv_city);
        tv_1      = (TextView)findViewById(R.id.jur_prof_tv_1);
        tv_2      = (TextView)findViewById(R.id.jur_prof_tv_2);
        tv_3      = (TextView)findViewById(R.id.jur_prof_tv_3);


        ratingBar = (RatingBar)findViewById(R.id.prof_jur_ratingbar);

        //image_profile_jur_icon= (ImageView)findViewById(R.id.jur_prof_image_icon );

        data_prof_v = getResources().getStringArray(R.array.array_profile_jur);
        tv_fio.setText(juristAccount.Surename+" "+juristAccount.Name+" "+juristAccount.Patronymic);
        tv_rating.setText(String.valueOf(juristAccount.Rating));
        tv_phone.setText(juristAccount.Phone);
        tv_email.setText(juristAccount.Email);
        tv_reply.setText("");
        tv_thanks.setText("");
        try{
            tv_adres.setText(juristAccount.Address.City+" "+juristAccount.Address.Street+" "+juristAccount.Address.StreetNumber+" "+juristAccount.Address.BuildingNumber);
            tv_city.setText(juristAccount.Address.City);
        }catch (Exception e){}
        //
        ratingBar.setRating((float) juristAccount.Rating);
        try{
            tv_firm.setText(juristAccount.JuristOrganisation.OrganisationName);
        }catch (Exception e){}
        try{
            tv_site.setText(juristAccount.Site);
        }catch (Exception e){}
        try{
            tv_1.setText(juristAccount.JudicialArea.Name);
        }catch (Exception e){}
        try{
            tv_2.setText(juristAccount.NumberInApReestr);
        }catch (Exception e){}
        try{
            tv_3.setText("Ученая степень: "+ juristAccount.PhdInfo +"\nВладение иностранными языками: "+ juristAccount.LanguagesInfo +"\nСтатус адвоката присвоен: "+ juristAccount.JuristStatusAssignedBy);
        }catch (Exception e){}

        String sp="";
        try{
            for(int i=0; i<juristAccount.Specializations.length; i++){
                sp = sp+juristAccount.Specializations[i].SectorName+" ";
            }
        }catch (Exception e){}
        tv_kvalif.setText(sp);


        if(juristAccount.CanFastComing){
            tv_srv.setText("да");
        }
        else{
            tv_srv.setText("нет");
        }
        if(juristAccount.WorkInOffDays){
            tv_job.setText("ПН-ВС");
        }
        else{
            tv_job.setText("ПН-ПТ");
        }
        if(juristAccount.IsFsarMember){
            tv_fsar.setText("да");
        }
        else{
            tv_fsar.setText("нет");
        }
        try{tv_stag.setText(in.dateDisplayStag(juristAccount.ExperienceLevel));}
        catch (Exception e){}

        if(juristAccount.ImageUrl!=null){
            Picasso.with(this.getApplicationContext())
                    .load("http://app.mmka.info/"+juristAccount.ImageUrl)
                    .into(image_profile_jur_icon);
        }

        stopProgressBar();
    }

    public void stopProgressBar(){
        prof_frameProg.setBackgroundResource(R.color.frameOff);
        prof_frameProg.setClickable(false);
        prof_progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    public void onClick(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.prof_jur_but_cancel:
                My_profile.this.finish();
                break;

            case R.id.prof_jur_but_finish_prof:
                in.setOnemay(false);
                My_profile.this.finish();
                break;
            case R.id.prof_jur_but_menu:
                in.setChoice_of_menus(1);
                Intent intent2 = new Intent(My_profile.this,Sidebar.class);
                startActivity(intent2);
                break;
            case R.id.prof_jur_ibut_redactor:
                Toast.makeText(My_profile.this,
                        "Для редактирования профиля свяжитесь с модератором в окне 'О нас'",
                        Toast.LENGTH_LONG).show();
                //Intent intent1 = new Intent(My_profile.this,Profile_redactor.class);
                //startActivity(intent1);
                break;
        }
    }

    public void listZ (JSONObject jsonObjectProf) {
        for (int i =0; i<5; i++){
            switch(i){
                case 0:
                    try {
                        data_profile[0] = jsonObjectProf.getString("regnum");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        dateJSON = DateFormat.format("yyyy", Long.decode(jsonObjectProf.getString("stag")));
                        int_year_json = Integer.parseInt(String.valueOf(dateJSON));
                        int_year_json = int_year_today - int_year_json;

                        int t1 = int_year_json % 10;
                        int t2 = int_year_json % 100;
                        if(t1 == 1 && t2 != 11) {data_profile[1] =String.valueOf(int_year_json)+" год";}
                        else if(t1>1 && t1<5 && t2!=12 && t2!=13 && t2!=14) {data_profile[1] =String.valueOf(int_year_json)+" года";}
                        else{data_profile[1] =String.valueOf(int_year_json)+" лет";}

                        //data_profile[1] = String.valueOf(int_year_json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        if(Boolean.parseBoolean(jsonObjectProf.getString("timout"))==true){
                            data_profile[2] = "да";
                        }
                        else{
                            data_profile[2] = "нет";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        if(Boolean.parseBoolean(jsonObjectProf.getString("fsar"))==true){
                            data_profile[3] = "да";
                        }
                        else{
                            data_profile[3] = "нет";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        if(Boolean.parseBoolean(jsonObjectProf.getString("job_weekend"))==true){
                            data_profile[4] = "да";
                        }
                        else{
                            data_profile[4] = "нет";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

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
                juristAccount = gson.fromJson(result, BaseJuristAccount.class);
                start_activity();
            }else{
                Toast.makeText(My_profile.this,
                        "Нет связи с сервером!",
                        Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }
    }
}