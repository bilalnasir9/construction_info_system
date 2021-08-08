package com.example.constructioninformationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class user_registration extends AppCompatActivity {
    Button button_userregister;
    EditText editText_useremail, editText_userpassword, editText_username;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String email, password, name;
    ProgressDialog progressDialog;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        button_userregister = findViewById(R.id.user_register_button);
        editText_useremail = findViewById(R.id.user_register_email);
        editText_userpassword = findViewById(R.id.user_register_password);
        editText_username = findViewById(R.id.user_register_name);
        button_userregister.setOnClickListener(view -> {
            email = editText_useremail.getText().toString();
            name = editText_username.getText().toString();
            password = editText_userpassword.getText().toString();
            if (TextUtils.isEmpty(email)) {
                editText_useremail.setError("Email cannot be empty");
            }
            if (TextUtils.isEmpty(name)) {
                editText_username.setError("Name cannot be empty");
            }
            if (TextUtils.isEmpty(password)) {
                editText_userpassword.setError("Password cannot be empty");
            }if (editText_userpassword.getText().length()<=7){
                editText_userpassword.setError("Password must be at least 8 characters");
            }
           else {
                progressDialog.show();
               auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                   @Override
                   public void onComplete(@NonNull @NotNull Task<SignInMethodQueryResult> task) {

                     try{
                         Boolean checkemail= task.getResult().getSignInMethods().isEmpty();
                  if (!checkemail){
                      progressDialog.dismiss();
                      editText_useremail.setError("This email is already exist");
                     }
                  else {
                      auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((Task<AuthResult> task2) ->
                      {
                          if (task2.isSuccessful()) {
                              reference.child("users").child(Objects.requireNonNull(auth.getUid())).child("name").setValue(name);
                              reference.child("users").child(Objects.requireNonNull(auth.getUid())).child("email").setValue(email);
                              progressDialog.dismiss();
                  Toast.makeText(user_registration.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(user_registration.this,user_panel.class));
                          }
                          else {
                              progressDialog.dismiss();
                              editText_useremail.setError("Email format is invalid");
                          }
                      });



                  }
                   }
                     catch (Exception e){
                         progressDialog.dismiss();
                         editText_useremail.setError("Email format is invalid");
                         Toast.makeText(user_registration.this, "Error found"+e.toString(), Toast.LENGTH_SHORT).show();
                     }

                   }
               });


           }

        });
    }

}
