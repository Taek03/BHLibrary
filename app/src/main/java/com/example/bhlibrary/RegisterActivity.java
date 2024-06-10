package com.example.bhlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText mEtID, mEtpwd, eName, eBirth, ePhone;
    private Button mBtnRegister;

    ImageButton btn_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mEtID = findViewById(R.id.et_ID);
        mEtpwd = findViewById(R.id.et_PW);
        mBtnRegister = findViewById(R.id.btn_register);
        btn_Back = findViewById(R.id.btn_back);
        eName = findViewById(R.id.et_Name);
        eBirth = findViewById(R.id.et_Birth);
        ePhone = findViewById(R.id.et_Phone);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = mEtID.getText().toString();
                String strPwd = mEtpwd.getText().toString();
                String strName = eName.getText().toString();
                String strBirth = eBirth.getText().toString();
                String strPhone = ePhone.getText().toString();

                if (TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPwd) || TextUtils.isEmpty(strName) || TextUtils.isEmpty(strBirth) || TextUtils.isEmpty(strPhone)) {
                    Toast.makeText(RegisterActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                UserAccount account = new UserAccount();
                                account.setIdToken(firebaseUser.getUid());
                                account.setEmailId(firebaseUser.getEmail());
                                account.setPassword(strPwd);
                                account.setName(strName);
                                account.setBirth(strBirth);
                                account.setPhone(strPhone);

                                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent1);

                                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                                Toast.makeText(RegisterActivity.this, "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}