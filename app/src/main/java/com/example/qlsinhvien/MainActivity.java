package com.example.qlsinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnSubjects,btnAuthor,btnExit,btnLogout;
    int counter = 0;
    private ImageButton btnProfile;
    LoginActivity check = new LoginActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubjects = findViewById(R.id.buttonSubjects);
        btnExit = findViewById(R.id.buttonExit);
        btnAuthor = findViewById(R.id.ButtonAuthor);
        btnLogout=findViewById(R.id.buttonLogout);
        btnProfile=findViewById(R.id.imageButtonProfile);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.checkLogin==true) {
                    Intent intent = new Intent(MainActivity.this,ProfileInformation.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Vui lòng đăng nhập",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        btnAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAuthor();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogExit();
            }
        });
        btnSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check.checkLogin==true) {
                    Intent intent = new Intent(MainActivity.this,ActivitySubjects.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Vui lòng đăng nhập",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                check.checkLogin=false;
            }
        });
    }
    //Dialog Tác giả
    private void DialogAuthor() {
        //Tạo đối tượng cửa sổ dialog
        Dialog dialog  =  new Dialog(this);

        //Nạp layout vào
        dialog.setContentView(R.layout.dialoginformation);
        dialog.show();
    }
    //Dialog Thoát
    private void DialogExit() {

        //Tạo đối tượng cửa sổ dialog
        Dialog dialog  =  new Dialog(this);

        //Nạp layout vào
        dialog.setContentView(R.layout.dialogexit);

        //Click No mới thoát, click ngoài ko thoát
        dialog.setCanceledOnTouchOutside(false);

        //Ánh xạ
        Button btnYes = dialog.findViewById(R.id.buttonYes);
        Button btnNo = dialog.findViewById(R.id.buttonNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Khởi tạo lại activity main
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                // Tạo sự kiện kết thúc app
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(startMain);
            }
        });
        //Nếu no thì đóng dialog
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //show dialog lên activity
        dialog.show();
    }

    //Nhấn nút back trên android 2 lần trên activity main thì thoát ứng dụng
    @Override
    public void onBackPressed() {
        counter++;
        if (counter >= 1){
            // Tạo sự kiện kết thúc app
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startActivity(startMain);
        }
    }
}