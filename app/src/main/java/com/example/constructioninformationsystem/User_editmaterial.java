package com.example.constructioninformationsystem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class User_editmaterial extends Fragment {

    EditText etquantity;
    String type, brand, price, uid, date;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_editmaterial, container, false);
        uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        Bundle bundle = this.getArguments();
        date = Objects.requireNonNull(bundle).getString("date");
        type = Objects.requireNonNull(bundle).getString("type");
        etquantity = view.findViewById(R.id.et_edit_material_quantity);
        Button btneditmaterial = view.findViewById(R.id.btn_edit_material);
        ImageButton btnback=view.findViewById(R.id.btnbackedit);
        btneditmaterial.setOnClickListener(view1 -> {
            if (TextUtils.isEmpty(etquantity.getText().toString())) {
                etquantity.setError("Input required");
            } else {
                reference.child("users").child(uid).child("project").child("material usage").child(date).child(type)
                        .child("quantity used").setValue(etquantity.getText().toString());
                Toast.makeText(getActivity(), "Successfully Updated ", Toast.LENGTH_SHORT).show();
            }
        });
        btnback.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main,new user_material_usage()).commit();
        });
        return view;
    }
}