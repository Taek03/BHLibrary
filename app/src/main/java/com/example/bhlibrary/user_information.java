package com.example.bhlibrary;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class user_information extends AppCompatActivity {
    ImageButton btn_back;
    Button btn_change;
    TextView tv_user_name, et_ID, et_PW, et_Birth, et_Phone;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        // Firebase 초기화
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://bhlibrary-a058a-default-rtdb.firebaseio.com/").getReference();

        // UI 요소 초기화
        btn_back = findViewById(R.id.btn_back);
        btn_change = findViewById(R.id.btn_change);
        tv_user_name = findViewById(R.id.tv_user_name);
        et_ID = findViewById(R.id.et_ID);
        et_PW = findViewById(R.id.et_PW);
        et_Birth = findViewById(R.id.et_Birth);
        et_Phone = findViewById(R.id.et_Phone);

        // 뒤로가기 버튼 클릭 리스너 설정
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 정보 수정 버튼 클릭 리스너 설정
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_information.this, user_information_c.class);
                startActivity(intent);
            }
        });

        // 현재 사용자 정보 가져오기
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            mDatabase.child("UserAccount").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // 데이터 매핑
                        UserAccount userAccount = dataSnapshot.getValue(UserAccount.class);
                        if (userAccount != null) {
                            tv_user_name.setText(userAccount.getName());
                            et_ID.setText(userAccount.getEmailId());
                            et_PW.setText(userAccount.getPassword());
                            et_Birth.setText(formatBirthDate(userAccount.getBirth()));
                            et_Phone.setText(formatPhoneNumber(userAccount.getPhone()));
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // 에러 처리
                }
            });
        }
    }

    private String formatBirthDate(String birth) {
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = originalFormat.parse(birth);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return birth; // 포맷팅에 실패하면 원래 문자열을 반환
        }
    }

    private String formatPhoneNumber(String phone) {
        if (phone.length() == 11) {
            return phone.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
        } else {
            return phone; // 포맷팅에 실패하면 원래 문자열을 반환
        }
    }
}
