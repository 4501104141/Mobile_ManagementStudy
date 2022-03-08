package com.example.qlsinhvien;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileInformation extends AppCompatActivity {
    private ImageView imageRepresent;
    private EditText nameEdit;
    private TextView emailEdit;
    private Button btnUpdate;
    private TextView btnReturn;
    private EditText passEdit;
    FirebaseUser mAuth=FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Declare equals to
        //mAuth=FirebaseAuth.getInstance();
        imageRepresent=findViewById(R.id.imageViewLecturers);
        nameEdit=findViewById(R.id.editTextNameLecturers);
        emailEdit=findViewById(R.id.editTextEmail);
        btnUpdate=findViewById(R.id.buttonUpdateProfite);
        btnReturn=findViewById(R.id.btnReturn);
        passEdit=findViewById(R.id.editTextPASS);

        //Set view editText.
        emailEdit.setText(mAuth.getEmail());
        nameEdit.setText(mAuth.getDisplayName());


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileInformation.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUpdate();
            }
        });
    }

    private void DialogUpdate() {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialogupdateprofile);

        dialog.setCanceledOnTouchOutside(false);

        Button btnYes = dialog.findViewById(R.id.buttonYesUpdateProfile);
        Button btnNo = dialog.findViewById(R.id.buttonNoUpdateProfile);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passEdit.length()>=5) {
                    mAuth.updatePassword(passEdit.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) { }
                    });
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(nameEdit.getText().toString()).build();
                    mAuth.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.cancel();
                }else if(passEdit.length()>=1&&passEdit.length()<=4) {
                    Toast.makeText(getApplicationContext(),"Vui lòng sử dụng mật khẩu nhiều hơn 5 kí tự",Toast.LENGTH_LONG).show();
                }else {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(nameEdit.getText().toString()).build();
                    mAuth.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.cancel();
                }
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
