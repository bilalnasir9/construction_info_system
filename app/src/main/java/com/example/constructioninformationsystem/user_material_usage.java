package com.example.constructioninformationsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.widget.Toast.LENGTH_SHORT;

public class user_material_usage extends Fragment {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ProgressDialog pd;
    String uid;
    List<String> list_date = new ArrayList<>();
    List<String> list_materialtype = new ArrayList<>();
    List<String> list_brand = new ArrayList<>();
    List<String> list_price = new ArrayList<>();
    List<String> list_quantity = new ArrayList<>();
    adapter_user_material objadapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_material_usage, container, false);
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait..");
        uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
         recyclerView = view.findViewById(R.id.recycler_materialUsage);
        FloatingActionButton btn_addmaterial = view.findViewById(R.id.float_addmaterial);
        reference.child("users").child(uid).child("project").child("material usage").get().addOnCompleteListener(task -> {
            try {
                String id = Objects.requireNonNull(task.getResult()).getValue().toString();
                getdata();
            } catch (Exception exception) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Record not found").setMessage("Your have not added any material usage yet")
                        .setNegativeButton("OK", (dialogInterface, i) -> {
                        });
                builder.show();            }
        });

        btn_addmaterial.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new addmaterial()).commit();
        });
        return view;
    }



    public void getdata(){
        reference.child("users").child(uid).child("project").child("material usage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pd.show();
                list_date.clear();
                list_materialtype.clear();
                list_brand.clear();
                list_price.clear();
                list_quantity.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        String date = ds.getKey();
                        assert date != null;
                        reference.child("users").child(uid).child("project").child("material usage").child(date).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot ds2 : snapshot.getChildren()) {
                                    try {
                                        String type = ds2.getKey();
                                        String brand = Objects.requireNonNull(ds2.child("brand").getValue()).toString();
                                        String price = Objects.requireNonNull(ds2.child("price").getValue()).toString();
                                        String quantity = Objects.requireNonNull(ds2.child("quantity used").getValue()).toString();
                                        list_date.add(date);
                                        list_materialtype.add(type);
                                        list_brand.add(brand);
                                        list_price.add(price);
                                        list_quantity.add(quantity);
                                    } catch (Exception e) {
                                        pd.dismiss();
                                        Toast.makeText(getActivity(), "Record not found" + e.toString(), LENGTH_SHORT).show();
                                    }
                                }
                                objadapter = new adapter_user_material(getActivity(), list_date, list_materialtype, list_brand, list_price, list_quantity);
                                recyclerView.setAdapter(objadapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                pd.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });


                    } catch (Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Record not found" + e.toString(), LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.dismiss();
            }
        });

    }
}