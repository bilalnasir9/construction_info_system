package com.example.constructioninformationsystem;

import android.app.AlertDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class addmaterial extends Fragment {
    TextView tvdatepick, tvprice, tvbrand, tvpricedetail;
    EditText etquantity;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
    final Calendar myCalendar = Calendar.getInstance();
    String uid;
    String[] material_type = {
            "cement",
            "sand",
            "steel",
            "concrete",
            "bricks",
            "wood"
    };
    Boolean check;
    Spinner spinner;
    String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addmaterial, container, false);

        spinner = view.findViewById(R.id.spiner_material_type);
        tvdatepick = view.findViewById(R.id.tvdatepick);
        tvbrand = view.findViewById(R.id.tv_newmaterial_brand);
        tvprice = view.findViewById(R.id.tv_newmaterial_price);
        tvpricedetail = view.findViewById(R.id.tv_pricedetail);
        etquantity = view.findViewById(R.id.et_newmaterial_quantity);
        Button btncreatematerial = view.findViewById(R.id.btn_create_new_material);
        uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        Date date1 = new Date();
        String myFormat2 = "dd-MM-yyyy hh:mm:ss"; //In which you need put here
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.getDefault());
        String gettime = sdf2.format(date1);
        tvdatepick.setText(gettime);
        spiner();
        btncreatematerial.setOnClickListener(view1 -> {
            if (TextUtils.isEmpty(etquantity.getText())) {
                etquantity.setError("Input required");
            } else {
                reference.child("users").child(uid).child("project").child("material").get().addOnCompleteListener(task -> {
                    try {
                        String res = Objects.requireNonNull(task.getResult()).getValue().toString();
                        addmat();
                    } catch (Exception e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Task failed").setMessage("You cannot insert any material usage until your project is created")
                                .setNegativeButton("OK", (dialogInterface, im) -> {
                                });
                        builder.show();
                    }
                });
            }


        });
        return view;
    }

    private void addmat() {
        String date, mt_brand, mt_price, mt_quantity;
        date = tvdatepick.getText().toString();

        mt_brand = tvbrand.getText().toString();
        mt_price = tvprice.getText().toString();
        mt_quantity = etquantity.getText().toString();
        reference.child("users").child(uid).child("project").child("material usage").child(date)
                .child(type).child("brand").setValue(mt_brand);
        reference.child("users").child(uid).child("project").child("material usage").child(date)
                .child(type).child("price").setValue(mt_price);
        reference.child("users").child(uid).child("project").child("material usage").child(date)
                .child(type).child("quantity used").setValue(mt_quantity);
        Toast.makeText(getActivity(), "Successful created", Toast.LENGTH_SHORT).show();

    }

    public void spiner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, material_type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = material_type[i];
                reference.child("users").child(uid).child("project").child("material").child(type).child("brand").get().addOnCompleteListener(task -> {
                    try {
                        String result = Objects.requireNonNull(task.getResult()).getValue().toString();
                        tvbrand.setText(result);
                        tvpricedetail.setText("price");
                        if (type.equals("cement")) {
                            tvpricedetail.setText("Price/Bag(50kg)");
                        } else if (type.equals("sand") || type.equals("concrete")) {
                            tvpricedetail.setText("Price/Truck");
                        }else   if (type.equals("steel")) {
                            tvpricedetail.setText("Price/Ton");
                        } else if (type.equals("bricks")) {
                            tvpricedetail.setText("Price/Brick");
                        }
                    } catch (Exception exception) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Task failed").setMessage("You cannot insert any material usage until your project is created")
                                .setNegativeButton("OK", (dialogInterface, im) -> {
                                });
                        builder.show();
                    }

                });
                reference.child("users").child(uid).child("project").child("material").child(type).child("price").get().addOnCompleteListener(task -> {
                    try {
                        String result = Objects.requireNonNull(task.getResult()).getValue().toString();
                        tvprice.setText(result);
                    } catch (Exception exception) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Project not found").setMessage("Your contractor has not been created your project yet")
                                .setNegativeButton("OK", (dialogInterface, im) -> {
                                });
                        builder.show();
                    }

                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}