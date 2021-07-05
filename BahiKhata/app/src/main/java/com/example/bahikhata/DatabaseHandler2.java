package com.example.bahikhata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler2 extends SQLiteOpenHelper {

    String day,month,year;
    Context c;
    public DatabaseHandler2(Context context, String day, String month, String year) {
        super(context, "Member"+month+year, null, 1);
        c= context;
        this.day=day;
        this.month=month;
        this.year=year;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "create table  Liter"+ month + year + "(Name Text,Date Integer,Liter Integer)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    void addContact(String name, String liter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Date", day);
        values.put("Liter", liter);
        db.insert("Liter"+month + year, null, values);
        Toast.makeText(c, "Inserted", Toast.LENGTH_SHORT).show();
        ((regular_entry)c).Update();
        db.close();
    }

    public ArrayList getAllContacts() {
        ArrayList a=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM Liter"+ month + year +" WHERE Date="+day, null);
        if (cursor.moveToFirst()) {
            do {
                a.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return a;
    }

    public ListAdapter showtoday() {
        ArrayList name=new ArrayList();
        ArrayList liter=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM Liter"+ month + year +" WHERE Date="+day, null);
        if (cursor.moveToFirst()) {
            do {
                name.add(cursor.getString(0));
                liter.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        ArrayAdapter adapter = new todayListAdapter(c, name,liter);
        return adapter;
    }
    public void deleteContact(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String del = "DELETE FROM Liter"+ month + year +" WHERE Name = \"" + name + "\" And Date = " + day;
        db.execSQL(del);
        today context = (today) c;
        context.Update();
    }
}
class todayListAdapter extends ArrayAdapter {
    Context context;
    ArrayList namelist;
    ArrayList literlist;
    public todayListAdapter(Context context, ArrayList name, ArrayList liter) {
        super(context, R.layout.edit1,R.id.name,name);
        this.context=context;
        namelist=name;
        literlist=liter;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.edit1, parent,false);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView liter = (TextView) convertView.findViewById(R.id.liter);
        name.setText(namelist.get(position).toString());
        liter.setText(literlist.get(position).toString());
        return convertView;
    }
}