package com.example.bahikhata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Edit extends AppCompatActivity {
    ListView listView;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        db=new DatabaseHandler(this);
        listView = (ListView) findViewById(R.id.list1);
        listView.setAdapter(db.getAllContactsedit());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.editpopup, null);
                ((TextView)popupView.findViewById(R.id.name)).setText(((TextView)view.findViewById(R.id.name)).getText().toString());
                ((TextView)popupView.findViewById(R.id.liter)).setText(((TextView)view.findViewById(R.id.liter)).getText().toString());
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
                popupView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupView.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.updatemember(((TextView)popupView.findViewById(R.id.name)).getText().toString(),((TextView)view.findViewById(R.id.name)).getText().toString(),((TextView)popupView.findViewById(R.id.liter)).getText().toString(),((TextView)view.findViewById(R.id.liter)).getText().toString());
                        Toast.makeText(Edit.this, "update", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }
    public void Update()
    {
        listView.setAdapter(db.getAllContactsedit());
    }
}