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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText email,pass;
    private Button btnRegis;
    private TextView btnReturn;
    private FirebaseAuth mAuth;
    private ProgressBar prg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance(); //Khoi tao Firebase

        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        btnRegis=findViewById(R.id.btnRegister);
        btnReturn=findViewById(R.id.buttonReturn);
        prg=findViewById(R.id.progressBarRegister);

        prg.setVisibility(View.GONE);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void register() {
        String emailLogin,passwordLogin;
        emailLogin=email.getText().toString();
        passwordLogin=pass.getText().toString();

        prg.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(emailLogin)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập Email.",Toast.LENGTH_LONG).show();
            prg.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(passwordLogin)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập mật khẩu.",Toast.LENGTH_LONG).show();
            prg.setVisibility(View.GONE);
            return;
        }
        if(passwordLogin.length()<5) {
            Toast.makeText(getApplicationContext(),"Sử dụng mật khẩu có 5 kí tự trở lên để đăng kí",Toast.LENGTH_LONG).show();
            prg.setVisibility(View.GONE);
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailLogin,passwordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Đã gửi email xác thực đến bạn.",Toast.LENGTH_LONG).show();

                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) { }
                        }
                    });
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);

                    prg.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getApplicationContext(),"Tạo không thành công",Toast.LENGTH_SHORT).show();
                    prg.setVisibility(View.GONE);
                }
            }
        });

    }
}
