package com.example.constructioninformationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class admin_login extends AppCompatActivity {
String email,password;
FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        setContentView(R.layout.activity_admin_login);
        Button btnadminlogin=findViewById(R.id.admin_login_button);
        EditText etemail=findViewById(R.id.admin_login_email);
        EditText etpassword=findViewById(R.id.admin_login_password);
        email=getResources().getString(R.string.admin_emailvalue);
        password=getResources().getString(R.string.admin_passwordvalue);
        btnadminlogin.setOnClickListener(view -> {
            progressDialog.show();
            if (email.equals(etemail.getText().toString())&&password.equals(etpassword.getText().toString())){
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                  if (task.isSuccessful()){
                      progressDialog.dismiss();
                      startActivity(new Intent(admin_login.this,admin_panel.class));
                  }
                  if (task.isCanceled()){
                      progressDialog.dismiss();
                      Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                  }
                });
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(this, "invalid id or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}