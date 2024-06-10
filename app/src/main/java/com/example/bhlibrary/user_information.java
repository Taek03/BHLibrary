package com.example.bhlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class user_information extends AppCompatActivity {
    ImageButton btn_back;
    Button btn_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        btn_back = findViewById(R.id.btn_back);
        btn_change = findViewById(R.id.btn_change);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_information.this, user_information_c.class);
                startActivity(intent);
            }
        });
    }
}