package com.example.bahikhata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Entry extends AppCompatActivity {

    EditText name,default_liter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        name = findViewById(R.id.name);
        default_liter = findViewById(R.id.default_liter);
        DatabaseHandler db=new DatabaseHandler(Entry.this);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = name.getText().toString();
                String s2 = default_liter.getText().toString();
                if(s1 != null && !s1.isEmpty())
                {
                    if(s2 != null && !s2.isEmpty()){
                        db.addContact(s1,s2);
                        name.setText("");
                        default_liter.setText(""); }
                    else
                        default_liter.setError("You need to enter a default liter");
                }
                else
                    name.setError("You need to enter a name");
            }
        });
        findViewById(R.id.del_member).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Entry.this,del_entry.class));
            }
        });

    }
}
