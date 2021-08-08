package com.example.constructioninformationsystem;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class User_editWorker extends Fragment {

    EditText etexpertise,etname,etcontact,etaddress;
    String uid,expertise,getdatetime;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_editworker, container, false);
       etexpertise=view.findViewById(R.id.et_edit_worker_expertise);
        etname=view.findViewById(R.id.et_edit_worker_name);
        etcontact=view.findViewById(R.id.et_edit_worker_contact);
        etaddress=view.findViewById(R.id.et_edit_worker_address);
        uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        Bundle bundle = this.getArguments();
        expertise = Objects.requireNonNull(bundle).getString("expertise");
        getdatetime = Objects.requireNonNull(bundle).getString("datetime");
        Button btneditworker = view.findViewById(R.id.btn_edit_worker);
        ImageButton btnback=view.findViewById(R.id.btnback_edit_worker);
        etexpertise.setEnabled(false);
        etexpertise.setText(expertise);
        btneditworker.setOnClickListener(view1 -> {
            if (TextUtils.isEmpty(etname.getText().toString())) {
                etname.setError("Input required");
            }
            if (TextUtils.isEmpty(etcontact.getText().toString())) {
                etcontact.setError("Input required");
            }
            if (TextUtils.isEmpty(etaddress.getText().toString())) {
                etaddress.setError("Input required");
            }else {
                reference.child("users").child(uid).child("project").child("workers").child(getdatetime).child(expertise).child("name")
                        .setValue(etname.getText().toString());
                reference.child("users").child(uid).child("project").child("workers").child(getdatetime).child(expertise).child("contact")
                        .setValue(etcontact.getText().toString());
                reference.child("users").child(uid).child("project").child("workers").child(getdatetime).child(expertise).child("address")
                        .setValue(etaddress.getText().toString());
                Toast.makeText(getActivity(), "Successfully Updated ", Toast.LENGTH_SHORT).show();
            }
        });
        btnback.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main,new workerinfo()).commit();
        });
        return view;
    }
}