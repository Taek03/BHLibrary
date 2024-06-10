package com.example.bhlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton btn_more_notices, btn_user, btn_loan, btn_find_library, btn_find_book, btn_recommend, btn_scan, btn_want;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_more_notices = findViewById(R.id.btn_more_notices);
        btn_user = findViewById(R.id.btn_user);
        btn_loan = findViewById(R.id.btn_loan);
        btn_find_library = findViewById(R.id.btn_find_library);
        btn_find_book = findViewById(R.id.btn_find_book);
        btn_recommend = findViewById(R.id.btn_recommend);
        btn_scan = findViewById(R.id.btn_scan);
        btn_want = findViewById(R.id.btn_want);

        btn_more_notices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, announcement.class);
                startActivity(intent);
            }
        });
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, user_information.class);
                startActivity(intent);
            }
        });
        btn_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, loanstatus.class);
                startActivity(intent);
            }
        });
        btn_find_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, library_search.class);
                startActivity(intent);
            }
        });
        btn_find_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, book_search.class);
                startActivity(intent);
            }
        });
        btn_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, recommend.class);
                startActivity(intent);
            }
        });
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, scan.class);
                startActivity(intent);
            }
        });
        btn_want.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, hope_book.class);
                startActivity(intent);
            }
        });
    }
}