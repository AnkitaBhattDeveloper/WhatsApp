package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp.databinding.ActivitySignUpBinding;
import com.example.whatsapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {
private TextView signintext ;
EditText etUserName,eteMail,etPassword;
Button btnSignup;


ActivitySignUpBinding binding;
private FirebaseAuth auth;
private FirebaseDatabase database;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // getSupportActionBar().hide();
        signintext = findViewById(R.id.signintext);
        etUserName=findViewById(R.id.etUserName);
        eteMail=findViewById(R.id.eteMail);
        etPassword=findViewById(R.id.etPassword);
        btnSignup=findViewById(R.id.btnSignup);

          auth = FirebaseAuth.getInstance();
          database=FirebaseDatabase.getInstance();
progressDialog = new ProgressDialog(SignUpActivity.this);
progressDialog.setTitle("Creating Account");
progressDialog.setMessage("We're creating your acount");




        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                auth.createUserWithEmailAndPassword
                        (binding.eteMail.getText().toString(),binding.etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                 progressDialog.dismiss();
                                if (task.isSuccessful())
                                {
                                    User user = new User(binding.etUserName.getText().toString(),
                                            binding.eteMail.getText().toString(),binding.etPassword.getText().toString());
                                    String id = task.getResult().getUser().getUid();
                                    database.getReference("Users").child(id).setValue(user);

                                    Toast.makeText(SignUpActivity.this,"Account created Successfully",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

       binding.signintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this,SigninActivity.class);
                startActivity(intent);
            }
        });

    }


}