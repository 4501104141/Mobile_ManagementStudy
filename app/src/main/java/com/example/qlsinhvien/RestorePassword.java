package com.example.qlsinhvien;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestorePassword extends AppCompatActivity {
    private EditText email;
    private FirebaseAuth mAuth;
    private TextView btnReturn;
    private Button btnRP;
    private ProgressBar prg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restorepassword);
        mAuth=FirebaseAuth.getInstance(); //Khoi tao Firebase
        email=findViewById(R.id.email);
        btnRP=findViewById(R.id.btnResetPassword);
        btnReturn=findViewById(R.id.buttonReturn);
        prg=findViewById(R.id.progressBarRestore);

        prg.setVisibility(View.GONE);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestorePassword.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btnRP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restorepassword();
            }
        });
    }

    private void restorepassword() {

        prg.setVisibility(View.VISIBLE);
        String emailLogin;
        emailLogin=email.getText().toString();
        if(TextUtils.isEmpty(emailLogin)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập Email.",Toast.LENGTH_SHORT).show();
            prg.setVisibility(View.GONE);
            return;
        }
        mAuth.sendPasswordResetEmail(emailLogin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Đã gửi xác thực đến email của bạn",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RestorePassword.this,LoginActivity.class);
                    startActivity(intent);
                    prg.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getApplicationContext(),"Email của bạn không tồn tại",Toast.LENGTH_LONG).show();
                    prg.setVisibility(View.GONE);
                }
            }
        });
    }
}
