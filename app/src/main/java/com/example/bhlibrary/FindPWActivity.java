package com.example.bhlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FindPWActivity extends AppCompatActivity {

    private EditText et_emailID, et_Birth, et_Phone;
    private Button btn_FindPW;
    private ImageButton btn_Back1;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);

        et_emailID=findViewById(R.id.find_mail);
        et_Birth = findViewById(R.id.Find_Birth);
        et_Phone = findViewById(R.id.Find_Phone);
        btn_FindPW = findViewById(R.id.btn_findpw);
        btn_Back1 = findViewById(R.id.back);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserAccount");

        btn_FindPW.setOnClickListener(v -> {
            String ID = et_emailID.getText().toString().trim();
            String birth = et_Birth.getText().toString().trim();
            String phone = et_Phone.getText().toString().trim();

            if (TextUtils.isEmpty(ID) || TextUtils.isEmpty(birth) || TextUtils.isEmpty(phone)) {
                Toast.makeText(FindPWActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                findEmail(ID, birth, phone);
            }
        });
        btn_Back1.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(FindPWActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findEmail(String ID, String birth, String phone) {
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean found = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserAccount account = snapshot.getValue(UserAccount.class);
                    if (account != null && account.getEmailId().equals(ID) &&
                            account.getBirth().equals(birth) && account.getPhone().equals(phone)) {
                        showPWDialog(account.getPassword());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Toast.makeText(FindPWActivity.this, "비밀번호를 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FindPWActivity.this, "데이터베이스 에러: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showPWDialog(String password) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FindPWActivity.this);
        builder.setTitle("비밀번호 찾기");
        builder.setMessage("비밀번호: " + password);
        builder.setPositiveButton("확인", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}