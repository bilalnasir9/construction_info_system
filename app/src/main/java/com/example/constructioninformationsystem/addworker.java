package com.example.constructioninformationsystem;

import android.app.AlertDialog;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class addworker extends Fragment {
    TextView tv_date_time;
    EditText  etname, etcontact, etaddress,etdatetime;
    String uid, expertise;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth  auth = FirebaseAuth.getInstance();
    String[] worker_expertise = {
            "Architect",
            "Civil Engineer",
            "Electrical Engineer",
            "Mason",
            "Plumber",
            "Painter",
            "Interior Designer",
            "Carpenter",
            "Others"
    };
    Spinner spinner;
    String type;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addworker, container, false);

        spinner = view.findViewById(R.id.spiner_worker_expertise);
        etname = view.findViewById(R.id.et_add_worker_name);
        etcontact = view.findViewById(R.id.et_add_worker_contact);
        etaddress = view.findViewById(R.id.et_add_worker_address);
        etdatetime=view.findViewById(R.id.et_add_worker_datetime);
        Button btnaddworker = view.findViewById(R.id.btn_add_worker);
        ImageButton btnbackpress=view.findViewById(R.id.btnback_add_worker);
        uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        Date date1 = new Date();
        String format = "dd-MM-yyyy hh:mm:ss"; //In which you need put here
        SimpleDateFormat sdf2 = new SimpleDateFormat(format, Locale.getDefault());
        String gettime = sdf2.format(date1);
        etdatetime.setText(gettime);
        spiner();
        btnbackpress.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new workerinfo()).commit();
        });
        btnaddworker.setOnClickListener(view1 -> {
            if (TextUtils.isEmpty(etname.getText())) {
                etname.setError("Input required");
            }
            if (TextUtils.isEmpty(etcontact.getText())) {
                etcontact.setError("Input required");
            }
            if (TextUtils.isEmpty(etaddress.getText())) {
                etaddress.setError("Input required");
            }else {
                reference.child("users").child(uid).child("project").get().addOnCompleteListener(task -> {
                    try {
                        String res = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getValue()).toString();
                        addworker();
                    } catch (Exception e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Task failed").setMessage("You cannot insert any worker's information until your project is created")
                                .setNegativeButton("OK", (dialogInterface, im) -> {
                                });
                        builder.show();
                    }
                });
            }


        });
        return view;
    }

    private void addworker() {
        String date, name, contact, address;
        date = etdatetime.getText().toString();
        name = etname.getText().toString();
        address = etaddress.getText().toString();
        contact = etcontact.getText().toString();
        reference.child("users").child(uid).child("project").child("workers").child(date)
                .child(type).child("name").setValue(name);
        reference.child("users").child(uid).child("project").child("workers").child(date)
                .child(type).child("address").setValue(address);
        reference.child("users").child(uid).child("project").child("workers").child(date)
                .child(type).child("contact").setValue(contact);
        Toast.makeText(getActivity(), "Successful created", Toast.LENGTH_SHORT).show();

    }
    public void spiner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, worker_expertise);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = worker_expertise[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


}