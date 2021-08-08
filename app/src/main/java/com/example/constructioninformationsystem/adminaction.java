package com.example.constructioninformationsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class adminaction extends Fragment {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    String userid;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adminaction, container, false);
        Button btncreateproject = view.findViewById(R.id.btncreateproject_layout);
        Button btndeleteuser = view.findViewById(R.id.btndeleteuser);
        Button btndeleteproject = view.findViewById(R.id.btndeleteproject);
        ImageButton btnbackpressed = view.findViewById(R.id.btnback);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");
        Bundle bundle = this.getArguments();
        userid = Objects.requireNonNull(bundle).getString("id");
        btncreateproject.setOnClickListener(view1 -> {
            createproject();
        });
        btndeleteuser.setOnClickListener(view1 -> {
            deleteuser();
        });
        btnbackpressed.setOnClickListener(view1 -> {
            onbackclicked();
        });

        btndeleteproject.setOnClickListener(view1 -> {
            deleteproject();
        });
        

        return view;

    }

    private void onbackclicked() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new admin_view_user()).commit();

    }

    public void createproject() {
        reference.child("users").child(userid).child("project").get().addOnCompleteListener(task -> {
            try {
                String check = task.getResult().getValue().toString();
                Toast.makeText(getActivity(), "project already created", Toast.LENGTH_SHORT).show();


            } catch (NullPointerException exception) {
                startcreate();

            }
        });
    }

    public void deleteuser() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity()).setTitle("Delete User").setMessage("All data of this user will be deleted permanently\nAre you sure to delete?");
        builder.setPositiveButton("Delete",(dialogInterface, i) -> {
            reference.child("users").child(userid).removeValue();
            Toast.makeText(getActivity(), "Deleted successfully", Toast.LENGTH_SHORT).show();
        }).setNegativeButton("Cancel",(dialogInterface, i) -> {

        });
        builder.show();




    }
    private void deleteproject() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity()).setTitle("Delete Project").setMessage("All project detail will be deleted permanently\nAre you sure to delete?");
        builder.setPositiveButton("Delete",(dialogInterface, i) -> {
            reference.child("users").child(userid).child("project").removeValue();
            reference.child("contractor").child("projects").child(userid).removeValue();
            Toast.makeText(getActivity(), "Deleted successfully", Toast.LENGTH_SHORT).show();
        }).setNegativeButton("Cancel",(dialogInterface, i) -> {

        });
        builder.show();
    }
    public void startcreate() {
        create_project createProject = new create_project();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Bundle b = new Bundle();
        b.putString("id", userid);
        createProject.setArguments(b);
        assert activity != null;
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, createProject).commit();

    }

}