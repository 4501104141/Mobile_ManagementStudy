package com.example.qlsinhvien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PasswordAuth extends AppCompatActivity {
    private Button btnAuth;
    private EditText textAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authlecter);

        btnAuth = findViewById(R.id.buttonAuth);
        textAuth = findViewById(R.id.editTextNumberSigned);

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordAuth();
            }
        });
    }

    private void passwordAuth() {
        if(textAuth.getText().toString().equals("4422268833")){
            Intent intent = new Intent(PasswordAuth.this,RegisterActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(), "Sai", Toast.LENGTH_SHORT).show();
        }
    }
}
