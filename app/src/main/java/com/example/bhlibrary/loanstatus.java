package com.example.bhlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class loanstatus extends AppCompatActivity {
    ImageButton btn_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loanstatus);

        btn_home = findViewById(R.id.btn_home);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loanstatus.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}