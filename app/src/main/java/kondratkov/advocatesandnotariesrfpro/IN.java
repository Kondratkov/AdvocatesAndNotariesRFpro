package kondratkov.advocatesandnotariesrfpro;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kondratkov.advocatesandnotariesrfpro.account.Address;
import kondratkov.advocatesandnotariesrfpro.account.City;
import kondratkov.advocatesandnotariesrfpro.account.JuristSpecialization;
import kondratkov.advocatesandnotariesrfpro.account.Region;
import kondratkov.advocatesandnotariesrfpro.api_classes.ClientQuestion;
import kondratkov.advocatesandnotariesrfpro.data_theme.AllQuestions;
import kondratkov.advocatesandnotariesrfpro.data_theme.AllQuestions_forum;

/**
 * Created by Modest on 11.03.2016.
 */
public class IN {

    public static int id_jur =0;
    public int get_id_jur(){return this.id_jur;}
    public void set_id_jur (int id_jur){this.id_jur = id_jur;}

    public static boolean kost_push = false;

    public static String name_jur ="";
    public String get_name_jur(){return this.name_jur;}
    public void set_name_jur (String name_jur){this.name_jur = name_jur;}

    public static String password_jur = "123";
    public String get_password_jur(){return this.password_jur;}
    public void set_password_jur (String password_jur){this.password_jur = password_jur;}

    public static boolean onemay = false;
    public boolean getOnemay(){return this.onemay;}
    public void setOnemay(boolean onemay){this.onemay = onemay;}

    public static int id_usr =0;
    public int get_id_usr(){return this.id_usr;}
    public void set_id_usr (int id_usr){this.id_usr = id_usr;}

    //public static String url = "192.168.0.100/api";
    //public static String url = "vsundupey.vds.serverdale.com/api";
    //public static String url = "195.128.124.172/api";
    public static String url = "app.mmka.info/api";
    public String get_url(){return this.url;}
    public void set_url (String url){this.url = url;}

    public static int idt =0;
    public int get_idt(){return this.idt;}
    public void set_idt (int idt){this.idt = idt;}

    public static String id_sud_ter = "";
    public String get_id_sud_ter(){return (id_sud_ter);}
    public void set_id_sud_ter (String id_sud_ter){this.id_sud_ter = id_sud_ter;}

    public static boolean potok_main = false;
    public boolean getpotok_main(){return this.potok_main;}
    public void setpotok_main(boolean potok_main){this.potok_main = potok_main;}

    public static int read =8;
    public int get_read(){return this.read;}
    public void set_read (int read){this.read = read;}

    public static int place =0;
    public int get_place(){return this.place;}
    public void set_place (int place){this.place = place;}

    public static String date = "0";
    public String get_date(){return this.date;}
    public void set_date (String date){this.date = date;}

    public static int font_1 = 0;
    public int get_font_1(){return this.font_1;}
    public void set_font_1 (int font_1){this.font_1 = font_1;}

    public static int font_2 = 0;
    public int get_font_2(){return this.font_2;}
    public void set_font_2 (int font_2){this.font_2 = font_2;}

    public static int font_3 = 0;
    public int get_font_3(){return this.font_3;}
    public void set_font_3 (int font_3){this.font_3 = font_3;}

    private static String name_user;

    public static String getName_user() {
        return name_user;
    }

    public static void setName_user(String name_user) {
        IN.name_user = name_user;
    }

    public static JuristSpecialization[] list_specialization = null;
    public JuristSpecialization[] get_list_specialization(){return this.list_specialization;}
    public void set_list_specialization (JuristSpecialization[] list_specialization){this.list_specialization = list_specialization;}

    //sidebar
    public static boolean startMenu = true;
    public boolean getBooleanMenu(){return this.startMenu;}
    public void setBooleanMenu(boolean startMenu){this.startMenu = startMenu;}

    public static int choice_of_menus = 12;
    public int  getChoice_of_menus(){return (choice_of_menus);}
    public void setChoice_of_menus(int choice_of_menus){this.choice_of_menus=choice_of_menus;}

    public static String [] arrayForumThem = null;
    public String [] get_arrayForumThem(){return this.arrayForumThem;}
    public void set_arrayForumThem (String [] arrayForumThem){this.arrayForumThem = arrayForumThem;}

    public static String address = "";
    public String get_address(){return this.address;}
    public void set_address (String address){this.address = address;}

    public static double coor_x = 0;
    public double get_coor_x(){return this.coor_x;}
    public void set_coor_x (double coor_x){this.coor_x = coor_x;}

    public static double coor_y = 0;
    public double get_coor_y(){return this.coor_y;}
    public void set_coor_y (double coor_y){this.coor_y = coor_y;}

    public static String email_reg = "123";
    public static String password = "123";
    public String getEmail_reg(){return this.email_reg;}
    public String getPassword(){return this.password;}

    public static String token = "";
    public String get_token(){return this.token;}
    public void set_token (String token){this.token = token;}

    public static ClientQuestion clientQuestion;
    public ClientQuestion get_clientQuestion(){return  this.clientQuestion;}
    public void set_clientQuestion(ClientQuestion clientQuestion){this.clientQuestion = clientQuestion;}

    public static boolean Tip = false;
    public boolean get_Tip(){return Tip;}
    public void set_Tip(boolean Tip){this.Tip = Tip;}

    public static String token_type = "";
    public String get_token_type(){return this.token_type;}
    public void set_token_type (String token_type){this.token_type = token_type;}

    public static String text = "";
    public String get_text(){return this.text;}
    public void set_text (String text){this.text = text;}

    public static String text1 = "";
    public String get_text1(){return this.text1;}
    public void set_text1 (String text1){this.text1 = text1;}

    public static int int_start_activity= 0 ;
    public int  get_int_start_activity(){return (int_start_activity);}
    public void set_int_start_activity(int int_start_activity){this.int_start_activity=int_start_activity;}

    public static boolean start_service = false;
    public boolean get_start_service(){return this.start_service;}
    public void set_start_service (boolean start_service){this.start_service = start_service;}

    public static List<AllQuestions> list_pr = null;
    public List<AllQuestions> get_list_pr(){return this.list_pr;}
    public void set_list_pr (List<AllQuestions> list_pr){this.list_pr = list_pr;}

    public static List<AllQuestions_forum> list_f = null;
    public List<AllQuestions_forum> get_list_f(){return this.list_f;}
    public void set_list_f (List<AllQuestions_forum> list_f){this.list_f = list_f;}

    public static Context context = null;
    public Context get_context(){return this.context;}
    public void set_context (Context context){this.context = context;}

    public static Activity activity = null;
    public Activity get_activity(){return this.activity;}
    public void set_activity (Activity activity){this.activity = activity;}

    public static int status = 0;
    public int  get_status(){return (status);}
    public void set_status(int status){this.status = status;}

    public static Region region;
    public Region get_region(){return (region);}
    public void set_region(Region region){this.region = region;}

    public static City city;
    public City get_city(){return city;}
    public void set_city(City city){this.city = city;}

    public static String FIO;
    public String get_FIO(){return FIO;}
    public void set_FIO(String FIO){this.FIO = FIO;}

    public static Address address1;
    public Address getAddress(){return address1;}
    public void  setAddress(Address address){this.address1 = address;}

    public static int quest=0;
    public int get_quest(){return quest;}
    public void set_quest(int quest){this.quest = quest;}

    public String dateDisplay(String sDate){
        String sDateDisplay="";
        long feedTime=0;
        long myTime = System.currentTimeMillis();
        String dated = sDate.substring(0,10)+" "+sDate.substring(11,19);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(dated);
            feedTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(feedTime>0){
            if(myTime-feedTime<60000){
                sDateDisplay = DateUtils.formatDateTime(context, feedTime,
                        DateUtils.FORMAT_SHOW_TIME);
            }
            else if(myTime-feedTime<3600000){
                CharSequence timeago;
                timeago = DateUtils.getRelativeTimeSpanString(
                        feedTime, System.currentTimeMillis(),
                        DateUtils.SECOND_IN_MILLIS);
                sDateDisplay= (String) timeago;
            }else{
                sDateDisplay = DateUtils.formatDateTime(context, feedTime,
                        DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_SHOW_TIME);
            }
        }

        return sDateDisplay;
    }
    public String dateDisplayStag(String sDate){
        String sDateDisplay="";
        long feedTime=0;
        long myTime = System.currentTimeMillis();


        try {String dated = sDate.substring(0,10)+" "+sDate.substring(11,19);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = new Date();
            date = sdf.parse(dated);
            feedTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(feedTime>0){
            long msYour = myTime-feedTime;
            double dYour = msYour/31536000000.0;
            if(dYour<1){
                sDateDisplay = "менее года";
            }else if(1>dYour&&dYour<5){
                sDateDisplay = "от 1 до 5 лет";
            }else {
                sDateDisplay = "свыше 5 лет";
            }
        }
        return sDateDisplay;
    }
}
