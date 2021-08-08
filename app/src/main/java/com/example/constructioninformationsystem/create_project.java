package com.example.constructioninformationsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static android.widget.Toast.LENGTH_SHORT;

public class create_project extends Fragment {
    Spinner spinner;
    create_PJCT objcreate;
    cement objcement;
    sand objsand;
    steel objsteel;
    concrete objconcrete;
    bricks objbricks;
    woods objwood;
    String[] projecttype = {
            "Plaza",
            "Home",
            "Road",
            "Others(Commercials)",
            "Others(Residential)"
    };
    ProgressDialog progressDialog;
    String Type, owner = "", location = "", userid;
    TextView tv_owner;
    EditText etlocation, cementsupplier, cementprice, cementbrand, sandsupplier, sandprice, sandbrand,
            steelsupplier, steelprice, steelbrand, concretesupplier, concreteprice, concretebrand,
            bricksupplier, brickprice, brickbrand, woodsupplier, woodprice,woodbrand;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_project, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");

        spinner = view.findViewById(R.id.spiner_project_type);
        tv_owner = view.findViewById(R.id.etownername);
        Button btn_createproject = view.findViewById(R.id.btn_create_project);
        etlocation = view.findViewById(R.id.etlocatoion);
        cementsupplier = view.findViewById(R.id.etcement_supplier);
        cementprice = view.findViewById(R.id.etcement_price);
        cementbrand = view.findViewById(R.id.etcement_brand);
        sandsupplier = view.findViewById(R.id.etsand_supplier);
        sandprice = view.findViewById(R.id.etsand_price);
        sandbrand = view.findViewById(R.id.etsand_brand);
        steelsupplier = view.findViewById(R.id.etsteel_supplier);
        steelprice = view.findViewById(R.id.etsteel_price);

        steelbrand = view.findViewById(R.id.etsteel_brand);
        concretesupplier = view.findViewById(R.id.etconcrete_supplier);
        concreteprice = view.findViewById(R.id.etconcrete_price);
        concretebrand = view.findViewById(R.id.etconcrete_brand);
        bricksupplier = view.findViewById(R.id.etbricks_supplier);
        brickprice = view.findViewById(R.id.etbricks_price);
        brickbrand = view.findViewById(R.id.etbrick_brand);
        woodsupplier = view.findViewById(R.id.etwood_supplier);
        woodprice = view.findViewById(R.id.etwood_price);
        woodbrand = view.findViewById(R.id.etwood_brand);
        Bundle bundle = this.getArguments();
        userid = Objects.requireNonNull(bundle).getString("id");
        reference.child("users").child(userid).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                owner = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getValue()).toString();
                tv_owner.setText(owner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, projecttype);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Type = projecttype[i];
                        location = etlocation.getText().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });


        btn_createproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                if (TextUtils.isEmpty(etlocation.getText())) {
                    etlocation.setError("Input required");
                }
                if (TextUtils.isEmpty(cementsupplier.getText())) {
                    cementsupplier.setError("Input required");
                }
                if (TextUtils.isEmpty(cementprice.getText())) {
                    cementprice.setError("Input required");
                }
                if (TextUtils.isEmpty(cementbrand.getText())) {
                    cementbrand.setError("Input required");
                }
                if (TextUtils.isEmpty(sandsupplier.getText())) {
                    sandsupplier.setError("Input required");
                }
                if (TextUtils.isEmpty(sandprice.getText())) {
                    sandprice.setError("Input required");
                }
                if (TextUtils.isEmpty(sandbrand.getText())) {
                    sandbrand.setError("Input required");
                }
                if (TextUtils.isEmpty(steelsupplier.getText())) {
                    steelsupplier.setError("Input required");
                }
                if (TextUtils.isEmpty(steelprice.getText())) {
                    steelprice.setError("Input required");
                }

                if (TextUtils.isEmpty(steelbrand.getText())) {
                    steelbrand.setError("Input required");
                }
                if (TextUtils.isEmpty(concretesupplier.getText())) {
                    concretesupplier.setError("Input required");
                }
                if (TextUtils.isEmpty(concreteprice.getText())) {
                    concreteprice.setError("Input required");
                }

                if (TextUtils.isEmpty(concretebrand.getText())) {
                    concretebrand.setError("Input required");
                }
                if (TextUtils.isEmpty(bricksupplier.getText())) {
                    bricksupplier.setError("Input required");
                }
                if (TextUtils.isEmpty(brickprice.getText())) {
                    brickprice.setError("Input required");
                }
                if (TextUtils.isEmpty(brickbrand.getText())) {
                    brickbrand.setError("Input required");
                }
                if (TextUtils.isEmpty(woodsupplier.getText())) {
                    woodsupplier.setError("Input required");
                }
                if (TextUtils.isEmpty(woodprice.getText())) {
                    woodprice.setError("Input required");
                }
                if (TextUtils.isEmpty(woodbrand.getText())) {
                    woodbrand.setError("Input required");
                }
                else {
                    progressDialog.show();
                    location = etlocation.getText().toString();
                    objcreate = new create_PJCT(Type, location, owner);
                    objcement = new cement(cementsupplier.getText().toString(), cementprice.getText().toString(), cementbrand.getText().toString());
                    objsand = new sand(sandsupplier.getText().toString(), sandprice.getText().toString(), sandbrand.getText().toString());
                    objsteel = new steel(steelsupplier.getText().toString(), steelprice.getText().toString(), steelbrand.getText().toString());
                    objconcrete = new concrete(concretesupplier.getText().toString(), concreteprice.getText().toString(), concretebrand.getText().toString());
                    objbricks = new bricks(bricksupplier.getText().toString(), brickprice.getText().toString(), brickbrand.getText().toString());
                    objwood = new woods(woodsupplier.getText().toString(), woodprice.getText().toString(),woodbrand.getText().toString());
                    createbtnclicked();
                }

            }
        });
        return view;
    }

    public void createbtnclicked() {
        try {
            reference2.child("users").child(userid).child("project").setValue(objcreate);
            reference2.child("contractor").child("projects").child(userid).setValue(objcreate);
            reference2.child("users").child(userid).child("project").child("material").child("cement").setValue(objcement);
            reference2.child("users").child(userid).child("project").child("material").child("sand").setValue(objsand);
            reference2.child("users").child(userid).child("project").child("material").child("steel").setValue(objsteel);
            reference2.child("users").child(userid).child("project").child("material").child("concrete").setValue(objconcrete);
            reference2.child("users").child(userid).child("project").child("material").child("bricks").setValue(objbricks);
            reference2.child("users").child(userid).child("project").child("material").child("wood").setValue(objwood);
            progressDialog.dismiss();
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString(), LENGTH_SHORT).show();
        }
        Toast.makeText(getActivity(), "Project successfully created", LENGTH_SHORT).show();
    }

}