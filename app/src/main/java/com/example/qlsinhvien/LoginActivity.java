package com.example.qlsinhvien;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText email,pass;
    private Button btnLogin,btnRegis,btnRP;
    private FirebaseAuth mAuth;
    public static boolean checkLogin = false;
    private ProgressBar prg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance(); //Khoi tao Firebase
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegis=findViewById(R.id.btnRegister);
        btnRP=findViewById(R.id.btnRestorePassword);
        prg=findViewById(R.id.progressBarLogin);

        prg.setVisibility(View.GONE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
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
        Intent intent = new Intent(LoginActivity.this, RestorePassword.class);
        startActivity(intent);
    }

    private void register() {
        /*Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);*/
        Intent intent = new Intent(LoginActivity.this, PasswordAuth.class);
        startActivity(intent);
    }

    private void login() {
        String emailLogin,passwordLogin;

        emailLogin=email.getText().toString();
        passwordLogin=pass.getText().toString();

//        emailLogin="minhdinh211001@gmail.com";
//        passwordLogin="644466";

        prg.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(emailLogin)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập Email.",Toast.LENGTH_SHORT).show();
            prg.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(passwordLogin)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập mật khẩu.",Toast.LENGTH_SHORT).show();
            prg.setVisibility(View.GONE);
            return;
        }
        mAuth.signInWithEmailAndPassword(emailLogin,passwordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(emailLogin,passwordLogin);
                    //reathenticate
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(user.isEmailVerified()==true) {
                                Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                checkLogin=true;
                            }else if(user.isEmailVerified()==false){
                                Toast.makeText(getApplicationContext(),"Đăng nhập không thành công",Toast.LENGTH_LONG).show();
                                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) { }
                                });
                            }
                        }
                    });
                    prg.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getApplicationContext(),"Đăng nhập không thành công",Toast.LENGTH_SHORT).show();
                    prg.setVisibility(View.GONE);
                }
            }
        });
    }
}
