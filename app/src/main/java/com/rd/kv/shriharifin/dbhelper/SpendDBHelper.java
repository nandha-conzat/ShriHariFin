package com.rd.kv.shriharifin.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rd.kv.shriharifin.helper.SpendDBFields.SpendAdd;

/**
 * Created by Data Crawl 6 on 31-03-2016.
 */
public class SpendDBHelper extends SQLiteOpenHelper {

    public SpendDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "spend.db";

    private static final String SQL_CREATE_SPEND = "CREATE TABLE "
            + SpendAdd.TABLE_NAME + " (" + SpendAdd._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SpendAdd.ID + " TEXT," //1
            + SpendAdd.AMOUNT + " TEXT," //2
            + SpendAdd.DATE + " TEXT," //3
            + SpendAdd.NAME + " TEXT," //4
            + SpendAdd.ADDRESS + " TEXT," //5
            + SpendAdd.PHONE + " TEXT," //6
            + SpendAdd.EMAIL + " TEXT," //7
            + SpendAdd.STATUS + " TEXT," //8
            + SpendAdd.NAME2 + " TEXT," //9
            + SpendAdd.INTEREST + " TEXT," //10
            + SpendAdd.DAYS + " TEXT," //11
            + SpendAdd.CREATED_DATE + " TEXT" + " )"; //12

    private static final String SQL_DELETE_SPEND = "DROP TABLE IF EXISTS "
            + SpendAdd.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SPEND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_SPEND);
        onCreate(db);
    }

    public long insertSpend(String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8, String val9, String val10, String val11, String val12) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(SpendAdd.ID, val1);
        initialValues.put(SpendAdd.AMOUNT, val2);
        initialValues.put(SpendAdd.DATE, val3);
        initialValues.put(SpendAdd.NAME, val4);
        initialValues.put(SpendAdd.ADDRESS, val5);
        initialValues.put(SpendAdd.PHONE, val6);
        initialValues.put(SpendAdd.EMAIL, val7);
        initialValues.put(SpendAdd.STATUS, val8);
        initialValues.put(SpendAdd.NAME2, val9);
        initialValues.put(SpendAdd.INTEREST, val10);
        initialValues.put(SpendAdd.DAYS, val11);
        initialValues.put(SpendAdd.CREATED_DATE, val12);

        long l = db.insert(SpendAdd.TABLE_NAME, null, initialValues);
        db.close();
        return l;
    }
}
