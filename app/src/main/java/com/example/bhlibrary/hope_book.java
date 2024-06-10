package com.example.bhlibrary;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class hope_book extends AppCompatActivity {
    ImageButton btn_home;
    Button btn_add;
    EditText et_book_input;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    LinearLayout bookListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hope_book);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("HopeBooks");

        btn_home = findViewById(R.id.btn_home);
        et_book_input = findViewById(R.id.et_book_input);
        btn_add = findViewById(R.id.btn_add);
        bookListLayout = findViewById(R.id.book_list_layout);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHopeBook();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(hope_book.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 책 목록 불러오기
        loadBooks();
    }

    public void addHopeBook() {
        String bookTitle = et_book_input.getText().toString().trim();
        String userEmail = mAuth.getCurrentUser().getEmail(); // 현재 로그인한 사용자의 이메일 가져오기

        if (!bookTitle.isEmpty()) {
            String userId = userEmail.replace(".", "_"); // Firebase 경로에 '.'은 사용할 수 없으므로 '_'로 변경
            DatabaseReference userRef = databaseReference.child(userId); // 사용자 이메일을 참조로 사용
            userRef.push().child("title").setValue(bookTitle); // 사용자 이메일 아래에 데이터 추가

            et_book_input.setText(""); // 입력 필드 초기화
        }
    }

    public void loadBooks() {
        String userEmail = mAuth.getCurrentUser().getEmail();
        String userId = userEmail.replace(".", "_");

        DatabaseReference userRef = databaseReference.child(userId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookListLayout.removeAllViews();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String bookTitle = snapshot.child("title").getValue(String.class);
                    String bookId = snapshot.getKey();

                    LinearLayout bookLayout = new LinearLayout(hope_book.this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(0, 0, 0, 16); // 좌우 0px, 상하 16px 마진 추가
                    bookLayout.setLayoutParams(layoutParams);
                    bookLayout.setOrientation(LinearLayout.HORIZONTAL);

                    TextView bookTextView = new TextView(hope_book.this);
                    LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1
                    );
                    bookTextView.setLayoutParams(textViewParams);
                    bookTextView.setText(bookTitle);

                    Button cancelButton = new Button(hope_book.this);
                    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    cancelButton.setLayoutParams(buttonParams);
                    cancelButton.setText("취소");
                    cancelButton.setTag(bookId); // 버튼에 책의 고유 ID를 태그로 저장
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 취소 버튼 클릭 시 해당 책을 Firebase에서 삭제
                            String bookId = (String) v.getTag();
                            userRef.child(bookId).removeValue();
                        }
                    });

                    bookLayout.addView(bookTextView);
                    bookLayout.addView(cancelButton);
                    bookListLayout.addView(bookLayout);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리
            }
        });
    }
}
