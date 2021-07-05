package com.example.bahikhata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context c;
    public DatabaseHandler(Context context) {
        super(context, "Member", null, 1);
        c=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "create table Member(Name Text,Default_Liter Integer)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    void addContact(String name,String default_liter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Default_Liter", default_liter);
        Cursor cursor = db.rawQuery("SELECT  * FROM Member WHERE Name = '"+name+"' ",null);
        if (cursor.moveToFirst())
            Toast.makeText(c, "Name is already exist", Toast.LENGTH_SHORT).show();
        else {
            db.insert("Member", null, values);
            Toast.makeText(c, "Inserted", Toast.LENGTH_SHORT).show();}
        db.close();
    }

    public ArrayAdapter getAllContacts() {
        ArrayList name=new ArrayList();
        ArrayList liter=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM Member", null);

        if (cursor.moveToFirst()) {
            do {
                name.add(cursor.getString(0));
                liter.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        ArrayAdapter adapter = new ListAdapter(c, name,liter);
        return adapter;
    }
    public ArrayAdapter getAllContactsedit() {
        ArrayList name=new ArrayList();
        ArrayList liter=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM Member", null);

        if (cursor.moveToFirst()) {
            do {
                name.add(cursor.getString(0));
                liter.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        ArrayAdapter adapter = new ListAdapteredit(c, name,liter);
        return adapter;
    }
    public ArrayAdapter remainigentry(String day, String month, String year) {
        ArrayList name=new ArrayList();
        ArrayList liter=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM Member", null);
        DatabaseHandler2 db1 =new DatabaseHandler2(c,day,month,year);
        ArrayList a = db1.getAllContacts();
        if (cursor.moveToFirst()) {
            do {
                if(!a.contains(cursor.getString(0))){
                name.add(cursor.getString(0));
                liter.add(cursor.getString(1));}
            } while (cursor.moveToNext());
        }
        ArrayAdapter adapter = new remainigentryAdapter(c,name,liter,db1);
        return adapter;
    }

    public void deleteContact(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        String del = "DELETE FROM Member WHERE Name = \"" + s + "\"";
        db.execSQL(del);
        del_entry context = (del_entry) c;
        context.Update();
    }

    public void updatemember(String newname, String oldname, String newliter, String oldliter) {
        SQLiteDatabase db = this.getWritableDatabase();
        String update = "UPDATE Member SET Name = \"" + newname + "\",Default_Liter = \"" + newliter +"\" WHERE Name = \"" + oldname + "\" And Default_Liter = \"" + oldliter +"\"";
        db.execSQL(update);
        Edit context = (Edit) c;
        context.Update();
    }
}
class ListAdapter extends ArrayAdapter {
    Context context;
    ArrayList namelist;
    ArrayList literlist;
    public ListAdapter(Context context, ArrayList name, ArrayList liter) {
        super(context, R.layout.del_list,R.id.name,name);
        this.context=context;
        namelist=name;
        literlist=liter;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.del_list, parent,false);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView liter = (TextView) convertView.findViewById(R.id.liter);
            convertView.findViewById(R.id.del_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHandler db=new DatabaseHandler(context);
                    db.deleteContact(name.getText().toString());
                }
            });
            name.setText(namelist.get(position).toString());
            liter.setText(literlist.get(position).toString());
        return convertView;
    }
}
class ListAdapteredit extends ArrayAdapter {
    Context context;
    ArrayList namelist;
    ArrayList literlist;
    public ListAdapteredit(Context context, ArrayList name, ArrayList liter) {
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
//            convertView.findViewById(R.id.del_button).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    DatabaseHandler db=new DatabaseHandler(context);
//                    db.deleteContact(name.getText().toString());
//                }
//            });
            name.setText(namelist.get(position).toString());
            liter.setText(literlist.get(position).toString());
        return convertView;
    }
}
class remainigentryAdapter extends ArrayAdapter {
    Context context;
    ArrayList namelist;
    ArrayList literlist;
    DatabaseHandler2 db1;
    public remainigentryAdapter(Context context, ArrayList name, ArrayList liter, DatabaseHandler2 db1) {
        super(context, R.layout.defaultentry,R.id.name,name);
        this.context=context;
        namelist=name;
        literlist=liter;
        this.db1=db1;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.defaultentry, parent,false);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView liter = (TextView) convertView.findViewById(R.id.liter);
        convertView.findViewById(R.id.default_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db1.addContact(namelist.get(position).toString(),literlist.get(position).toString());
            }
        });
        name.setText(namelist.get(position).toString());
        liter.setText(literlist.get(position).toString());
        return convertView;
    }
}