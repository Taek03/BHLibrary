package com.example.bhlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ImageButton btn_more_notices, btn_user, btn_loan, btn_find_library, btn_find_book, btn_recommend, btn_scan, btn_want, btn_logout;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase 인증 객체 가져오기
        mAuth = FirebaseAuth.getInstance();

        btn_more_notices = findViewById(R.id.btn_more_notices);
        btn_user = findViewById(R.id.btn_user);
        btn_loan = findViewById(R.id.btn_loan);
        btn_find_library = findViewById(R.id.btn_find_library);
        btn_find_book = findViewById(R.id.btn_find_book);
        btn_recommend = findViewById(R.id.btn_recommend);
        btn_scan = findViewById(R.id.btn_scan);
        btn_want = findViewById(R.id.btn_want);
        btn_logout = findViewById(R.id.btn_logout);

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

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그아웃
                mAuth.signOut();
                // 로그인 화면으로 이동
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                // 현재 액티비티 종료
                finish();
            }
        });
    }
}
