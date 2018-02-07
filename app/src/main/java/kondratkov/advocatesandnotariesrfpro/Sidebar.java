package kondratkov.advocatesandnotariesrfpro;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import kondratkov.advocatesandnotariesrfpro.my_info.About_us;
import kondratkov.advocatesandnotariesrfpro.my_info.My_profile;
import kondratkov.advocatesandnotariesrfpro.my_info.My_theme;

public class Sidebar extends Activity implements View.OnTouchListener{
    IN in;
    public TextView tvFSidebar;
    public LinearLayout lilaSidebar;
    public ScrollView s1_close;
    public Button b1_close, button_fin_close;
    public TextView s1, s2, s3, s4, s5;
    public TextView [] tv_mass;


    public LinearLayout lila_side_my_mess, lila_side_my_prof, lila_side_my_odres,
            lila_side_forum; //lila_side_help, lila_side_paiment, lila_side_share;
    public LinearLayout[] ff= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sidebar);

        lilaSidebar = (LinearLayout)findViewById(R.id.lilaSidebar);
        in = new IN();
        tvFSidebar= (TextView)findViewById(R.id.tvFSidebar);

        s1 = (TextView)findViewById(R.id.sidebar_s1) ;
        s2 = (TextView)findViewById(R.id.sidebar_s2) ;
        s3 = (TextView)findViewById(R.id.sidebar_s3) ;
        s4 = (TextView)findViewById(R.id.sidebar_s4) ;
        s5 = (TextView)findViewById(R.id.sidebar_s5) ;

        tv_mass = new TextView[]{s1, s2, s3, s4, s5};
        for(int i=0; i<tv_mass.length; i++){
            tv_mass[i].setTextSize(in.get_font_1());
        }

        lila_side_my_prof       = (LinearLayout)findViewById(R.id.lila_side_my_prof);
        lila_side_my_mess       = (LinearLayout)findViewById(R.id.lila_side_my_mess);
        lila_side_my_odres      = (LinearLayout)findViewById(R.id.lila_side_my_odres);
        lila_side_forum         = (LinearLayout)findViewById(R.id.lila_side_forum);

        //lila_side_paiment       = (LinearLayout)findViewById(R.id.lila_side_paiment);
        //lila_side_share         = (LinearLayout)findViewById(R.id.lila_side_share);
        //lila_side_help          = (LinearLayout)findViewById(R.id.lila_side_help);

        ff = new LinearLayout[]{lila_side_my_prof, lila_side_my_mess, lila_side_my_odres,
                lila_side_forum};//, lila_side_paiment, lila_side_share, lila_side_help};

        s1_close = (ScrollView)findViewById(R.id.scrollView3);
        b1_close = (Button)findViewById(R.id.butSidebar);
        button_fin_close = (Button)findViewById(R.id.button_fin_close);

        s1_close.setOnTouchListener(this);
        b1_close.setOnTouchListener(this);
        button_fin_close.setOnTouchListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(in.getOnemay()==false){
            this.finish();
        }
        in.setpotok_main(false);
        tvFSidebar.setText(in.get_FIO());
        for(int i =1; i<8; i++){
            if(i==in.getChoice_of_menus()){
                ff[i-1].setBackgroundResource(R.color.reply1);
            }
            else{
                // ff[i-1].setBackgroundResource(R.drawable.transpar);
            }
        }

    }


    public void onClickSidebar(View v){
        Intent inSid = null;
        switch (v.getId()){
            case R.id.butSidebar:
                lilaSidebar.setBackgroundResource(R.drawable.transpar);
                in.setBooleanMenu(true);
                Sidebar.this.finish();
                break;
            case R.id.sid_but_1:
                inSid = new Intent(Sidebar.this, My_profile.class);
                startActivity(inSid);
                this.finish();
                break;
            case R.id.sid_but_2:
                inSid = new Intent(Sidebar.this, My_theme.class);
                startActivity(inSid);
                this.finish();
                break;
            case R.id.sid_but_3:
                break;
            case R.id.sid_but_4:
                inSid = new Intent(Sidebar.this, Forum.class);
                startActivity(inSid);
                this.finish();
                break;
            case R.id.sid_but_5:
                break;
            case R.id.sid_but_6:
                break;
            case R.id.sid_but_7:
                inSid = new Intent(Sidebar.this, Settings.class);
                startActivity(inSid);
                this.finish();
                break;
            case R.id.sid_but_8:
                break;
            case R.id.sid_but_9:
                //inSid = new Intent(Sidebar.this, Paid_services.class);
                inSid = new Intent(Sidebar.this, About_us.class);
                startActivity(inSid);
                this.finish();
                break;
        }
    }

    public float xX = 0;
    public float xNew = 0;
    Boolean tr = true;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case (MotionEvent.ACTION_UP):
                if(xNew < -300){
                    Sidebar.this.finish();
                }
                tr = true;
                break;
            case (MotionEvent.ACTION_DOWN):
                xX = event.getX();
                xNew = 0;
                Log.d("qwerty", "ACTION_DOWN xX "+xX);
                break;
            case (MotionEvent.ACTION_MOVE):
                Log.d("qwerty", "ACTION_MOVE ");
                if(tr){
                    xX = event.getX();
                    tr= false;
                }else{
                    int historySize = event.getHistorySize();
                    for (int i = 0; i <historySize; i++) {
                        float x = event.getHistoricalX(i);
                        processMovement(x);
                    }
                    float x = event.getX();
                    processMovement(x);
                }

        }
        return false;
    }
    private void processMovement(float _x) {
        // Todo: Обработка движения.
        Log.d("qwerty", "xX " + xX);
        Log.d("qwerty", "_x " + _x);
        xNew = xX -_x;
        Log.d("qwerty", "xNew " + xNew);
        Log.d("qwerty", " ");
        Log.d("qwerty", " ");
    }

}

    /*Button butSidebar;
    AdIn in;

    public int[] icon = {
            R.drawable.icon_my_prof,
            R.drawable.icon_my_mess,
            R.drawable.icon_my_odres,
            R.drawable.icon_paiment,
            R.drawable.icon_o_nas,
            R.drawable.icon_o_nas,
            R.drawable.icon_help
    };

    public static final String ATTRIBUTE_NAME_TEXT = "text";
    public static final String ATTRIBUTE_NAME_IMAGE = "image";

    public LinearLayout lilaSidebar;
    public TextView tvNikSidebar, tv_sidebar_input;
    public ImageView image_user_Sidebar;
    public LinearLayout lila_side_my_mess, lila_side_my_prof, lila_side_my_odres, lila_side_to_send_quest, lila_side_to_send_doc,
            lila_side_forum, lila_side_jur, lila_side_nat, lila_side_map, lila_side_help, lila_side_paiment, lila_side_share;
    public LinearLayout[] ff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidebar);

        in = new AdIn();

        Log.d("qwerty", "Start Sidebar");

        butSidebar = (Button)findViewById(R.id.butSidebar);
        lilaSidebar = (LinearLayout)findViewById(R.id.lilaSidebar);

        lila_side_my_prof       = (LinearLayout)findViewById(R.id.lila_side_my_prof);
        lila_side_my_mess       = (LinearLayout)findViewById(R.id.lila_side_my_mess);
        lila_side_my_odres      = (LinearLayout)findViewById(R.id.lila_side_my_odres);

        lila_side_forum         = (LinearLayout)findViewById(R.id.lila_side_forum);

        lila_side_paiment       = (LinearLayout)findViewById(R.id.lila_side_paiment);
        lila_side_share         = (LinearLayout)findViewById(R.id.lila_side_share);
        lila_side_help          = (LinearLayout)findViewById(R.id.lila_side_help);

        ff = new LinearLayout[]{lila_side_my_prof, lila_side_my_mess, lila_side_my_odres, lila_side_to_send_quest,
                lila_side_to_send_doc, lila_side_forum, lila_side_jur, lila_side_nat, lila_side_map, lila_side_paiment, lila_side_share, lila_side_help};


        switch (in.getChoice_of_menus()){
            case 0:
                for(int i = 0; i<12; i++){
                    if(i == 0){
                        ff[i].setBackgroundResource(R.color.reply1);
                    }
                    else{
                        ff[i].setBackgroundResource(R.drawable.transpar);
                    }
                }
                break;
            case 1:
                for(int i = 0; i<12; i++){
                    if(i == 1){
                        ff[i].setBackgroundResource(R.color.reply1);
                    }
                    else{
                        ff[i].setBackgroundResource(R.drawable.transpar);
                    }
                }
                break;
            case 2:
                for(int i = 0; i<12; i++){
                    if(i == 2){
                        ff[i].setBackgroundResource(R.color.reply1);
                    }
                    else{
                        ff[i].setBackgroundResource(R.drawable.transpar);
                    }
                }
                break;
            case 3:
                for(int i = 0; i<12; i++){
                    if(i == 3){
                        ff[i].setBackgroundResource(R.color.reply1);
                    }
                    else{
                        ff[i].setBackgroundResource(R.drawable.transpar);
                    }
                }
                break;
            case 4:
                for(int i = 0; i<12; i++){
                    if(i == 4){
                        ff[i].setBackgroundResource(R.color.reply1);
                    }
                    else{
                        ff[i].setBackgroundResource(R.drawable.transpar);
                    }
                }
                break;
            case 5:
                for(int i = 0; i<12; i++){
                    if(i == 5){
                        ff[i].setBackgroundResource(R.color.reply1);
                    }
                    else{
                        ff[i].setBackgroundResource(R.drawable.transpar);
                    }
                }
                break;
            case 6:
                for(int i = 0; i<12; i++){
                    if(i == 6){
                        ff[i].setBackgroundResource(R.color.reply1);
                    }
                    else{
                        ff[i].setBackgroundResource(R.drawable.transpar);
                    }
                }
                break;
            case 7:
                for(int i = 0; i<12; i++){
                    if(i == 7){
                        ff[i].setBackgroundResource(R.color.reply1);
                    }
                    else{
                        ff[i].setBackgroundResource(R.drawable.transpar);
                    }
                }
                break;
            case 8:
                for(int i = 0; i<12; i++){
                    if(i == 8){
                        ff[i].setBackgroundResource(R.color.reply1);
                    }
                    else{
                        ff[i].setBackgroundResource(R.drawable.transpar);
                    }
                }
                break;
            case 9:
                for(int i = 0; i<12; i++){
                    if(i == 9){
                        ff[i].setBackgroundResource(R.color.reply1);
                    }
                    else{
                        ff[i].setBackgroundResource(R.drawable.transpar);
                    }
                }
                break;
            case 10:
                for(int i = 0; i<12; i++){
                    if(i == 10){
                        ff[i].setBackgroundResource(R.color.reply1);
                    }
                    else{
                        ff[i].setBackgroundResource(R.drawable.transpar);
                    }
                }
                break;
            case 11:
                for(int i = 0; i<12; i++){
                    if(i == 11){
                        ff[i].setBackgroundResource(R.color.reply1);
                    }
                    else{
                        ff[i].setBackgroundResource(R.drawable.transpar);
                    }
                }
                break;
            case 12:
                for(int i = 0; i<12; i++){

                    ff[i].setBackgroundResource(R.drawable.transpar);

                }
                break;
        }

        tvNikSidebar = (TextView)findViewById(R.id.tvNikSidebar);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(in.getOnemay())
        {
            tv_sidebar_input.setText("");
            tvNikSidebar.setText("ПростоПольз");
            image_user_Sidebar.setImageResource(R.drawable.ricfas);
        }
        else
        {
            tv_sidebar_input.setText(R.string.l_input);
            tvNikSidebar.setText("");
            image_user_Sidebar.setImageResource(R.drawable.transpar);
        }

        in.setBooleanMenu(false);
    }

    public void onClickSidebar (View view){
        Intent intent_sidebar = null;

        Log.d("qwerty", " void onClick");
        if(view.getId() ==R.id.butSidebar){
            lilaSidebar.setBackgroundResource(R.drawable.transpar);
            in.setBooleanMenu(true);
            Sidebar.this.finish();
        }
        else if(view.getId() ==R.id.lila_side_my_prof||view.getId() ==R.id.text_side_my_prof||view.getId() ==R.id.image_side_my_prof) {
            if(in.getOnemay())
            {
                intent_sidebar = new Intent(Sidebar.this, ProfileJur.class);
                startActivity(intent_sidebar);
                Sidebar.this.finish();
            }
            else{
                openDialog();
            }
        }
        else if(view.getId() ==R.id.lila_side_my_mess||view.getId() ==R.id.text_side_my_mess||view.getId() ==R.id.image_side_my_mess){
            if(in.getOnemay())
            {
                intent_sidebar = new Intent(Sidebar.this,MyAnswers.class);
                startActivity(intent_sidebar);
                Sidebar.this.finish();
            }
            else{
                openDialog();
            }
        }
        else if(view.getId() ==R.id.lila_side_my_odres||view.getId() ==R.id.text_side_my_odres||view.getId() ==R.id.image_side_my_odres){
            intent_sidebar = new Intent(Sidebar.this, Maps_jur.class);
            startActivity(intent_sidebar);
            Sidebar.this.finish();

        }



        else if(view.getId() ==R.id.lila_side_forum||view.getId() ==R.id.text_side_forum||view.getId() ==R.id.image_side_forum){
            intent_sidebar = new Intent(Sidebar.this,Forum.class);
            startActivity(intent_sidebar);
            Sidebar.this.finish();

        }

        else if(view.getId() ==R.id.lila_side_paiment||view.getId() ==R.id.text_side_paiment||view.getId() ==R.id.image_side_paiment){

        }
        else if(view.getId() ==R.id.lila_side_share||view.getId() ==R.id.text_side_share||view.getId() ==R.id.image_side_share){

        }
        else if(view.getId() ==R.id.lila_side_help||view.getId() ==R.id.text_side_help||view.getId() ==R.id.image_side_help){


        }
        else if(view.getId() == R.id.but_sidebar_vhod)
        {
            if(in.getOnemay()){

            }else {
                intent_sidebar = new Intent(Sidebar.this,LogIN.class);
                startActivity(intent_sidebar);
                Sidebar.this.finish();
            }
        }
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(Sidebar.this);
        dialog.setTitle("ВОЙТИ В СИСТЕМУ?");
        dialog.setContentView(R.layout.dialog_not_registered);

        Button but_dialog_reg = (Button) dialog.getWindow().findViewById(
                R.id.but_dialog_reg);

        but_dialog_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intD = new Intent(Sidebar.this, LogIN.class);
                startActivity(intD);
                Sidebar.this.finish();
                //dialog.dismiss();
            }
        });
        Button but_dialog_reg_cancel = (Button) dialog.getWindow().findViewById(
                R.id.but_dialog_reg_cancel);

        but_dialog_reg_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //__________onTouchEvent_______________________________________________

    public float xX = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();


        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            xX = event.getX();
        }

        switch (action) {

            case (MotionEvent.ACTION_MOVE):
            {
                int historySize = event.getHistorySize();
                for (int i = 0; i <historySize; i++) {
                    float x = event.getHistoricalX(i);
                    processMovement(x);
                }
                float x = event.getX();
                processMovement(x);
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
    private void processMovement(float _x) {
        // Todo: Обработка движения.
        int dq = (int) (xX-_x);
        if( dq  < -300){
            if(in.getBooleanMenu()==false){
                lilaSidebar.setBackgroundResource(R.drawable.transpar);
                Sidebar.this.finish();
                in.setBooleanMenu(true);
            }
        }
    }
}*/
