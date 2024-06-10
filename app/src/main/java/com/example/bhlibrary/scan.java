package com.example.bhlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;

public class scan extends AppCompatActivity {
    private ImageButton btn_back;
    private Button btn_scan;
    private TextView tv_scan_result;
    private CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        btn_back = findViewById(R.id.btn_back);
        btn_scan = findViewById(R.id.btn_scan);
        tv_scan_result = findViewById(R.id.tv_scan_result);
        cameraView = findViewById(R.id.camera_view);

        // 뒤로 가기 버튼 클릭 리스너 설정
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 스캔 버튼 클릭 리스너 설정
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera(); // 카메라 시작 메서드 호출
            }
        });
    }

    private void startCamera() {
        // 카메라 초기화 및 프리뷰 시작
        initCamera();
    }

    private void initCamera() {
        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onCameraOpened(CameraOptions options) {
                // 부모 클래스의 onCameraOpened 메서드 호출
                super.onCameraOpened(options);
                Log.d("CameraX", "Camera opened");
            }

            @Override
            public void onCameraClosed() {
                // 카메라가 닫힐 때 호출되는 메서드
                super.onCameraClosed();
                Log.d("CameraX", "Camera closed");
            }
        });
        cameraView.open(); // 카메라 열기
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.destroy(); // 카메라 리소스 해제
    }
}
