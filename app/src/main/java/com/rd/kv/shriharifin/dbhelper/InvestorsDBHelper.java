package com.rd.kv.shriharifin.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rd.kv.shriharifin.helper.InvestorsDBFileds.InvestorsAdd;

import java.sql.SQLException;


/**
 * Created by Data Crawl 6 on 31-03-2016.
 */
public class InvestorsDBHelper extends SQLiteOpenHelper {

    public InvestorsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "investors.db";

    private static final String SQL_CREATE_INVESTORS = "CREATE TABLE "
            + InvestorsAdd.TABLE_NAME + " (" + InvestorsAdd._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + InvestorsAdd.ID + " TEXT," //1
            + InvestorsAdd.NAME + " TEXT," //2
            + InvestorsAdd.AMOUNT + " TEXT," //3
            + InvestorsAdd.DATE + " TEXT" + " )"; //4

    private static final String SQL_DELETE_INVESTORS = "DROP TABLE IF EXISTS "
            + InvestorsAdd.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_INVESTORS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_INVESTORS);
        onCreate(db);
    }

    public long insertInvestors(String val1, String val2, String val3, String val4) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(InvestorsAdd.ID, val1);
        initialValues.put(InvestorsAdd.NAME, val2);
        initialValues.put(InvestorsAdd.AMOUNT, val3);
        initialValues.put(InvestorsAdd.DATE, val4);

        long l = db.insert(InvestorsAdd.TABLE_NAME, null, initialValues);
        db.close();
        return l;
    }

//    public Cursor InvestorsYearSum() throws SQLException {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String fetch = "Select sum(" + InvestorsAdd.AMOUNT + ") FROM " + InvestorsAdd.TABLE_NAME + " WHERE year(" + InvestorsAdd.DATE + ") = year(DateTime('now'))";
//        Cursor c = db.rawQuery(fetch, null);
//        if (c != null) {
//            c.moveToFirst();
//        }
//        return c;
//    }
}
