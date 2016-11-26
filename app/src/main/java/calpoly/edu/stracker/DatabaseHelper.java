package calpoly.edu.stracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by panktigandhi on 10/22/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "stracker.db";
    public static final String TABLE_EXPENSE = "expenses";
    public static final String ID = "ID";
    public static final String TASK = "TASK";
    public static final String DATE = "DATE";
    public static final String AMOUNT = "AMOUNT";
    public static final String CATEGORY = "CATEGORY";

    public static final String TABLE_CATEGORY = "category";
    public static final String CATEGORYNAME = "CATEGORYNAME";
    public static final String IMAGE = "IMAGE";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(" create table " + TABLE_EXPENSE + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, DATE TEXT, AMOUNT INTEGER, CATEGORY TEXT ) ");
            db.execSQL(" create table " + TABLE_CATEGORY + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, IMAGE TEXT, CATEGORYNAME TEXT ) ");
        } catch (Exception e) {
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if exists" + TABLE_EXPENSE);
        db.execSQL("Drop Table if exists" + TABLE_CATEGORY);
        onCreate(db);
    }

    public boolean insertData(String Task, String Date, String Amount, String Category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK, Task);
        contentValues.put(DATE, Date);
        contentValues.put(AMOUNT, Amount);
        contentValues.put(CATEGORY, Category);

        long result = db.insert(TABLE_EXPENSE, null, contentValues);
        if (result == -1) return false;
        else return true;
    }

    public boolean insertCategory(String image, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE, image);
        contentValues.put(CATEGORYNAME, category);

        long result = db.insert(TABLE_CATEGORY, null, contentValues);
        if (result == -1) return false;
        else return true;
    }



    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_EXPENSE, null);
        return res;

    }

    public Cursor getDataDateRange(String begin, String end) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_EXPENSE +
                " WHERE " + DATE + " >= " + begin +
                " AND " + DATE + " <= " + end, null);

        return res;
    }

    public boolean updateData(String Task, String Date, String Amount, String Category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TASK, Task);
        contentValues.put(DATE, Date);
        contentValues.put(AMOUNT, Amount);
        contentValues.put(CATEGORY, Category);
        db.update(TABLE_EXPENSE, contentValues, "TASK = ?", new String[]{Task});
        return true;
    }

    public Integer deleteData(String Task) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_EXPENSE, "TASK = ?", new String[]{Task});
    }

    public static String convertSqlDate(Calendar calendar) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(calendar.getTime());
    }

    public static String convertHumanDate(Calendar calendar) {
        String format = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(calendar.getTime());
    }
}
