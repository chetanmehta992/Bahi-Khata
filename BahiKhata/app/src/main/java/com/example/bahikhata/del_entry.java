package com.example.bahikhata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class del_entry extends AppCompatActivity {
    ListView listView;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_entry);
        db=new DatabaseHandler(this);

        listView = (ListView) findViewById(R.id.list1);
        listView.setAdapter(db.getAllContacts());
    }
    public void Update()
    {
        listView.setAdapter(db.getAllContacts());
    }
}