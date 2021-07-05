package com.example.bahikhata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class regular_entry extends AppCompatActivity {
    public ListView listView;
    DatabaseHandler db;
    DatabaseHandler2 db1;
    String year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_entry);

        year = getIntent().getStringExtra("year");
        month = getIntent().getStringExtra("month");
        day = getIntent().getStringExtra("day");

        db=new DatabaseHandler(this);
        db1=new DatabaseHandler2(this,day,month,year);
        listView = (ListView) findViewById(R.id.entry);
        listView.setAdapter(db.remainigentry(day,month,year));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.normalliter, null);
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
                popupView.findViewById(R.id.half).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db1.addContact(((TextView)view.findViewById(R.id.name)).getText().toString(),".5");
                        popupWindow.dismiss();
                    }
                });popupView.findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db1.addContact(((TextView)view.findViewById(R.id.name)).getText().toString(),"1");
                        popupWindow.dismiss();
                    }
                });popupView.findViewById(R.id.onehalf).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db1.addContact(((TextView)view.findViewById(R.id.name)).getText().toString(),"1.5");
                        popupWindow.dismiss();
                    }
                });popupView.findViewById(R.id.two).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db1.addContact(((TextView)view.findViewById(R.id.name)).getText().toString(),"2");
                        popupWindow.dismiss();
                    }
                });
                popupView.findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String data = ((EditText)popupView.findViewById(R.id.todayentry)).getText().toString();
                        if(data!="" && data!=null) {
                            db1.addContact(((TextView) view.findViewById(R.id.name)).getText().toString(), data);
                            popupWindow.dismiss();
                        }else{
                            Toast.makeText(regular_entry.this, "please enter today entry", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
    public void Update()
    {
        listView.setAdapter(db.remainigentry(day,month,year));
    }

}