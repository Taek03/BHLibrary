package com.example.bhlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText et_ID, et_PW;
    Button mBtnLogin, mBtnRegister, btn_FindId, btn_FindPw;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText mEtId, mEtpwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mEtId = findViewById(R.id.et_ID);
        mEtpwd = findViewById(R.id.et_PW);
        mBtnRegister = findViewById(R.id.btn_Regist);
        mBtnLogin = findViewById(R.id.btn_Login);
        btn_FindId= findViewById(R.id.btn_FindId);
        btn_FindPw=findViewById(R.id.btn_FindPw);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = mEtId.getText().toString();
                String strPwd = mEtpwd.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btn_FindId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(LoginActivity.this, FindIDActivity.class);
                startActivity(intent1);
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, terms.class);
                startActivity(intent);
            }
        });
        btn_FindPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(LoginActivity.this, FindPWActivity.class);
                startActivity(intent2);
            }
        });
    }

}