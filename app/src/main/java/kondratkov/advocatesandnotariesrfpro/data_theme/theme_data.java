package kondratkov.advocatesandnotariesrfpro.data_theme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kondratkov.advocatesandnotariesrfpro.IN;


public class theme_data extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "mythemedatajur.db";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_TABLE = "theme_table";
    private static final String DATABASE_TABLE_forum = "theme_table_forum";
    private static final String DATABASE_TABLE_mess = "mess_table";

    public IN in = new IN();

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_IDT   = "idt";
    public static final String COLUMN_IDU   = "idu";
    public static final String COLUMN_IDJ   = "idj";
    public static final String COLUMN_DATE  = "date";
    public static final String COLUMN_NAME  = "name";
    public static final String COLUMN_COUNT = "count";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_READ  = "read";
    public static final String COLUMN_TEXT  = "text";
    public static final String COLUMN_TEXT_dell  = "text_dell";
    public static final String COLUMN_THEME = "theme";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID + " integer primary key autoincrement, " +
            COLUMN_IDT    + " integer,"       +  COLUMN_IDU   + " integer,"       +  COLUMN_IDJ   + " integer," +
            COLUMN_DATE   + " text not null," +  COLUMN_NAME  + " text not null," +  COLUMN_COUNT + " integer," +
            COLUMN_PLACE  + " integer,"       +  COLUMN_READ  + " integer,"       +  COLUMN_TEXT  + " text not null," +
            COLUMN_THEME  + " integer);";//+COLUMN_TEXT_dell  + " text not null,"

    public static final String COLUMN_IDf = BaseColumns._ID;
    public static final String COLUMN_IDTf   = "idt";
    public static final String COLUMN_IDUf   = "idu";
    public static final String COLUMN_IDJf   = "idj";
    public static final String COLUMN_DATEf  = "date";
    public static final String COLUMN_NAMEf  = "name";
    public static final String COLUMN_COUNTf = "count";
    public static final String COLUMN_PLACEf = "place";
    public static final String COLUMN_READf  = "read";
    public static final String COLUMN_TEXTf  = "text";
    public static final String COLUMN_THEMEf = "theme";

    private static final String DATABASE_CREATE_SCRIPT_forum = "create table "
            + DATABASE_TABLE_forum + " (" + BaseColumns._ID + " integer primary key autoincrement, " +
            COLUMN_IDTf    + " integer,"       +  COLUMN_IDUf   + " integer,"       +  COLUMN_IDJf   + " integer," +
            COLUMN_DATEf   + " text not null," +  COLUMN_NAMEf  + " text not null," +  COLUMN_COUNTf + " integer," +
            COLUMN_PLACEf  + " integer,"       +  COLUMN_READf  + " integer,"       +  COLUMN_TEXTf  + " text not null," +
            COLUMN_THEMEf  + " integer);";

    public static final String COLUMN_IDm = BaseColumns._ID;
    public static final String COLUMN_IDTm    = "idt";
    public static final String COLUMN_IDUm    = "idu";
    public static final String COLUMN_IDJm    = "idj";
    public static final String COLUMN_DATEm   = "date";
    public static final String COLUMN_NAMEm   = "name";
    public static final String COLUMN_TEXTm   = "text";
    public static final String COLUMN_PLACEm  = "place";
    public static final String COLUMN_TIPWHOm = "tip_who";

    private static final String DATABASE_CREATE_SCRIPT_mess = "create table "
            + DATABASE_TABLE_mess + " (" + BaseColumns._ID + " integer primary key autoincrement, " +
            COLUMN_IDTm    + " integer,"       +  COLUMN_IDUm   + " integer,"       +  COLUMN_IDJm   + " integer," +
            COLUMN_DATEm   + " text not null," +  COLUMN_NAMEm  + " text not null," +  COLUMN_TEXTm  + " text not null," + COLUMN_PLACEm + " integer," +
            COLUMN_TIPWHOm  + " integer);";



    public theme_data(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public theme_data(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
    }

    public theme_data(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("qwerty", "onCreate");

        db.execSQL(DATABASE_CREATE_SCRIPT_mess);
        db.execSQL(DATABASE_CREATE_SCRIPT_forum);
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();
        try {
            Log.d("qwerty", "onCreate 1");
            //db.execSQL(DATABASE_CREATE_SCRIPTf);
            Log.d("qwerty", "onCreate 2");
           // db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE+"");
            //onCreate(db);
            Log.d("qwerty", "onCreate 3");
        } finally {
            db.endTransaction();
        }
    }

    // ��������� ����� �������
    public void addQuestion(AllQuestions questions) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(  COLUMN_IDT,   questions.get_idt());
        values.put(  COLUMN_IDU,   questions.get_idu());
        values.put(  COLUMN_IDJ,   questions.get_idj());
        values.put(  COLUMN_DATE,  questions.get_date());
        values.put(  COLUMN_NAME,  questions.get_name());
        values.put(  COLUMN_COUNT, questions.get_count());
        values.put(  COLUMN_PLACE, questions.get_place());
        values.put(  COLUMN_READ,  questions.get_read());
        values.put(  COLUMN_TEXT,  questions.get_text());
        values.put(  COLUMN_THEME, questions.get_theme());

        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    // �������� �������
    public AllQuestions getQuestion(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DATABASE_TABLE, new String[] {
            COLUMN_ID,   COLUMN_IDT,   COLUMN_IDU,   COLUMN_IDJ,  COLUMN_DATE,
            COLUMN_NAME, COLUMN_COUNT, COLUMN_PLACE, COLUMN_READ, COLUMN_TEXT,
            COLUMN_THEME }, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        AllQuestions questions = new AllQuestions(
                Integer.parseInt(cursor.getString(0)),//id
                Integer.parseInt(cursor.getString(1)), //idt
                Integer.parseInt(cursor.getString(2)),//idu
                Integer.parseInt(cursor.getString(3)),//idj
                cursor.getString(4),//date
                cursor.getString(5), //name
                Integer.parseInt(cursor.getString(6)),//count
                Integer.parseInt(cursor.getString(7)),//place
                Integer.parseInt(cursor.getString(8)),//self
                cursor.getString(9),//text
                Integer.parseInt(cursor.getString(10)));//theme
        return questions;
    }

    //vozrat danih o theme
    public String date_question(int id, int id_table)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String con = "";

        Cursor cursor = db.query(DATABASE_TABLE, new String[]{
                        COLUMN_ID,   COLUMN_IDT,   COLUMN_IDU,   COLUMN_IDJ,  COLUMN_DATE,
                        COLUMN_NAME, COLUMN_COUNT, COLUMN_PLACE, COLUMN_READ, COLUMN_TEXT,
                        COLUMN_THEME}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        for(int i =0; i<10; i++)
        {
            if(i==id_table)con = cursor.getString(i);
        }
        return con;
    }

    /*
    public List<AllQuestions> getAllQuestion() {
        List contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                AllQuestions questions = new AllQuestions();
                questions.setID(Integer.parseInt(cursor.getString(0)));//id
                questions.set_idt(Integer.parseInt(cursor.getString(1))); //idt
                questions.set_idu(Integer.parseInt(cursor.getString(2)));//idu
                questions.set_idj(Integer.parseInt(cursor.getString(3)));//idj
                questions.set_date(cursor.getString(4));//date
                questions.set_name(cursor.getString(5)); //name
                questions.set_count(Integer.parseInt(cursor.getString(6)));//count
                questions.set_place(Integer.parseInt(cursor.getString(7)));//place
                questions.set_read(Integer.parseInt(cursor.getString(8)));//self
                questions.set_text(cursor.getString(9));//text
                questions.set_theme(Integer.parseInt(cursor.getString(10)));//theme

                contactList.add(questions);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }
    */
    public int getContactsCount() {
        String countQuery = "SELECT * FROM " + DATABASE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery(countQuery, null);
        Cursor cursor = db.query(DATABASE_TABLE,
                null,//new String[]{"name", "date"},
                COLUMN_IDJ +" = "+in.get_id_jur(),
                null,
                null,
                null,
                COLUMN_DATE);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public boolean idt_yes(int idt){
        boolean bool= true;
        String countQuery = "SELECT * FROM " + DATABASE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery(countQuery, null);
        Cursor cursor = db.query(DATABASE_TABLE,
                null,//new String[]{"name", "date"},
                COLUMN_IDT + " = " + String.valueOf(idt),
                null,
                null,
                null,
                COLUMN_DATE);
        int count = cursor.getCount();
        cursor.close();
        if(count<1)bool = false;
        return bool;
    }

    public String getEndDate(){
        String end_date = "";
        return end_date;}

    public int updateQuest(AllQuestions questions) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(  COLUMN_IDT,   questions.get_idt());
        values.put(  COLUMN_IDU,   questions.get_idu());
        values.put(  COLUMN_IDJ,   questions.get_idj());
        values.put(  COLUMN_DATE,  questions.get_date());
        values.put(  COLUMN_NAME,  questions.get_name());
        values.put(  COLUMN_COUNT, questions.get_count());
        values.put(  COLUMN_PLACE, questions.get_place());
        values.put(  COLUMN_READ,  questions.get_read());
        values.put(  COLUMN_TEXT, questions.get_text());
        values.put(COLUMN_THEME, questions.get_theme());

        // ��������� ������
        return db.update(DATABASE_TABLE, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(questions.getID()) });
    }

    // ������� �������
    public void deleteQuest(AllQuestions questions) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, COLUMN_ID + " = ?",
                new String[] {String.valueOf(questions.getID())});
        db.close();
    }

    public List<AllQuestions> getSort1Question() {
        List contactList1 = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE,
                null,//new String[]{"name", "date"},
                COLUMN_IDJ +" = "+in.get_id_jur(),
                null,
                null,
                null,
                COLUMN_DATE);

        if (cursor.moveToFirst()) {
            do {
                AllQuestions questions = new AllQuestions();
                questions.setID(Integer.parseInt(cursor.getString(0)));//id
                questions.set_idt(Integer.parseInt(cursor.getString(1))); //idt
                questions.set_idu(Integer.parseInt(cursor.getString(2)));//idu
                questions.set_idj(Integer.parseInt(cursor.getString(3)));//idj
                questions.set_date(cursor.getString(4));//date
                questions.set_name(cursor.getString(5)); //name
                questions.set_count(Integer.parseInt(cursor.getString(6)));//count
                questions.set_place(Integer.parseInt(cursor.getString(7)));//place
                questions.set_read(Integer.parseInt(cursor.getString(8)));//read
                questions.set_text(cursor.getString(9));//text
                questions.set_theme(Integer.parseInt(cursor.getString(10)));//theme

                contactList1.add(questions);

            } while (cursor.moveToNext());
        }
        cursor.close();
        in.set_list_pr(contactList1);
        return contactList1;
    }

    public List<AllQuestions> getSort2Question() {
        List contactList2 = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE,
                null,//new String[]{"name", "date"},
                COLUMN_IDJ +" = "+in.get_id_jur(),
                null,
                null,
                null,
                COLUMN_READ);

        if (cursor.moveToFirst()) {
            do {
                AllQuestions questions = new AllQuestions();
                questions.setID(Integer.parseInt(cursor.getString(0)));//id
                questions.set_idt(Integer.parseInt(cursor.getString(1))); //idt
                questions.set_idu(Integer.parseInt(cursor.getString(2)));//idu
                questions.set_idj(Integer.parseInt(cursor.getString(3)));//idj
                questions.set_date(cursor.getString(4));//date
                questions.set_name(cursor.getString(5)); //name
                questions.set_count(Integer.parseInt(cursor.getString(6)));//count
                questions.set_place(Integer.parseInt(cursor.getString(7)));//place
                questions.set_read(Integer.parseInt(cursor.getString(8)));//read
                questions.set_text(cursor.getString(9));//text
                questions.set_theme(Integer.parseInt(cursor.getString(10)));//theme

                contactList2.add(questions);

            } while (cursor.moveToNext());
        }
        cursor.close();
        in.set_list_pr(contactList2);
        return contactList2;
    }

    public List<AllQuestions> getSort3Question() {
        List contactList3 = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE,
                null,//new String[]{"name", "date"},
                COLUMN_IDJ +" = "+in.get_id_jur(),
                null,
                null,
                null,
                COLUMN_THEME);

        if (cursor.moveToFirst()) {
            do {
                AllQuestions questions = new AllQuestions();
                questions.setID(Integer.parseInt(cursor.getString(0)));//id
                questions.set_idt(Integer.parseInt(cursor.getString(1))); //idt
                questions.set_idu(Integer.parseInt(cursor.getString(2)));//idu
                questions.set_idj(Integer.parseInt(cursor.getString(3)));//idj
                questions.set_date(cursor.getString(4));//date
                questions.set_name(cursor.getString(5)); //name
                questions.set_count(Integer.parseInt(cursor.getString(6)));//count
                questions.set_place(Integer.parseInt(cursor.getString(7)));//place
                questions.set_read(Integer.parseInt(cursor.getString(8)));//read
                questions.set_text(cursor.getString(9));//text
                questions.set_theme(Integer.parseInt(cursor.getString(10)));//theme

                contactList3.add(questions);

            } while (cursor.moveToNext());
        }
        cursor.close();
        in.set_list_pr(contactList3);
        return contactList3;
    }
}

