package com.example.bahikhata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler3 extends SQLiteOpenHelper {

    private final Context c;
    private final String month;
    private final String year;

    public DatabaseHandler3(Context context, String month, String year) {
        super(context, "Memberpay"+month+year, null, 1);
        c= context;
        this.month=month;
        this.year=year;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
