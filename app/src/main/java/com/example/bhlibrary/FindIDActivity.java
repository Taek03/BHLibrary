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

public class FindIDActivity extends AppCompatActivity {

    private EditText et_Name, et_Birth, et_Phone;
    private Button btn_FindID;
    private ImageButton btn_Back1;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_idactivity);

        et_Name = findViewById(R.id.find_email);
        et_Birth = findViewById(R.id.Find_Birth);
        et_Phone = findViewById(R.id.Find_Phone);
        btn_FindID = findViewById(R.id.btn_findid);
        btn_Back1 = findViewById(R.id.back);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserAccount");

        btn_FindID.setOnClickListener(v -> {
            String name = et_Name.getText().toString().trim();
            String birth = et_Birth.getText().toString().trim();
            String phone = et_Phone.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(birth) || TextUtils.isEmpty(phone)) {
                Toast.makeText(FindIDActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                findEmail(name, birth, phone);
            }
        });
        btn_Back1.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(FindIDActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findEmail(String name, String birth, String phone) {
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean found = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserAccount account = snapshot.getValue(UserAccount.class);
                    if (account != null && account.getName().equals(name) &&
                            account.getBirth().equals(birth) && account.getPhone().equals(phone)) {
                        showEmailDialog(account.getEmailId());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Toast.makeText(FindIDActivity.this, "아이디를 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FindIDActivity.this, "데이터베이스 에러: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showEmailDialog(String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FindIDActivity.this);
        builder.setTitle("아이디 찾기");
        builder.setMessage("아이디: " + email);
        builder.setPositiveButton("확인", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}