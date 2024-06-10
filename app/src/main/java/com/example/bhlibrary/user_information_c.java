package com.example.bhlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_information_c extends AppCompatActivity {
    ImageButton btn_back;
    Button btn_finish;
    EditText et_PW_c, et_PW_c2, et_Birth_c, et_Phone_c;
    TextView tv_user_name_c, et_ID_c;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information_c);

        // Firebase 초기화
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://bhlibrary-a058a-default-rtdb.firebaseio.com/").getReference();

        // UI 요소 초기화
        btn_back = findViewById(R.id.btn_back);
        btn_finish = findViewById(R.id.btn_finish);
        et_PW_c = findViewById(R.id.et_PW_c);
        et_PW_c2 = findViewById(R.id.et_PW_c2); // 새 비밀번호 확인 EditText 추가
        et_Birth_c = findViewById(R.id.et_Birth_c);
        et_Phone_c = findViewById(R.id.et_Phone_c);
        tv_user_name_c = findViewById(R.id.tv_user_name_c);
        et_ID_c = findViewById(R.id.et_ID_c);

        // 뒤로가기 버튼 클릭 리스너 설정
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 수정 완료 버튼 클릭 리스너 설정
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserInformation();
            }
        });

        // 현재 사용자 정보 가져오기
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            mDatabase.child("UserAccount").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // 데이터 매핑
                        UserAccount userAccount = dataSnapshot.getValue(UserAccount.class);
                        if (userAccount != null) {
                            tv_user_name_c.setText(userAccount.getName());
                            et_ID_c.setText(userAccount.getEmailId());
                            et_PW_c.setText(userAccount.getPassword());
                            et_Birth_c.setText(userAccount.getBirth());
                            et_Phone_c.setText(userAccount.getPhone());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // 에러 처리
                }
            });
        }
    }

    // 사용자 정보 업데이트 메서드
    private void updateUserInformation() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            String currentPassword = et_PW_c.getText().toString().trim();
            String newPassword = et_PW_c2.getText().toString().trim(); // 새 비밀번호 확인 추가
            String newBirth = et_Birth_c.getText().toString().trim();
            String newPhone = et_Phone_c.getText().toString().trim();

            if (currentPassword.isEmpty()) {
                et_PW_c.setError("기존 비밀번호를 입력하세요.");
                et_PW_c.requestFocus();
                return;
            }

            if (newPassword.isEmpty()) {
                et_PW_c2.setError("새 비밀번호를 입력하세요.");
                et_PW_c2.requestFocus();
                return;
            }

            if (!newPassword.equals(et_PW_c2.getText().toString())) {
                et_PW_c2.setError("새 비밀번호가 일치하지 않습니다.");
                et_PW_c2.requestFocus();
                return;
            }

            // Firebase에서 현재 비밀번호 확인 후 업데이트
            mAuth.signInWithEmailAndPassword(currentUser.getEmail(), currentPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // 현재 비밀번호 인증 성공 시 업데이트
                            currentUser.updatePassword(newPassword).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    // Firebase에 새로운 정보 업데이트
                                    mDatabase.child("UserAccount").child(uid).child("password").setValue(newPassword);
                                    mDatabase.child("UserAccount").child(uid).child("birth").setValue(newBirth);
                                    mDatabase.child("UserAccount").child(uid).child("phone").setValue(newPhone);

                                    Toast.makeText(user_information_c.this, "정보가 업데이트되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish(); // 액티비티 닫기
                                } else {
                                    Toast.makeText(user_information_c.this, "비밀번호 업데이트 실패", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(user_information_c.this, "기존 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
