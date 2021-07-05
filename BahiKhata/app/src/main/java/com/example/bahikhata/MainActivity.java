package com.example.bahikhata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    int d,m,y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView);
        d = Integer.parseInt(new SimpleDateFormat("dd").format(new Date(calendarView.getDate())));
        m = Integer.parseInt(new SimpleDateFormat("MM").format(new Date(calendarView.getDate())));
        y = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date(calendarView.getDate())));
       calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if(year==y && month+1==m && dayOfMonth<=d)
                {
                    Intent intent = new Intent(MainActivity.this, regular_entry.class);
                    intent.putExtra("year", ""+year);
                    intent.putExtra("month", ""+month);
                    intent.putExtra("day", ""+dayOfMonth);
                    startActivity(intent);
                }
                else if(year==y && month+1<m)
                {

                }
                else if(year<y)
                {

                }
                else
                {
                    Toast.makeText(MainActivity.this, "wrong data for today", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.add_member).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Entry.class));
            }
        });
        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Edit.class));
            }
        });
        findViewById(R.id.today).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, today.class);
                intent.putExtra("year", ""+y);
                intent.putExtra("month", ""+(m-1));
                intent.putExtra("day", ""+d);
                startActivity(intent);
            }
        });
        findViewById(R.id.thatday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.datepick, null);
                ArrayList spinnerArray =  new ArrayList();
                for(int i=1;i<d;i++)
                spinnerArray.add(i);
                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, spinnerArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner sItems = popupView.findViewById(R.id.spinner);
                sItems.setAdapter(adapter);
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
                popupView.findViewById(R.id.thatday).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = sItems.getSelectedItem().toString();
                        popupWindow.dismiss();
                        Intent intent = new Intent(MainActivity.this, today.class);
                        intent.putExtra("year", ""+y);
                        intent.putExtra("month", ""+(m-1));
                        intent.putExtra("day", text);
                        startActivity(intent);
                    }
                });
            }
        });

    }
}