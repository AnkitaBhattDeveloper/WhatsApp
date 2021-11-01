package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.whatsapp.databinding.ActivitySignUpBinding;
import com.example.whatsapp.databinding.ActivitySigninBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {
private Button btncreateaccount,btnSignin;
ActivitySigninBinding binding;
ProgressDialog progressDialog;
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        btnSignin = findViewById(R.id.btnSignIn);
        btncreateaccount = findViewById(R.id.btncreateaccount);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(SigninActivity.this);
        progressDialog.setTitle("Logging");
        progressDialog.setMessage("Logging to your account");

btnSignin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        progressDialog.show();
        auth.signInWithEmailAndPassword(binding.eteMail.getText().toString(),binding.etPassword.getText().toString())
                 .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         progressDialog.dismiss();
                         if (task.isSuccessful()){
                             Intent intent = new Intent(SigninActivity.this,MainActivity.class);
                             startActivity(intent);
                         }
                         else{
                             Toast.makeText(SigninActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                         }

                     }
                 });

    }
});
if (auth.getCurrentUser() != null)
{
    Intent intent = new Intent(SigninActivity.this,MainActivity.class);
    startActivity(intent);
}



      binding.btncreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}