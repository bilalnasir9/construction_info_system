package com.example.constructioninformationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button button_userlogin;
    EditText useremail, userpassword;
    TextView textView_userregistration, textView_adminlogin;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String email, password;
    ProgressDialog progressDialog;
    Boolean network = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        userstatus();
        button_userlogin = findViewById(R.id.user_login_button);
        useremail = findViewById(R.id.user_login_email);
        userpassword = findViewById(R.id.user_login_password);
        textView_userregistration = findViewById(R.id.tv_registerclick);
        textView_adminlogin = findViewById(R.id.tv_adminloginclick);
        textView_userregistration.setOnClickListener(view -> {
            startActivity(new Intent(this, user_registration.class));
        });
        textView_adminlogin.setOnClickListener(view -> {
            startActivity(new Intent(this, admin_login.class));
        });

        button_userlogin.setOnClickListener(view -> {
            email = useremail.getText().toString();
            password = userpassword.getText().toString();
            if (TextUtils.isEmpty(email)) {
                useremail.setError("Email cannot be empty");

            } else if (TextUtils.isEmpty(password)) {
                userpassword.setError("Password cannot be empty");

            } else if (userpassword.length() <= 7) {
                userpassword.setError("Password must be at least 8 characters ");

            } else {
                progressDialog.show();
                try {
                    auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                        startActivity(new Intent(MainActivity.this, user_panel.class));
                        progressDialog.dismiss();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(MainActivity.this, "Email or Password is invalid", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Error found", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }
    public void isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {
            network = true;
        }
    }
    public void userstatus() {
        isNetworkAvailable(this);
        if (!network) {
            @SuppressLint("UseCompatLoadingForDrawables") AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Network error!")
                    .setIcon(getDrawable(R.drawable.ic_baseline_networkcheck)).setMessage("please check your internet connection");
            builder.setNegativeButton("OK", (dialogInterface, i) -> {
            });
            builder.show();
        }
    }

}