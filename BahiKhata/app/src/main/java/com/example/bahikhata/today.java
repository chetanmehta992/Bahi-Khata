package com.example.bahikhata;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

public class today extends AppCompatActivity {
    String year,month,day;
    public SwipeMenuListView listView;
    private DatabaseHandler2 db1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        year = getIntent().getStringExtra("year");
        month = getIntent().getStringExtra("month");
        day = getIntent().getStringExtra("day");

        db1=new DatabaseHandler2(this,day,month,year);
        listView =  findViewById(R.id.todaylist);
        Update();

    }

    public void Update() {
        listView.setAdapter(db1.showtoday());
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem editItem = new SwipeMenuItem(getApplicationContext());
                editItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0x20, 0xC2)));
                editItem.setWidth(150);
                editItem.setTitleColor(Color.WHITE);
                editItem.setIcon(R.drawable.ic_edit);
                menu.addMenuItem(editItem);

            }
        };

        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = inflater.inflate(R.layout.edit_today_entry, null);
                        int width = LinearLayout.LayoutParams.MATCH_PARENT;
                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        boolean focusable = true;
                        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                        popupWindow.showAtLocation(listView, Gravity.CENTER, 0, 0);
                        ((TextView)popupView.findViewById(R.id.nameview)).setText(((TextView)listView.getChildAt(position).findViewById(R.id.name)).getText().toString());
                        ((TextView)popupView.findViewById(R.id.todayentry)).setText(((TextView)listView.getChildAt(position).findViewById(R.id.liter)).getText().toString());
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
                                db1.addContact(((TextView)listView.getChildAt(position).findViewById(R.id.name)).getText().toString(),".5");
                                popupWindow.dismiss();
                            }
                        });popupView.findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db1.addContact(((TextView)listView.getChildAt(position).findViewById(R.id.name)).getText().toString(),"1");
                            popupWindow.dismiss();
                        }
                    });popupView.findViewById(R.id.onehalf).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db1.addContact(((TextView)listView.getChildAt(position).findViewById(R.id.name)).getText().toString(),"1.5");
                            popupWindow.dismiss();
                        }
                    });popupView.findViewById(R.id.two).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db1.addContact(((TextView)listView.getChildAt(position).findViewById(R.id.name)).getText().toString(),"2");
                            popupWindow.dismiss();
                        }
                    });
                        popupView.findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String data = ((EditText)popupView.findViewById(R.id.todayentry)).getText().toString();
                                if(data!="" && data!=null) {
                                    db1.addContact(((TextView)listView.getChildAt(position).findViewById(R.id.name)).getText().toString(), data);
                                    popupWindow.dismiss();
                                }else{
                                    Toast.makeText(today.this, "please enter today entry", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        popupView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                db1.deleteContact(((TextView)listView.getChildAt(position).findViewById(R.id.name)).getText().toString());
                                popupWindow.dismiss();
                            }
                        });


                        break;
                }
                return false;
            }
        });
    }
}